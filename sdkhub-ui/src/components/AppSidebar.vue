<template>
  <aside class="app-sidebar" :style="{ width: appStore.sidebarCollapsed ? '64px' : '200px' }">
    <div class="sidebar-content">
      <el-menu
        :default-active="$route.path"
        :collapse="appStore.sidebarCollapsed"
        :collapse-transition="false"
        router
        class="sidebar-menu"
      >
        <el-menu-item index="/">
          <el-icon><HomeFilled /></el-icon>
          <template #title>{{ t('nav.home') }}</template>
        </el-menu-item>

        <el-menu-item index="/installed">
          <el-icon><Download /></el-icon>
          <template #title>{{ t('nav.installed') }}</template>
        </el-menu-item>

        <el-menu-item index="/settings">
          <el-icon><Setting /></el-icon>
          <template #title>{{ t('nav.settings') }}</template>
        </el-menu-item>
      </el-menu>

      <div class="category-section" v-if="!appStore.sidebarCollapsed">
        <el-divider />
        <div class="category-title">{{ t('common.category') }}</div>
        <el-menu
          :default-active="sdkStore.currentCategory"
          @select="handleCategorySelect"
          class="category-menu"
        >
          <el-menu-item
            v-for="category in sdkStore.categories"
            :key="category"
            :index="category"
          >
            <el-icon>
              <component :is="getCategoryIcon(category)" />
            </el-icon>
            <template #title>{{ t(`sdk.categories.${category}`) }}</template>
          </el-menu-item>
        </el-menu>
      </div>
    </div>

    <div class="collapse-btn">
      <el-button
        text
        :icon="appStore.sidebarCollapsed ? ArrowRight : ArrowLeft"
        @click="appStore.toggleSidebar"
      />
    </div>
  </aside>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import {
  HomeFilled,
  Download,
  Setting,
  ArrowLeft,
  ArrowRight,
  Grid,
  Coffee,
  Document,
  Tools,
  Collection,
  Coin,
  Message,
  Box,
  Cloudy,
  MoreFilled
} from '@element-plus/icons-vue'
import { useAppStore } from '@/stores/app'
import { useSdkStore } from '@/stores/sdk'

const { t } = useI18n()
const appStore = useAppStore()
const sdkStore = useSdkStore()

const getCategoryIcon = (category: string) => {
  const iconMap: Record<string, any> = {
    all: Grid,
    java: Coffee,
    languages: Document,
    buildTools: Tools,
    frameworks: Collection,
    databases: Coin,
    messaging: Message,
    container: Box,
    cloud: Cloudy,
    others: MoreFilled
  }
  return iconMap[category] || Grid
}

const handleCategorySelect = (category: string) => {
  sdkStore.setCategory(category)
}
</script>

<style scoped>
.app-sidebar {
  display: flex;
  flex-direction: column;
  background-color: var(--el-bg-color);
  border-right: 1px solid var(--el-border-color-light);
  transition: width 0.3s;
  overflow: hidden;
}

.sidebar-content {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
}

.sidebar-menu {
  border-right: none;
}

.category-section {
  padding-bottom: 16px;
}

.category-title {
  padding: 0 20px;
  font-size: 12px;
  color: var(--el-text-color-secondary);
  text-transform: uppercase;
  margin-bottom: 8px;
}

.category-menu {
  border-right: none;
}

.collapse-btn {
  padding: 12px;
  text-align: center;
  border-top: 1px solid var(--el-border-color-light);
  flex-shrink: 0;
}
</style>
