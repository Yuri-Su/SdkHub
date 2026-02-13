<template>
  <el-dropdown trigger="click" @command="handleLanguageChange">
    <el-button circle>
      <span class="lang-text">{{ currentLangText }}</span>
    </el-button>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item command="zh-CN" :disabled="appStore.language === 'zh-CN'">
          {{ t('settings.language.zhCN') }}
        </el-dropdown-item>
        <el-dropdown-item command="en-US" :disabled="appStore.language === 'en-US'">
          {{ t('settings.language.enUS') }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { useAppStore, type Language } from '@/stores/app'

const { t, locale } = useI18n()
const appStore = useAppStore()

const currentLangText = computed(() => {
  return appStore.language === 'zh-CN' ? '中' : 'EN'
})

const handleLanguageChange = (lang: Language) => {
  appStore.setLanguage(lang)
  locale.value = lang
}
</script>

<style scoped>
.lang-text {
  font-size: 12px;
  font-weight: 600;
}
</style>
