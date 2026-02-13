export default {
  app: {
    title: 'SdkHub',
    subtitle: 'SDK Version Manager',
    description: 'Modern SDK management interface based on SDKMAN'
  },
  nav: {
    home: 'Home',
    installed: 'Installed',
    settings: 'Settings'
  },
  common: {
    search: 'Search SDK...',
    loading: 'Loading...',
    refresh: 'Refresh',
    confirm: 'Confirm',
    cancel: 'Cancel',
    close: 'Close',
    save: 'Save',
    delete: 'Delete',
    install: 'Install',
    uninstall: 'Uninstall',
    use: 'Use',
    setDefault: 'Set Default',
    version: 'Version',
    status: 'Status',
    category: 'Category',
    all: 'All',
    website: 'Website',
    noData: 'No Data'
  },
  sdk: {
    available: 'Available SDKs',
    installed: 'Installed SDKs',
    notInstalled: 'Not Installed',
    current: 'Current',
    default: 'Default',
    noInstalled: 'No installed SDKs',
    installSuccess: 'Installation successful',
    installFailed: 'Installation failed',
    uninstallSuccess: 'Uninstallation successful',
    uninstallFailed: 'Uninstallation failed',
    uninstallConfirm: 'Are you sure to uninstall {name} {version}?',
    useSuccess: 'Switch successful',
    useFailed: 'Switch failed',
    setDefaultSuccess: 'Set default successful',
    setDefaultFailed: 'Set default failed',
    categories: {
      all: 'All',
      java: 'Java',
      languages: 'Languages',
      buildTools: 'Build Tools',
      frameworks: 'Frameworks',
      databases: 'Databases',
      messaging: 'Messaging',
      container: 'Container',
      cloud: 'Cloud',
      others: 'Others'
    }
  },
  settings: {
    title: 'Settings',
    theme: {
      title: 'Theme',
      light: 'Light',
      dark: 'Dark',
      auto: 'Auto'
    },
    language: {
      title: 'Language',
      zhCN: '简体中文',
      enUS: 'English'
    },
    path: {
      title: 'Path Configuration',
      sdkPath: 'SDK Installation Path',
      sdkPathPlaceholder: 'Enter SDK installation path, e.g. ~/.sdkman',
      cachePath: 'Cache Path',
      cachePathPlaceholder: 'Cache path (read-only)',
      saveSuccess: 'Configuration saved successfully',
      saveFailed: 'Failed to save configuration'
    }
  },
  status: {
    sdkmanInstalled: 'SDKMAN is installed',
    sdkmanNotInstalled: 'SDKMAN is not installed',
    checkFailed: 'Check failed'
  }
}
