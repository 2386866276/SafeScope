package com.safescope.scanner.model

import androidx.compose.ui.graphics.Color

enum class ScriptAnalysisAction(val label: String) {
    MONITOR("监控"),
    WARN("警告"),
    BLOCK("拦截"),
    NONE("无需处理")
}

data class DecodedSnippet(
    val encodingType: String,
    val original: String,
    val decoded: String,
    val riskHint: String
)

data class ShScriptAnalysis(
    val fileName: String,
    val fileSize: Long,
    val lineCount: Int,
    val encodingType: String,
    val decodedSnippets: List<DecodedSnippet>,
    val suspiciousPatterns: List<String>,
    val overallRisk: ThreatLevel,
    val suggestedAction: ScriptAnalysisAction,
    val summary: String
)