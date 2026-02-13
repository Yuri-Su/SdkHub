# SdkHub - 现代化 SDK 管理工具

<p align="center">
  <img src="https://img.shields.io/badge/Vue-3.4-4FC08D?logo=vue.js" alt="Vue 3">
  <img src="https://img.shields.io/badge/TypeScript-5.6-3178C6?logo=typescript" alt="TypeScript">
  <img src="https://img.shields.io/badge/Java-21-007396?logo=java" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F?logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Element%20Plus-2.5-409EFF?logo=element" alt="Element Plus">
</p>

<p align="center">
  <b>一款基于 Vue 3 + Spring Boot 构建的现代化 SDK 管理工具</b>
</p>

## ✨ 功能特性

- 🎨 **现代化 UI** - 采用 Vue 3 + Element Plus，卡片式布局，界面美观流畅
- 🌍 **国际化支持** - 支持中英文切换，自动检测系统语言偏好
- 🌗 **主题切换** - 支持亮色/暗色/自动三种主题模式，自动跟随系统
- 📦 **SDK 管理** - 浏览、安装、卸载、切换 SDK 版本，基于 SDKMAN
- 🔍 **搜索过滤** - 快速搜索 SDK 名称、ID 或描述
- 🏷️ **分类浏览** - 按类别查看 SDK（Java、构建工具、编程语言、框架等）
- ⚙️ **配置管理** - 支持修改 SDK 安装路径等配置

## 🚀 技术栈

### 前端 (sdkhub-ui/)

- **Vue 3** - 渐进式 JavaScript 框架
- **TypeScript** - 类型安全的 JavaScript 超集
- **Element Plus** - 基于 Vue 3 的组件库
- **Pinia** - Vue 状态管理方案
- **Vue Router** - Vue.js 路由管理器
- **Vue I18n** - 国际化插件
- **Axios** - HTTP 客户端
- **Vite** - 构建工具

### 后端 (src/main/java/)

- **Java 21** - 最新的 Java LTS 版本
- **Spring Boot 4.0** - 快速开发框架
- **Lombok** - 简化 Java 代码的工具
- **Maven** - 项目构建工具

## 📦 项目结构

```
SdkHub/
├── src/main/java/com/yuchang/sdkhub/    # Java 后端代码
│   ├── controller/                      # REST API 控制器
│   │   ├── SdkController.java           # SDK 管理接口
│   │   └── ConfigController.java        # 配置管理接口
│   ├── service/                         # 业务逻辑层
│   │   └── SdkmanService.java           # SDKMAN 服务封装
│   ├── config/                          # 配置类
│   │   └── SdkHubProperties.java        # 应用配置属性
│   ├── dto/                             # 数据传输对象
│   │   ├── SdkInfoDTO.java
│   │   ├── SdkVersionDTO.java
│   │   └── ...
│   ├── vo/                              # 视图对象
│   │   ├── SdkVO.java
│   │   └── ...
│   └── SdkHubApplication.java           # 应用入口
├── src/main/resources/
│   └── application.yaml                 # 应用配置文件
├── sdkhub-ui/                           # Vue 3 前端项目
│   ├── src/
│   │   ├── api/                         # API 接口封装
│   │   ├── components/                  # 公共组件
│   │   ├── views/                       # 页面视图
│   │   ├── stores/                      # Pinia 状态管理
│   │   ├── locales/                     # 国际化文件
│   │   ├── router/                      # 路由配置
│   │   └── types/                       # TypeScript 类型定义
│   ├── package.json
│   └── vite.config.ts
├── pom.xml                              # Maven 配置
└── README.md
```

## 🛠️ 开发环境

### 前置要求

- **Node.js** >= 18.0
- **Java** >= 21
- **Maven** >= 3.8
- **SDKMAN** - 用于管理 SDK（可选，但推荐安装）

### 安装依赖

```bash
# 1. 安装前端依赖
cd sdkhub-ui
npm install

# 2. 安装后端依赖（在根目录）
mvn clean install
```

### 开发模式

需要同时启动前端和后端服务：

```bash
# 终端 1：启动前端开发服务器
cd sdkhub-ui
npm run dev

# 终端 2：启动 Java 后端（在项目根目录）
mvn spring-boot:run
```

访问地址：

- 前端：http://localhost:5173
- 后端 API：http://localhost:8080

### 构建生产版本

```bash
# 构建前端
cd sdkhub-ui
npm run build

# 构建后端（包含前端静态资源）
cd ..
mvn clean package
```

## 📡 API 接口

### SDK 管理

| 接口        | 方法   | 路径                            | 说明             |
|-----------|------|-------------------------------|----------------|
| 获取所有 SDK  | GET  | `/api/sdks`                   | 获取可用 SDK 列表    |
| 获取 SDK 版本 | GET  | `/api/sdks/{sdkId}/versions`  | 获取指定 SDK 的所有版本 |
| 安装 SDK    | POST | `/api/sdks/{sdkId}/install`   | 安装指定版本的 SDK    |
| 卸载 SDK    | POST | `/api/sdks/{sdkId}/uninstall` | 卸载指定版本的 SDK    |
| 切换版本      | POST | `/api/sdks/{sdkId}/use`       | 临时切换到指定版本      |
| 设置默认      | POST | `/api/sdks/{sdkId}/default`   | 设置默认版本         |
| 已安装列表     | GET  | `/api/sdks/installed`         | 获取已安装的 SDK     |
| 当前版本      | GET  | `/api/sdks/{sdkId}/current`   | 获取当前使用的版本      |
| SDKMAN 状态 | GET  | `/api/sdks/status`            | 检查 SDKMAN 安装状态 |

### 配置管理

| 接口   | 方法   | 路径            | 说明     |
|------|------|---------------|--------|
| 获取配置 | GET  | `/api/config` | 获取当前配置 |
| 更新配置 | POST | `/api/config` | 更新配置信息 |

## 📝 使用说明

### 首页 - SDK 列表

- 浏览所有可用的 SDK，以卡片形式展示
- 左侧边栏按分类筛选（Java、编程语言、构建工具等）
- 顶部搜索栏可搜索 SDK 名称、ID 或描述
- 点击 SDK 卡片查看版本详情，进行安装/卸载/切换操作

### 已安装页面

- 查看已安装的 SDK 列表
- 快速切换当前使用的版本
- 卸载不需要的版本

### 设置页面

- **主题设置**：切换亮色/暗色/自动主题
- **语言设置**：切换中文/英文界面
- **路径配置**：修改 SDK 安装路径
- **SDKMAN 状态**：查看 SDKMAN 安装状态
- **关于**：查看应用版本信息

## 🔧 配置说明

### 后端配置 (application.yaml)

```yaml
sdkhub:
  # SDK 安装路径（默认 ~/.sdkman）
  sdk-path: ~/.sdkman
  # 缓存路径
  cache-path: ~/.sdkhub/cache
  # 自动检查更新
  auto-check-update: true

server:
  port: 8080
```

### 前端配置

前端配置文件位于 `sdkhub-ui/src/locales/`，可添加新的语言支持：

- `zh-CN.ts` - 简体中文
- `en-US.ts` - English

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

- [SDKMAN!](https://sdkman.io/) - 灵感来源和底层工具
- [Vue.js](https://vuejs.org/) - 前端框架
- [Element Plus](https://element-plus.org/) - UI 组件库
- [Spring Boot](https://spring.io/projects/spring-boot) - 后端框架

---

<p align="center">
  Made with ❤️ by SdkHub Team
</p>
