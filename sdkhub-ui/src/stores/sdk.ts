import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Sdk, SdkVersion, InstalledSdk, SdkmanStatus } from '@/types/sdk'
import * as sdkApi from '@/api/sdk'

export const useSdkStore = defineStore('sdk', () => {
  // State
  const sdks = ref<Sdk[]>([])
  const installedSdks = ref<InstalledSdk[]>([])
  const sdkVersions = ref<Record<string, SdkVersion[]>>({})
  const sdkmanStatus = ref<SdkmanStatus | null>(null)
  const currentCategory = ref('all')
  const searchQuery = ref('')
  const loading = ref(false)

  // Getters
  const categories = computed(() => {
    const categorySet = new Set(sdks.value.map(sdk => sdk.category))
    const categoryList = Array.from(categorySet)
    // 将 'others' 移到最后
    const othersIndex = categoryList.indexOf('others')
    if (othersIndex > -1) {
      categoryList.splice(othersIndex, 1)
      categoryList.push('others')
    }
    return ['all', ...categoryList]
  })

  const filteredSdks = computed(() => {
    let result = sdks.value

    // 按分类过滤
    if (currentCategory.value !== 'all') {
      result = result.filter(sdk => sdk.category === currentCategory.value)
    }

    // 按搜索词过滤
    if (searchQuery.value) {
      const query = searchQuery.value.toLowerCase()
      result = result.filter(sdk =>
        sdk.name.toLowerCase().includes(query) ||
        sdk.id.toLowerCase().includes(query) ||
        sdk.description.toLowerCase().includes(query)
      )
    }

    return result
  })

  const getSdkById = (id: string) => {
    return sdks.value.find(sdk => sdk.id === id)
  }

  const getVersionsBySdkId = (sdkId: string) => {
    return sdkVersions.value[sdkId] || []
  }

  // Actions
  const fetchSdks = async () => {
    loading.value = true
    try {
      sdks.value = await sdkApi.getAllSdks()
    } finally {
      loading.value = false
    }
  }

  const fetchInstalledSdks = async () => {
    try {
      installedSdks.value = await sdkApi.getInstalledSdks()
    } catch (error) {
      console.error('Failed to fetch installed SDKs:', error)
    }
  }

  const fetchSdkVersions = async (sdkId: string) => {
    try {
      const versions = await sdkApi.getSdkVersions(sdkId)
      sdkVersions.value[sdkId] = versions
    } catch (error) {
      console.error('Failed to fetch SDK versions:', error)
    }
  }

  const fetchSdkmanStatus = async () => {
    try {
      sdkmanStatus.value = await sdkApi.getSdkmanStatus()
    } catch (error) {
      console.error('Failed to fetch SDKMAN status:', error)
    }
  }

  const installSdk = async (sdkId: string, version: string) => {
    const result = await sdkApi.installSdk(sdkId, version)
    if (result.success) {
      await fetchInstalledSdks()
      await fetchSdkVersions(sdkId)
    }
    return result
  }

  const uninstallSdk = async (sdkId: string, version: string) => {
    const result = await sdkApi.uninstallSdk(sdkId, version)
    if (result.success) {
      await fetchInstalledSdks()
      await fetchSdkVersions(sdkId)
    }
    return result
  }

  const useSdk = async (sdkId: string, version: string) => {
    const result = await sdkApi.useSdk(sdkId, version)
    if (result.success) {
      await fetchInstalledSdks()
      await fetchSdkVersions(sdkId)
    }
    return result
  }

  const setDefaultSdk = async (sdkId: string, version: string) => {
    const result = await sdkApi.setDefaultSdk(sdkId, version)
    if (result.success) {
      await fetchInstalledSdks()
      await fetchSdkVersions(sdkId)
    }
    return result
  }

  const setCategory = (category: string) => {
    currentCategory.value = category
  }

  const setSearchQuery = (query: string) => {
    searchQuery.value = query
  }

  return {
    sdks,
    installedSdks,
    sdkVersions,
    sdkmanStatus,
    currentCategory,
    searchQuery,
    loading,
    categories,
    filteredSdks,
    getSdkById,
    getVersionsBySdkId,
    fetchSdks,
    fetchInstalledSdks,
    fetchSdkVersions,
    fetchSdkmanStatus,
    installSdk,
    uninstallSdk,
    useSdk,
    setDefaultSdk,
    setCategory,
    setSearchQuery
  }
})
