<template>
  <div class="home-view">
    <div class="page-header">
      <h2>{{ t('sdk.available') }}</h2>
      <el-tag type="info" size="large">{{ sdkStore.filteredSdks.length }} SDKs</el-tag>
    </div>

    <el-skeleton :rows="5" animated v-if="sdkStore.loading" />

    <div v-else-if="sdkStore.filteredSdks.length === 0" class="empty-state">
      <el-empty :description="t('common.search')" />
    </div>

    <div v-else class="sdk-grid">
      <SdkCard
        v-for="sdk in sdkStore.filteredSdks"
        :key="sdk.id"
        :sdk="sdk"
        @view="handleViewSdk"
      />
    </div>

    <!-- SDK Detail Dialog -->
    <SdkDetailDialog
      v-model="detailDialogVisible"
      :sdk="selectedSdk"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { useSdkStore } from '@/stores/sdk'
import SdkCard from '@/components/SdkCard.vue'
import SdkDetailDialog from '@/components/SdkDetailDialog.vue'
import type { Sdk } from '@/types/sdk'

const { t } = useI18n()
const sdkStore = useSdkStore()

const detailDialogVisible = ref(false)
const selectedSdk = ref<Sdk | null>(null)

const handleViewSdk = (sdk: Sdk) => {
  selectedSdk.value = sdk
  detailDialogVisible.value = true
}

onMounted(() => {
  sdkStore.fetchSdks()
  sdkStore.fetchInstalledSdks()
})
</script>

<style scoped>
.home-view {
  padding: 24px;
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
}

.page-header h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: var(--el-text-color-primary);
}

.sdk-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 20px;
}

.empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;
}
</style>
