package com.safescope.scanner.model

import androidx.compose.ui.graphics.Color

enum class RiskLevel(val label: String, val color: Color, val bgColor: Color) {
    NONE("无风险", Color(0xFF4CAF50), Color(0xFFE8F5E9)),
    LOW("低风险", Color(0xFF8BC34A), Color(0xFFF1F8E9)),
    MEDIUM("中风险", Color(0xFFFF9800), Color(0xFFFFF3E0)),
    HIGH("高风险", Color(0xFFF44336), Color(0xFFFFEBEE)),
    CRITICAL("严重", Color(0xFFD50000), Color(0xFFFFCDD2))
}

data class AppThreatInfo(
    val packageName: String,
    val appName: String,
    val versionName: String?,
    val riskLevel: RiskLevel,
    val riskReason: String,
    val installer: String? = null,
    val isSystemApp: Boolean = false,
    val permissions: List<String> = emptyList()
)

data class USBDebugStatus(
    val isEnabled: Boolean,
    val detectionTime: String,
    val riskLevel: RiskLevel,
    val detail: String
)

data class SuspiciousFile(
    val path: String,
    val fileName: String,
    val fileSize: Long,
    val lastModified: Long,
    val riskLevel: RiskLevel,
    val reason: String,
    val isMT2Related: Boolean = false
)

data class DeviceInfo(
    val brand: String,
    val model: String,
    val androidVersion: String,
    val apiLevel: Int,
    val sdkInt: Int,
    val fingerprint: String,
    val serial: String?,
    val imei: String?,
    val phoneNumber: String?,
    val cpuAbi: List<String>,
    val totalMemory: Long,
    val availableMemory: Long,
    val isRooted: Boolean,
    val isUSBConnected: Boolean,
    val isUSBDebugEnabled: Boolean,
    val batteryLevel: Int,
    val securityPatch: String,
    val baseband: String,
    val buildTime: String
)