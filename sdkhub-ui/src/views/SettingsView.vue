<template>
  <div class="settings-view">
    <div class="page-header">
      <h2>{{ t('settings.title') }}</h2>
    </div>

    <div class="settings-content">
      <!-- Theme Settings -->
      <el-card class="settings-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <el-icon><Brush /></el-icon>
            <span>{{ t('settings.theme.title') }}</span>
          </div>
        </template>

        <div class="theme-options">
          <el-radio-group v-model="appStore.theme" @change="handleThemeChange">
            <el-radio-button label="light">
              <el-icon><Sunny /></el-icon>
              {{ t('settings.theme.light') }}
            </el-radio-button>
            <el-radio-button label="dark">
              <el-icon><Moon /></el-icon>
              {{ t('settings.theme.dark') }}
            </el-radio-button>
            <el-radio-button label="auto">
              <el-icon><SemiSelect /></el-icon>
              {{ t('settings.theme.auto') }}
            </el-radio-button>
          </el-radio-group>
        </div>
      </el-card>

      <!-- Language Settings -->
      <el-card class="settings-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <el-icon><ChatDotRound /></el-icon>
            <span>{{ t('settings.language.title') }}</span>
          </div>
        </template>

        <div class="language-options">
          <el-radio-group v-model="appStore.language" @change="handleLanguageChange">
            <el-radio-button label="zh-CN">{{ t('settings.language.zhCN') }}</el-radio-button>
            <el-radio-button label="en-US">{{ t('settings.language.enUS') }}</el-radio-button>
          </el-radio-group>
        </div>
      </el-card>

      <!-- Path Settings -->
      <el-card class="settings-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <el-icon><Folder /></el-icon>
            <span>{{ t('settings.path.title') }}</span>
          </div>
        </template>

        <el-form :model="configForm" label-position="top" class="path-form">
          <el-form-item :label="t('settings.path.sdkPath')">
            <el-input
              v-model="configForm.sdkPath"
              :placeholder="t('settings.path.sdkPathPlaceholder')"
            >
              <template #append>
                <el-button @click="saveConfig" :loading="saving">
                  {{ t('common.save') }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item :label="t('settings.path.cachePath')">
            <el-input
              v-model="configForm.cachePath"
              :placeholder="t('settings.path.cachePathPlaceholder')"
              disabled
            />
          </el-form-item>
        </el-form>
      </el-card>

      <!-- SDKMAN Status -->
      <el-card class="settings-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <el-icon><InfoFilled /></el-icon>
            <span>SDKMAN</span>
          </div>
        </template>

        <div class="sdkman-status" v-if="sdkStore.sdkmanStatus">
          <el-alert
            :title="sdkStore.sdkmanStatus.message"
            :type="sdkStore.sdkmanStatus.installed ? 'success' : 'warning'"
            :closable="false"
            show-icon
          />
          <div v-if="sdkStore.sdkmanStatus.installed" class="sdkman-path">
            <el-icon><Folder /></el-icon>
            <span>{{ sdkStore.sdkmanStatus.path }}</span>
          </div>
        </div>

        <div v-else class="sdkman-loading">
          <el-skeleton :rows="2" animated />
        </div>
      </el-card>

      <!-- About -->
      <el-card class="settings-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <el-icon><InfoFilled /></el-icon>
            <span>{{ t('app.title') }}</span>
          </div>
        </template>

        <div class="about-content">
          <div class="logo-large">
            <el-icon :size="64" color="var(--el-color-primary)"><Box /></el-icon>
          </div>
          <h3>{{ t('app.title') }}</h3>
          <p class="subtitle">{{ t('app.subtitle') }}</p>
          <p class="description">{{ t('app.description') }}</p>
          <div class="version">v0.1.0</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, reactive } from 'vue'
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'
import {
  Brush,
  Sunny,
  Moon,
  SemiSelect,
  ChatDotRound,
  InfoFilled,
  Folder,
  Box
} from '@element-plus/icons-vue'
import { useAppStore, type Theme, type Language } from '@/stores/app'
import { useSdkStore } from '@/stores/sdk'
import { getConfig, updateConfig } from '@/api/config'
import type { Config } from '@/types/config'

const { t, locale } = useI18n()
const appStore = useAppStore()
const sdkStore = useSdkStore()

const saving = ref(false)
const configForm = reactive<Config>({
  sdkPath: '',
  cachePath: '',
  autoCheckUpdate: true
})

const handleThemeChange = (theme: Theme) => {
  appStore.setTheme(theme)
}

const handleLanguageChange = (lang: Language) => {
  appStore.setLanguage(lang)
  locale.value = lang
}

const loadConfig = async () => {
  try {
    const config = await getConfig()
    configForm.sdkPath = config.sdkPath
    configForm.cachePath = config.cachePath
    configForm.autoCheckUpdate = config.autoCheckUpdate
  } catch (error) {
    console.error('Failed to load config:', error)
  }
}

const saveConfig = async () => {
  saving.value = true
  try {
    await updateConfig({
      sdkPath: configForm.sdkPath,
      cachePath: configForm.cachePath,
      autoCheckUpdate: configForm.autoCheckUpdate
    })
    ElMessage.success(t('settings.path.saveSuccess'))
    await sdkStore.fetchSdkmanStatus()
  } catch (error) {
    ElMessage.error(t('settings.path.saveFailed'))
  } finally {
    saving.value = false
  }
}

onMounted(() => {
  sdkStore.fetchSdkmanStatus()
  loadConfig()
})
</script>

<style scoped>
.settings-view {
  padding: 24px;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: var(--el-text-color-primary);
}

.settings-content {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 20px;
}

.settings-card {
  border-radius: 12px;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
}

.theme-options,
.language-options {
  padding: 16px 0;
}

.path-form {
  padding: 8px 0;
}

.sdkman-status {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.sdkman-path {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px;
  background-color: var(--el-fill-color-light);
  border-radius: 8px;
  font-size: 13px;
  color: var(--el-text-color-secondary);
  word-break: break-all;
}

.about-content {
  text-align: center;
  padding: 24px;
}

.logo-large {
  margin-bottom: 16px;
}

.about-content h3 {
  margin: 0 0 8px 0;
  font-size: 20px;
  font-weight: 600;
}

.subtitle {
  margin: 0 0 8px 0;
  color: var(--el-text-color-secondary);
}

.description {
  margin: 0 0 16px 0;
  color: var(--el-text-color-regular);
  font-size: 14px;
}

.version {
  display: inline-block;
  padding: 4px 12px;
  background-color: var(--el-fill-color);
  border-radius: 12px;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}
</style>
