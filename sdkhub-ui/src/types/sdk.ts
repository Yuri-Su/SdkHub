/**
 * SDK 信息
 */
export interface Sdk {
  id: string
  name: string
  vendor: string
  description: string
  category: string
  website: string
  latestVersion?: string
}

/**
 * SDK 版本信息
 */
export interface SdkVersion {
  version: string
  installed: boolean
  current: boolean
  used?: boolean
  releaseDate?: string
}

/**
 * 已安装 SDK
 */
export interface InstalledSdk {
  id: string
  name: string
  version: string
  current: boolean
  path?: string
}

/**
 * SDK 操作结果
 */
export interface SdkOperationResult {
  success: boolean
  message: string
  sdkId: string
  version: string
}

/**
 * SDK 当前版本
 */
export interface SdkCurrentVersion {
  sdkId: string
  version: string
  installed: boolean
}

/**
 * SDKMAN 状态
 */
export interface SdkmanStatus {
  installed: boolean
  path: string
  message: string
}

/**
 * SDK 分类
 */
export interface SdkCategory {
  key: string
  name: string
  icon: string
}
