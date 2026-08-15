package com.safescope.scanner.scanner

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import com.safescope.scanner.model.RiskLevel
import com.safescope.scanner.model.USBDebugStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object USBDebugDetector {

    fun detectUSBDebugStatus(context: Context): USBDebugStatus {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val isEnabled = try {
            Settings.Secure.getInt(context.contentResolver, Settings.Secure.ADB_ENABLED, 0) == 1
        } catch (e: Exception) {
            false
        }

        val riskLevel = when {
            isEnabled -> RiskLevel.HIGH
            else -> RiskLevel.NONE
        }

        val detail = when {
            isEnabled -> "USB 调试已开启，攻击者可能通过 USB 连接获取设备控制权"
            else -> "USB 调试未开启，设备相对安全"
        }

        return USBDebugStatus(
            isEnabled = isEnabled,
            detectionTime = timestamp,
            riskLevel = riskLevel,
            detail = detail
        )
    }

    fun isUSBConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_USB)
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnected == true
        }
    }
}