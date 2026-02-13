<template>
  <el-dialog
    v-model="visible"
    :title="sdk?.name"
    width="700px"
    destroy-on-close
    class="sdk-detail-dialog"
  >
    <div v-if="sdk" class="sdk-detail">
      <div class="sdk-header">
        <p class="sdk-description">{{ sdk.description }}</p>
        <div class="sdk-meta">
          <el-tag size="small">{{ t(`sdk.categories.${sdk.category}`) }}</el-tag>
          <el-link v-if="sdk.website" :href="sdk.website" target="_blank" type="primary">
            {{ t('common.website') }}
          </el-link>
        </div>
      </div>

      <el-divider />

      <div class="versions-section">
        <h4>{{ t('common.version') }}</h4>
        <el-skeleton :rows="3" animated v-if="loading" />
        <el-empty v-else-if="versions.length === 0" :description="t('common.noData')" />
        <div v-else class="version-list">
          <div
            v-for="version in versions"
            :key="version.version"
            class="version-item"
            :class="{
              'installed': version.installed,
              'current': version.current,
              'used': version.used
            }"
          >
            <div class="version-info">
              <span class="version-name">{{ version.version }}</span>
              <div class="version-tags">
                <el-tag v-if="version.installed" size="small" type="success">
                  {{ t('sdk.installed') }}
                </el-tag>
                <el-tag v-if="version.current" size="small" type="primary">
                  {{ t('sdk.current') }}
                </el-tag>
                <el-tag v-if="version.used" size="small" type="warning">
                  {{ t('sdk.default') }}
                </el-tag>
              </div>
            </div>
            <div class="version-actions">
              <el-button
                v-if="!version.installed"
                type="primary"
                size="small"
                :loading="installing === version.version"
                @click="handleInstall(version.version)"
              >
                {{ t('common.install') }}
              </el-button>
              <template v-else>
                <el-button
                  v-if="!version.used"
                  type="success"
                  size="small"
                  :loading="using === version.version"
                  @click="handleUse(version.version)"
                >
                  {{ t('common.use') }}
                </el-button>
                <el-button
                  v-if="!version.used"
                  type="warning"
                  size="small"
                  :loading="settingDefault === version.version"
                  @click="handleSetDefault(version.version)"
                >
                  {{ t('common.setDefault') }}
                </el-button>
                <el-button
                  type="danger"
                  size="small"
                  :loading="uninstalling === version.version"
                  @click="handleUninstall(version.version)"
                >
                  {{ t('common.uninstall') }}
                </el-button>
              </template>
            </div>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useSdkStore } from '@/stores/sdk'
import type { Sdk, SdkVersion } from '@/types/sdk'

const { t } = useI18n()
const sdkStore = useSdkStore()

const props = defineProps<{
  modelValue: boolean
  sdk: Sdk | null
}>()

const emit = defineEmits<{
  'update:modelValue': [value: boolean]
}>()

const visible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const versions = ref<SdkVersion[]>([])
const loading = ref(false)
const installing = ref('')
const uninstalling = ref('')
const using = ref('')
const settingDefault = ref('')

const fetchVersions = async () => {
  if (!props.sdk) return
  loading.value = true
  await sdkStore.fetchSdkVersions(props.sdk.id)
  versions.value = sdkStore.getVersionsBySdkId(props.sdk.id)
  loading.value = false
}

watch(() => props.sdk, () => {
  if (props.sdk && visible.value) {
    fetchVersions()
  }
}, { immediate: true })

watch(visible, (val) => {
  if (val && props.sdk) {
    fetchVersions()
  }
})

const handleInstall = async (version: string) => {
  if (!props.sdk) return
  installing.value = version
  const result = await sdkStore.installSdk(props.sdk.id, version)
  installing.value = ''
  if (result.success) {
    ElMessage.success(t('sdk.installSuccess'))
    fetchVersions()
  } else {
    ElMessage.error(t('sdk.installFailed'))
  }
}

const handleUninstall = async (version: string) => {
  if (!props.sdk) return
  try {
    await ElMessageBox.confirm(
      t('sdk.uninstallConfirm', { version }),
      t('common.confirm'),
      { type: 'warning' }
    )
    uninstalling.value = version
    const result = await sdkStore.uninstallSdk(props.sdk.id, version)
    uninstalling.value = ''
    if (result.success) {
      ElMessage.success(t('sdk.uninstallSuccess'))
      fetchVersions()
    } else {
      ElMessage.error(t('sdk.uninstallFailed'))
    }
  } catch {
    // cancelled
  }
}

const handleUse = async (version: string) => {
  if (!props.sdk) return
  using.value = version
  const result = await sdkStore.useSdk(props.sdk.id, version)
  using.value = ''
  if (result.success) {
    ElMessage.success(t('sdk.useSuccess'))
    fetchVersions()
  } else {
    ElMessage.error(t('sdk.useFailed'))
  }
}

const handleSetDefault = async (version: string) => {
  if (!props.sdk) return
  settingDefault.value = version
  const result = await sdkStore.setDefaultSdk(props.sdk.id, version)
  settingDefault.value = ''
  if (result.success) {
    ElMessage.success(t('sdk.setDefaultSuccess'))
    fetchVersions()
  } else {
    ElMessage.error(t('sdk.setDefaultFailed'))
  }
}
</script>

<style scoped>
.sdk-detail-dialog :deep(.el-dialog__body) {
  padding-top: 0;
}

.sdk-detail {
  max-height: 60vh;
  overflow-y: auto;
}

.sdk-header {
  margin-bottom: 20px;
}

.sdk-description {
  margin: 0 0 12px 0;
  color: var(--el-text-color-regular);
  line-height: 1.6;
}

.sdk-meta {
  display: flex;
  gap: 12px;
  align-items: center;
}

.versions-section h4 {
  margin: 0 0 16px 0;
  font-size: 16px;
  color: var(--el-text-color-primary);
}

.version-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.version-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-radius: 8px;
  background-color: var(--el-fill-color-light);
  transition: all 0.3s;
}

.version-item.installed {
  background-color: var(--el-color-success-light-9);
}

.version-item.current {
  background-color: var(--el-color-primary-light-9);
}

.version-item.used {
  background-color: var(--el-color-warning-light-9);
}

.version-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.version-name {
  font-weight: 500;
  color: var(--el-text-color-primary);
}

.version-tags {
  display: flex;
  gap: 4px;
}

.version-actions {
  display: flex;
  gap: 8px;
}
</style>
