<template>
  <div class="installed-view">
    <div class="page-header">
      <h2>{{ t('sdk.installed') }}</h2>
      <el-button type="primary" :icon="Refresh" @click="refresh" :loading="loading">
        {{ t('common.refresh') }}
      </el-button>
    </div>

    <el-skeleton :rows="5" animated v-if="loading" />

    <div v-else-if="installedSdks.length === 0" class="empty-state">
      <el-empty :description="t('sdk.noInstalled')" />
    </div>

    <div v-else class="installed-list">
      <el-card
        v-for="sdk in installedSdks"
        :key="`${sdk.id}-${sdk.version}`"
        class="installed-card"
        shadow="hover"
      >
        <div class="installed-header">
          <div class="sdk-info">
            <h3>{{ sdk.name }}</h3>
            <el-tag size="small" :type="sdk.current ? 'success' : 'info'">
              {{ sdk.version }}
            </el-tag>
          </div>
          <div class="sdk-status">
            <el-tag v-if="sdk.current" type="success" effect="dark">
              {{ t('sdk.current') }}
            </el-tag>
          </div>
        </div>

        <div class="installed-actions">
          <el-button
            v-if="!sdk.current"
            type="primary"
            size="small"
            @click="handleUse(sdk)"
            :loading="using === `${sdk.id}-${sdk.version}`"
          >
            {{ t('common.use') }}
          </el-button>
          <el-button
            type="danger"
            size="small"
            @click="handleUninstall(sdk)"
            :loading="uninstalling === `${sdk.id}-${sdk.version}`"
          >
            {{ t('common.uninstall') }}
          </el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useSdkStore } from '@/stores/sdk'
import type { InstalledSdk } from '@/types/sdk'

const { t } = useI18n()
const sdkStore = useSdkStore()

const loading = ref(false)
const installedSdks = ref<InstalledSdk[]>([])
const using = ref('')
const uninstalling = ref('')

const refresh = async () => {
  loading.value = true
  await sdkStore.fetchInstalledSdks()
  installedSdks.value = sdkStore.installedSdks
  loading.value = false
}

const handleUse = async (sdk: InstalledSdk) => {
  using.value = `${sdk.id}-${sdk.version}`
  const result = await sdkStore.useSdk(sdk.id, sdk.version)
  using.value = ''
  if (result.success) {
    ElMessage.success(t('sdk.useSuccess'))
    refresh()
  } else {
    ElMessage.error(t('sdk.useFailed'))
  }
}

const handleUninstall = async (sdk: InstalledSdk) => {
  try {
    await ElMessageBox.confirm(
      t('sdk.uninstallConfirm', { name: sdk.name, version: sdk.version }),
      t('common.confirm'),
      { type: 'warning' }
    )
    uninstalling.value = `${sdk.id}-${sdk.version}`
    const result = await sdkStore.uninstallSdk(sdk.id, sdk.version)
    uninstalling.value = ''
    if (result.success) {
      ElMessage.success(t('sdk.uninstallSuccess'))
      refresh()
    } else {
      ElMessage.error(t('sdk.uninstallFailed'))
    }
  } catch {
    // cancelled
  }
}

onMounted(() => {
  refresh()
})
</script>

<style scoped>
.installed-view {
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

.installed-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(360px, 1fr));
  gap: 20px;
}

.installed-card {
  border-radius: 12px;
}

.installed-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 16px;
}

.sdk-info h3 {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
}

.installed-actions {
  display: flex;
  gap: 8px;
}

.empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;
}
</style>
