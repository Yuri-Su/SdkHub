<template>
  <el-config-provider :locale="locale">
    <div class="app" :class="{ 'dark': appStore.isDark }">
      <div class="app-wrapper">
        <AppHeader class="app-header" />
        <div class="app-body">
          <AppSidebar class="app-sidebar" />
          <main class="app-main">
            <router-view />
          </main>
        </div>
      </div>
    </div>
  </el-config-provider>
</template>

<script setup lang="ts">
import { onMounted, computed } from 'vue'
import { useAppStore } from '@/stores/app'
import AppHeader from '@/components/AppHeader.vue'
import AppSidebar from '@/components/AppSidebar.vue'
import zhCN from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'

const appStore = useAppStore()

const locale = computed(() => {
  return appStore.language === 'zh-CN' ? zhCN : en
})

onMounted(() => {
  appStore.init()
})
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #app {
  height: 100%;
}

.app {
  height: 100%;
  background-color: var(--el-bg-color-page);
  color: var(--el-text-color-primary);
  transition: background-color 0.3s, color 0.3s;
}

.app-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.app-header {
  flex-shrink: 0;
  height: 64px;
}

.app-body {
  display: flex;
  flex: 1;
  overflow: hidden;
}

.app-sidebar {
  flex-shrink: 0;
}

.app-main {
  flex: 1;
  overflow-y: auto;
  background-color: var(--el-bg-color-page);
  padding: 0;
}

.dark {
  color-scheme: dark;
}

::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: var(--el-border-color);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: var(--el-text-color-disabled);
}
</style>
