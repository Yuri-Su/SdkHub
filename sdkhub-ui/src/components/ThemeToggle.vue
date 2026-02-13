<template>
  <el-dropdown trigger="click" @command="handleThemeChange">
    <el-button circle :icon="currentIcon" />
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item command="light" :disabled="appStore.theme === 'light'">
          <el-icon><Sunny /></el-icon>
          <span>{{ t('settings.theme.light') }}</span>
        </el-dropdown-item>
        <el-dropdown-item command="dark" :disabled="appStore.theme === 'dark'">
          <el-icon><Moon /></el-icon>
          <span>{{ t('settings.theme.dark') }}</span>
        </el-dropdown-item>
        <el-dropdown-item command="auto" :disabled="appStore.theme === 'auto'">
          <el-icon><SemiSelect /></el-icon>
          <span>{{ t('settings.theme.auto') }}</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { Sunny, Moon, SemiSelect } from '@element-plus/icons-vue'
import { useAppStore, type Theme } from '@/stores/app'

const { t } = useI18n()
const appStore = useAppStore()

const currentIcon = computed(() => {
  switch (appStore.theme) {
    case 'light':
      return Sunny
    case 'dark':
      return Moon
    default:
      return SemiSelect
  }
})

const handleThemeChange = (theme: Theme) => {
  appStore.setTheme(theme)
}
</script>
