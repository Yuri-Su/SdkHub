import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export type Theme = 'light' | 'dark' | 'auto'
export type Language = 'zh-CN' | 'en-US'

export const useAppStore = defineStore('app', () => {
  // State
  const theme = ref<Theme>('auto')
  const language = ref<Language>('zh-CN')
  const sidebarCollapsed = ref(false)

  // Getters
  const isDark = computed(() => {
    if (theme.value === 'auto') {
      return window.matchMedia('(prefers-color-scheme: dark)').matches
    }
    return theme.value === 'dark'
  })

  const currentTheme = computed(() => {
    if (theme.value === 'auto') {
      return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
    }
    return theme.value
  })

  // Actions
  const setTheme = (newTheme: Theme) => {
    theme.value = newTheme
    localStorage.setItem('theme', newTheme)
    applyTheme()
  }

  const setLanguage = (newLanguage: Language) => {
    language.value = newLanguage
    localStorage.setItem('language', newLanguage)
  }

  const toggleSidebar = () => {
    sidebarCollapsed.value = !sidebarCollapsed.value
  }

  const applyTheme = () => {
    const html = document.documentElement
    if (isDark.value) {
      html.classList.add('dark')
    } else {
      html.classList.remove('dark')
    }
  }

  const init = () => {
    // 从 localStorage 读取设置
    const savedTheme = localStorage.getItem('theme') as Theme
    const savedLanguage = localStorage.getItem('language') as Language

    if (savedTheme) {
      theme.value = savedTheme
    } else {
      // 检测系统主题偏好
      theme.value = 'auto'
    }

    if (savedLanguage) {
      language.value = savedLanguage
    } else {
      // 检测系统语言
      const systemLang = navigator.language
      language.value = systemLang.startsWith('zh') ? 'zh-CN' : 'en-US'
    }

    applyTheme()

    // 监听系统主题变化
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', () => {
      if (theme.value === 'auto') {
        applyTheme()
      }
    })
  }

  return {
    theme,
    language,
    sidebarCollapsed,
    isDark,
    currentTheme,
    setTheme,
    setLanguage,
    toggleSidebar,
    init
  }
})
