package com.safescope.scanner.model

import androidx.compose.ui.graphics.Color

enum class ThreatLevel(val label: String, val color: Color, val bgColor: Color) {
    SAFE("安全", Color(0xFF4CAF50), Color(0xFFE8F5E9)),
    WARNING("警告", Color(0xFFFF9800), Color(0xFFFFF3E0)),
    DANGEROUS("危险", Color(0xFFF44336), Color(0xFFFFEBEE)),
    INFO("信息", Color(0xFF2196F3), Color(0xFFE3F2FD));

    companion object {
        fun fromSeverity(severity: Int): ThreatLevel = when (severity) {
            in 0..2 -> SAFE
            3 -> WARNING
            in 4..5 -> DANGEROUS
            else -> INFO
        }
    }
}

data class LogEntry(
    val id: Long = System.currentTimeMillis(),
    val timestamp: String,
    val fileName: String,
    val title: String,
    val detail: String,
    val level: ThreatLevel,
    val lineNumber: Int? = null,
    val isKeywordHighlighted: Boolean = false
)

data class ScanResult(
    val id: Long = System.currentTimeMillis(),
    val fileName: String,
    val fileSize: Long,
    val fileType: String,
    val scanTime: String,
    val isSafe: Boolean,
    val overallLevel: ThreatLevel,
    val logEntries: List<LogEntry> = emptyList(),
    val summary: String = ""
)