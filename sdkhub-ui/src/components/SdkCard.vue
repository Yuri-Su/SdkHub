<template>
  <el-card class="sdk-card" :body-style="{ padding: '20px' }" shadow="hover">
    <div class="sdk-header">
      <div class="sdk-icon">
        <el-icon :size="32" color="var(--el-color-primary)">
          <component :is="getCategoryIcon(sdk.category)" />
        </el-icon>
      </div>
      <div class="sdk-info">
        <h3 class="sdk-name">{{ sdk.name }}</h3>
        <el-tag size="small" effect="plain">{{ t(`sdk.categories.${sdk.category}`) }}</el-tag>
      </div>
    </div>

    <p class="sdk-description">{{ sdk.description }}</p>

    <div class="sdk-footer">
      <div class="sdk-version" v-if="sdk.latestVersion">
        <el-icon><VersionTag /></el-icon>
        <span>{{ sdk.latestVersion }}</span>
      </div>
      <div class="sdk-actions">
        <el-button
          type="primary"
          size="small"
          @click="$emit('view', sdk)"
        >
          {{ t('common.install') }}
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import {
  Coffee,
  Document,
  Tools,
  Collection,
  Coin,
  Message,
  Box,
  Cloudy,
  MoreFilled,
  Grid,
  CollectionTag as VersionTag
} from '@element-plus/icons-vue'
import type { Sdk } from '@/types/sdk'

const { t } = useI18n()

defineProps<{
  sdk: Sdk
}>()

defineEmits<{
  view: [sdk: Sdk]
}>()

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
</script>

<style scoped>
.sdk-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.sdk-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.sdk-header {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  margin-bottom: 12px;
}

.sdk-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 56px;
  height: 56px;
  border-radius: 12px;
  background: linear-gradient(135deg, var(--el-color-primary-light-8), var(--el-color-primary-light-9));
}

.sdk-info {
  flex: 1;
}

.sdk-name {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
  color: var(--el-text-color-primary);
  line-height: 1.4;
}

.sdk-description {
  margin: 0 0 16px 0;
  font-size: 13px;
  color: var(--el-text-color-regular);
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.sdk-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.sdk-version {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}
</style>
