package com.safescope.scanner

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.safescope.scanner.ui.screens.AboutScreen
import com.safescope.scanner.ui.screens.AppScanScreen
import com.safescope.scanner.ui.screens.DeviceInfoScreen
import com.safescope.scanner.ui.screens.FileScanPickerScreen
import com.safescope.scanner.ui.screens.FileScanScreen
import com.safescope.scanner.ui.screens.HomeScreen
import com.safescope.scanner.ui.screens.PrivacyPolicyDialog
import com.safescope.scanner.ui.screens.SettingsScreen
import com.safescope.scanner.ui.screens.ShAnalysisScreen
import com.safescope.scanner.ui.screens.USBDebugScreen
import com.safescope.scanner.ui.theme.AppColorTheme
import com.safescope.scanner.ui.theme.AppThemeMode
import com.safescope.scanner.ui.theme.SafeScopeTheme
import com.safescope.scanner.ui.theme.createTypography

class MainActivity : ComponentActivity() {

    private val PREFS_NAME = "safescope_prefs"
    private val KEY_POLICY_ACCEPTED = "policy_accepted"

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // 权限结果回调，静默处理
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val typography = createTypography(this)
        enableEdgeToEdge()

        // 请求运行时权限
        requestRuntimePermissions()

        setContent {
            SafeScopeTheme(
                themeMode = AppThemeMode.SYSTEM,
                colorTheme = AppColorTheme.MONET,
                typography = typography
            ) {
                SafeScopeApp()
            }
        }
    }

    private fun requestRuntimePermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_PHONE_STATE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Android 11+ 需要特殊处理
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

@Composable
fun SafeScopeApp() {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf("home") }
    var themeMode by remember { mutableStateOf(AppThemeMode.SYSTEM) }
    var colorTheme by remember { mutableStateOf(AppColorTheme.MONET) }

    // 用 SharedPreferences 持久化弹窗状态，避免每次启动都弹
    val prefs = remember { context.getSharedPreferences("safescope_prefs", 0) }
    var policyAccepted by remember { mutableStateOf(prefs.getBoolean("policy_accepted", false)) }

    if (!policyAccepted) {
        PrivacyPolicyDialog(
            onAgree = {
                prefs.edit().putBoolean("policy_accepted", true).apply()
                policyAccepted = true
            },
            onDisagree = {
                prefs.edit().putBoolean("policy_accepted", false).apply()
                policyAccepted = false
            }
        )
        return
    }

    SafeScopeTheme(
        themeMode = themeMode,
        colorTheme = colorTheme
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentScreen) {
                "home" -> HomeScreen(
                    onNavigateToAppScan = { currentScreen = "app_scan" },
                    onNavigateToUSBDebug = { currentScreen = "usb_debug" },
                    onNavigateToFileScan = { currentScreen = "file_scan" },
                    onNavigateToDeviceInfo = { currentScreen = "device_info" },
                    onNavigateToShAnalysis = { currentScreen = "sh_analysis" },
                    onNavigateToAbout = { currentScreen = "about" },
                    onNavigateToFilePicker = { currentScreen = "file_picker" },
                    onNavigateToSettings = { currentScreen = "settings" }
                )
                "app_scan" -> AppScanScreen(
                    onBack = { currentScreen = "home" }
                )
                "usb_debug" -> USBDebugScreen(
                    onBack = { currentScreen = "home" }
                )
                "file_scan" -> FileScanScreen(
                    onBack = { currentScreen = "home" }
                )
                "device_info" -> DeviceInfoScreen(
                    onBack = { currentScreen = "home" }
                )
                "sh_analysis" -> ShAnalysisScreen(
                    onBack = { currentScreen = "home" }
                )
                "about" -> AboutScreen(
                    onBack = { currentScreen = "home" },
                    onOpenPrivacyPolicy = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ifdian.net/a/zephyr7"))
                        context.startActivity(intent)
                    }
                )
                "file_picker" -> FileScanPickerScreen(
                    onBack = { currentScreen = "home" }
                )
                "settings" -> SettingsScreen(
                    onBack = { currentScreen = "home" },
                    currentThemeMode = themeMode,
                    currentColorTheme = colorTheme,
                    onThemeModeChange = { themeMode = it },
                    onColorThemeChange = { colorTheme = it }
                )
            }
        }
    }
}