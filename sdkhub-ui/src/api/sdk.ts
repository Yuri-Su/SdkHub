import axios from 'axios'
import type {
  Sdk,
  SdkVersion,
  InstalledSdk,
  SdkOperationResult,
  SdkCurrentVersion,
  SdkmanStatus
} from '@/types/sdk'

const api = axios.create({
  baseURL: '/api',
  timeout: 30000
})

/**
 * 获取所有可用的 SDK 列表
 */
export const getAllSdks = async (): Promise<Sdk[]> => {
  const response = await api.get<Sdk[]>('/sdks')
  return response.data
}

/**
 * 获取指定 SDK 的所有可用版本
 */
export const getSdkVersions = async (sdkId: string): Promise<SdkVersion[]> => {
  const response = await api.get<SdkVersion[]>(`/sdks/${sdkId}/versions`)
  return response.data
}

/**
 * 安装指定版本的 SDK
 */
export const installSdk = async (sdkId: string, version: string): Promise<SdkOperationResult> => {
  const response = await api.post<SdkOperationResult>(`/sdks/${sdkId}/install?version=${version}`)
  return response.data
}

/**
 * 卸载指定版本的 SDK
 */
export const uninstallSdk = async (sdkId: string, version: string): Promise<SdkOperationResult> => {
  const response = await api.post<SdkOperationResult>(`/sdks/${sdkId}/uninstall`, version, {
    headers: {
      'Content-Type': 'text/plain'
    }
  })
  return response.data
}

/**
 * 切换到指定版本的 SDK（临时使用）
 */
export const useSdk = async (sdkId: string, version: string): Promise<SdkOperationResult> => {
  const response = await api.post<SdkOperationResult>(`/sdks/${sdkId}/use`, { version })
  return response.data
}

/**
 * 设置默认 SDK 版本
 */
export const setDefaultSdk = async (sdkId: string, version: string): Promise<SdkOperationResult> => {
  const response = await api.post<SdkOperationResult>(`/sdks/${sdkId}/default?version=${version}`)
  return response.data
}

/**
 * 获取已安装的 SDK 列表
 */
export const getInstalledSdks = async (): Promise<InstalledSdk[]> => {
  const response = await api.get<InstalledSdk[]>('/sdks/installed')
  return response.data
}

/**
 * 获取当前使用的 SDK 版本
 */
export const getCurrentVersion = async (sdkId: string): Promise<SdkCurrentVersion> => {
  const response = await api.get<SdkCurrentVersion>(`/sdks/${sdkId}/current`)
  return response.data
}

/**
 * 检查 SDKMAN 状态
 */
export const getSdkmanStatus = async (): Promise<SdkmanStatus> => {
  const response = await api.get<SdkmanStatus>('/sdks/status')
  return response.data
}
