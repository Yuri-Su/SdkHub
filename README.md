# SdkHub - 现代化 SDK 管理工具

<p align="center">
  <img src="https://img.shields.io/badge/Vue-3.5-4FC08D?logo=vue.js" alt="Vue 3">
  <img src="https://img.shields.io/badge/TypeScript-5.9-3178C6?logo=typescript" alt="TypeScript">
  <img src="https://img.shields.io/badge/Tauri-2.0-FFC131?logo=tauri" alt="Tauri">
  <img src="https://img.shields.io/badge/Java-21-007396?logo=java" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Element%20Plus-2.13-409EFF?logo=element" alt="Element Plus">
</p>

<p align="center">
  <b>一款基于 Tauri + Vue 3 + Java 21 构建的现代化 SDK 管理工具</b>
</p>

## ✨ 功能特性

- 💻 **跨平台支持** - 支持 Windows、macOS 和 Linux 系统
- 🎨 **现代化 UI** - 采用 Vue 3 + Element Plus，界面美观流畅
- 🌍 **国际化支持** - 支持中英文切换，自动检测系统语言
- 🌗 **主题切换** - 支持亮色/暗色/自动三种主题模式
- 📦 **SDK 管理** - 浏览、安装、卸载、切换 SDK 版本
- 🔍 **搜索过滤** - 快速查找所需的 SDK
- 🏷️ **分类浏览** - 按类别查看 SDK（Java、构建工具、编程语言等）

## 🚀 技术栈

### 前端
- **Vue 3** - 渐进式 JavaScript 框架
- **TypeScript** - 类型安全的 JavaScript 超集
- **Element Plus** - 基于 Vue 3 的组件库
- **Pinia** - Vue 状态管理方案
- **Vue Router** - Vue.js 路由管理器
- **Vue I18n** - 国际化插件

### 后端
- **Java 21** - 最新的 Java LTS 版本
- **Spring Boot 3.2** - 快速开发框架
- **Lombok** - 简化 Java 代码的工具

### 桌面端
- **Tauri** - 使用 Web 技术构建桌面应用
- **Rust** - 系统级编程语言（Tauri 核心）

## 📦 项目结构

```
SdkHub/
├── src/
│   ├── main/
│   │   ├── frontend/          # Vue 3 前端代码
│   │   │   ├── src/
│   │   │   │   ├── components/  # 组件
│   │   │   │   ├── views/       # 页面
│   │   │   │   ├── stores/      # Pinia 状态管理
│   │   │   │   ├── router/      # 路由配置
│   │   │   │   ├── i18n/        # 国际化
│   │   │   │   └── styles/      # 样式文件
│   │   │   └── index.html
│   │   ├── java/              # Java 后端代码
│   │   │   └── com/yuchang/sdkhub/
│   │   │       ├── controller/  # REST API 控制器
│   │   │       ├── config/      # 配置类
│   │   │       └── SdkHubApplication.java
│   │   └── resources/         # 资源文件
│   │       ├── static/        # 前端构建输出
│   │       └── application.yaml
│   └── tauri/                 # Tauri 配置
│       ├── src/
│       │   └── main.rs        # Rust 主程序
│       ├── Cargo.toml
│       └── tauri.conf.json
├── icons/                     # 应用图标
├── package.json               # Node.js 依赖
├── pom.xml                    # Maven 配置
├── vite.config.ts             # Vite 配置
└── tsconfig.json              # TypeScript 配置
```

## 🛠️ 开发环境

### 前置要求
- **Node.js** >= 18.0
- **Java** >= 21
- **Maven** >= 3.8
- **Rust** >= 1.70（用于 Tauri）

### 安装依赖

```bash
# 安装 Node.js 依赖
npm install

# 安装 Tauri CLI
npm install -g @tauri-apps/cli
```

### 开发模式

```bash
# 启动前端开发服务器
npm run dev

# 在新终端启动 Java 后端
mvn spring-boot:run

# 或者启动 Tauri 开发模式（包含前后端）
npm run tauri:dev
```

### 构建生产版本

```bash
# 构建前端
npm run build

# 构建 Java 后端
mvn clean package

# 构建 Tauri 桌面应用
npm run tauri:build
```

## 📝 使用说明

### SDK 列表页面
- 浏览所有可用的 SDK
- 按分类筛选（Java、构建工具、编程语言等）
- 搜索 SDK 名称或描述
- 点击"查看"查看版本详情

### 已安装页面
- 查看已安装的 SDK
- 切换当前使用的版本
- 卸载不需要的版本

### 设置页面
- 切换主题（亮色/暗色/自动）
- 切换语言（中文/英文）
- 配置 SDK 安装路径
- 查看系统信息

## 🔧 配置说明

### 前端配置
编辑 `src/main/frontend/src/i18n/locales/` 目录下的 JSON 文件可添加新的语言支持。

### 后端配置
编辑 `src/main/resources/application.yaml` 可修改以下配置：
- 服务器端口
- SDK 安装路径
- 日志级别

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 [MIT](LICENSE) 许可证开源。

## 🙏 致谢

- [SDKMAN!](https://sdkman.io/) - 灵感来源
- [Tauri](https://tauri.app/) - 桌面应用框架
- [Vue.js](https://vuejs.org/) - 前端框架
- [Element Plus](https://element-plus.org/) - UI 组件库
- [Spring Boot](https://spring.io/projects/spring-boot) - 后端框架

---

<p align="center">
  Made with ❤️ by SdkHub Team
</p>
