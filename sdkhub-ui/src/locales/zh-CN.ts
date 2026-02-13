export default {
  app: {
    title: 'SdkHub',
    subtitle: 'SDK 版本管理工具',
    description: '基于 SDKMAN 的现代化 SDK 管理界面'
  },
  nav: {
    home: '首页',
    installed: '已安装',
    settings: '设置'
  },
  common: {
    search: '搜索 SDK...',
    loading: '加载中...',
    refresh: '刷新',
    confirm: '确认',
    cancel: '取消',
    close: '关闭',
    save: '保存',
    delete: '删除',
    install: '安装',
    uninstall: '卸载',
    use: '使用',
    setDefault: '设为默认',
    version: '版本',
    status: '状态',
    category: '分类',
    all: '全部',
    website: '官网',
    noData: '暂无数据'
  },
  sdk: {
    available: '可用 SDK',
    installed: '已安装 SDK',
    notInstalled: '未安装',
    current: '当前使用',
    default: '默认版本',
    noInstalled: '暂无已安装的 SDK',
    installSuccess: '安装成功',
    installFailed: '安装失败',
    uninstallSuccess: '卸载成功',
    uninstallFailed: '卸载失败',
    uninstallConfirm: '确定要卸载 {name} {version} 吗？',
    useSuccess: '切换成功',
    useFailed: '切换失败',
    setDefaultSuccess: '设置默认成功',
    setDefaultFailed: '设置默认失败',
    categories: {
      all: '全部',
      java: 'Java',
      languages: '编程语言',
      buildTools: '构建工具',
      frameworks: '框架',
      databases: '数据库',
      messaging: '消息队列',
      container: '容器',
      cloud: '云服务',
      others: '其他'
    }
  },
  settings: {
    title: '设置',
    theme: {
      title: '主题',
      light: '亮色',
      dark: '暗色',
      auto: '自动'
    },
    language: {
      title: '语言',
      zhCN: '简体中文',
      enUS: 'English'
    },
    path: {
      title: '路径配置',
      sdkPath: 'SDK 安装路径',
      sdkPathPlaceholder: '请输入 SDK 安装路径，如 ~/.sdkman',
      cachePath: '缓存路径',
      cachePathPlaceholder: '缓存路径（只读）',
      saveSuccess: '配置保存成功',
      saveFailed: '配置保存失败'
    }
  },
  status: {
    sdkmanInstalled: 'SDKMAN 已安装',
    sdkmanNotInstalled: 'SDKMAN 未安装',
    checkFailed: '检查失败'
  }
}
