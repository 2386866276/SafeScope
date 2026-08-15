# SafeScope

SafeScope is a local security scanning tool that runs on Android devices. Without relying on any cloud service, it helps users inspect potential risks on their device, including suspicious apps, abnormal shell scripts, and dangerous USB debugging states.

## Features

- **App threat scan**: Identify apps on the device that may pose a risk.
- **Device info scan**: Collect and display the device runtime status and security-related configuration.
- **Shell script analysis**: Parse and detect suspicious shell script content.
- **Suspicious file scan**: Scan storage directories for abnormal files.
- **USB debugging detection**: Warn about the security risks introduced by enabled USB debugging.
- **Archive scan**: Analyze files contained inside Zip archives.

## Tech Stack

| Item | Description |
| --- | --- |
| Language | Kotlin |
| UI Framework | Jetpack Compose (Material 3) |
| Build Tool | Gradle (Kotlin DSL) |
| Minimum Version | Android 5.0 (API 21) |
| Target Version | Android 13 (API 35) |
| Compile Version | Android 14 (API 36) |

## Permissions

The app requests the following permissions to perform local scanning:

- `READ_PHONE_STATE`: Read device status information.
- `READ_EXTERNAL_STORAGE` / `MANAGE_EXTERNAL_STORAGE`: Read storage to scan files.
- `PACKAGE_USAGE_STATS`: Access app usage statistics (requires additional user authorization).
- `SYSTEM_ALERT_WINDOW`: Display an overlay prompt.

All scanning is performed locally on the device. No data is uploaded to any server.

## Build and Run

Open this project in Android Studio, then run it on a connected device or emulator. You can also build from the command line:

```bash
./gradlew assembleDebug
```

## Project Structure

```
app/src/main/kotlin/com/safescope/scanner/
├── MainActivity.kt          # App entry point and navigation
├── model/                   # Data models
├── scanner/                 # Scanner implementations
└── ui/screens/              # Feature screens
```

## Privacy

A privacy policy dialog is shown on first launch and must be read and accepted before use. All scan results are stored only on the device.

## License

This project is licensed under the [MIT License](LICENSE). See the `LICENSE` file for details.
