# SafeScope

SafeScope 是一款執行於 Android 裝置上的本機安全掃描工具。在不依賴任何雲端服務的前提下，它協助使用者檢查裝置中潛在的風險，包含可疑應用程式、異常的 Shell 指令碼，以及危險的 USB 偵錯狀態等。

## 功能特性

- **應用程式威脅掃描**：辨識裝置上可能具有風險的應用程式。
- **裝置資訊掃描**：收集並展示裝置的運作狀態與安全相關設定。
- **Shell 指令碼分析**：解析並偵測可疑的 Shell 指令碼內容。
- **可疑檔案掃描**：掃描儲存目錄中的異常檔案。
- **USB 偵錯偵測**：提醒 USB 偵錯開啟可能帶來的安全風險。
- **壓縮檔掃描**：分析 Zip 壓縮檔內的檔案。

## 技術架構

| 項目 | 說明 |
| --- | --- |
| 開發語言 | Kotlin |
| 介面框架 | Jetpack Compose（Material 3） |
| 建置工具 | Gradle（Kotlin DSL） |
| 最低版本 | Android 5.0（API 21） |
| 目標版本 | Android 13（API 35） |
| 編譯版本 | Android 14（API 36） |

## 權限說明

應用程式會申請下列權限以完成本機掃描：

- `READ_PHONE_STATE`：讀取裝置狀態資訊。
- `READ_EXTERNAL_STORAGE` / `MANAGE_EXTERNAL_STORAGE`：讀取儲存空間以掃描檔案。
- `PACKAGE_USAGE_STATS`：取得應用程式使用狀況（需使用者額外授權）。
- `SYSTEM_ALERT_WINDOW`：顯示懸浮層提示。

所有掃描皆於裝置本機完成，不會將任何資料上傳至伺服器。

## 建置與執行

使用 Android Studio 開啟本專案，連接裝置或啟動模擬器後執行。亦可透過命令列建置：

```bash
./gradlew assembleDebug
```

## 專案結構

```
app/src/main/kotlin/com/safescope/scanner/
├── MainActivity.kt          # 應用程式進入點與導覽
├── model/                   # 資料模型
├── scanner/                 # 各類掃描器實作
└── ui/screens/              # 各功能畫面
```

## 隱私

應用程式首次啟動時會顯示隱私政策對話框，需閱讀並同意後方可使用。所有掃描結果僅儲存於本機。

## 授權條款

本專案採用 [MIT 授權條款](LICENSE)。詳見 `LICENSE` 檔案。
