<template>
  <header class="app-header">
    <div class="header-left">
      <div class="logo">
        <el-icon :size="28" color="var(--el-color-primary)"><Box /></el-icon>
        <span class="title">{{ t('app.title') }}</span>
      </div>
    </div>

    <div class="header-center">
      <el-input
        v-model="sdkStore.searchQuery"
        :placeholder="t('common.search')"
        clearable
        class="search-input"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <div class="header-right">
      <ThemeToggle />
      <LanguageToggle />
      <el-button circle :icon="Refresh" @click="refresh" :loading="sdkStore.loading" />
    </div>
  </header>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import { Box, Search, Refresh } from '@element-plus/icons-vue'
import { useSdkStore } from '@/stores/sdk'
import ThemeToggle from './ThemeToggle.vue'
import LanguageToggle from './LanguageToggle.vue'

const { t } = useI18n()
const sdkStore = useSdkStore()

const refresh = () => {
  sdkStore.fetchSdks()
  sdkStore.fetchInstalledSdks()
}
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  padding: 0 24px;
  background-color: var(--el-bg-color);
  border-bottom: 1px solid var(--el-border-color-light);
}

.header-left {
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
}

.title {
  font-size: 20px;
  font-weight: 600;
  color: var(--el-text-color-primary);
}

.header-center {
  flex: 1;
  max-width: 480px;
  margin: 0 24px;
}

.search-input {
  width: 100%;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}
</style>
