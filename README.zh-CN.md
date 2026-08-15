# SafeScope

SafeScope 是一款运行在 Android 设备上的本地安全扫描工具。它在不依赖任何云端服务的前提下，帮助用户检查设备中存在的潜在风险，包括可疑应用、异常的 Shell 脚本、危险的 USB 调试状态等。

## 功能特性

- **应用威胁扫描**：识别设备上可能存在风险的应用。
- **设备信息扫描**：收集并展示设备的运行状态与安全相关配置。
- **Shell 脚本分析**：解析并检测可疑的 Shell 脚本内容。
- **可疑文件扫描**：扫描存储目录中的异常文件。
- **USB 调试检测**：提示 USB 调试开启可能带来的安全风险。
- **压缩包扫描**：分析 Zip 压缩包内的文件。

## 技术栈

| 项目 | 说明 |
| --- | --- |
| 开发语言 | Kotlin |
| 界面框架 | Jetpack Compose（Material 3） |
| 构建工具 | Gradle（Kotlin DSL） |
| 最低版本 | Android 5.0（API 21） |
| 目标版本 | Android 13（API 35） |
| 编译版本 | Android 14（API 36） |

## 权限说明

应用会申请以下权限以完成本地扫描：

- `READ_PHONE_STATE`：读取设备状态信息。
- `READ_EXTERNAL_STORAGE` / `MANAGE_EXTERNAL_STORAGE`：读取存储空间以扫描文件。
- `PACKAGE_USAGE_STATS`：获取应用使用情况（需用户额外授权）。
- `SYSTEM_ALERT_WINDOW`：显示悬浮层提示。

所有扫描均在设备本地完成，不会将任何数据上传到服务器。

## 构建与运行

使用 Android Studio 打开本项目，连接设备或启动模拟器后运行。也可通过命令行构建：

```bash
./gradlew assembleDebug
```

## 项目结构

```
app/src/main/kotlin/com/safescope/scanner/
├── MainActivity.kt          # 应用入口与导航
├── model/                   # 数据模型
├── scanner/                 # 各类扫描器实现
└── ui/screens/              # 各功能界面
```

## 隐私

应用首次启动时会展示隐私政策弹窗，需阅读并同意后方可使用。所有扫描结果仅保存在本机。

## 许可证

本项目采用 [MIT 许可证](LICENSE)。详见 `LICENSE` 文件。
