package com.safescope.scanner.scanner

import com.safescope.scanner.model.LogEntry
import com.safescope.scanner.model.ThreatLevel
import java.io.InputStream
import java.util.zip.ZipInputStream

object ZipScanner {

    private val DANGEROUS_EXTENSIONS = setOf(
        ".sh", ".bat", ".cmd", ".ps1", ".vbs", ".js", ".jar", ".exe", ".dll", ".so", ".dylib"
    )
    private val WARNING_EXTENSIONS = setOf(
        ".py", ".pl", ".rb", ".php", ".asp", ".aspx", ".jsp"
    )

    fun scan(inputStream: InputStream, fileName: String): List<LogEntry> {
        val entries = mutableListOf<LogEntry>()
        val zis = ZipInputStream(inputStream)
        val timestamp = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
        var fileCount = 0
        var suspiciousCount = 0
        val suspiciousFiles = mutableListOf<String>()

        try {
            generateSequence { zis.nextEntry }.forEach { entry ->
                fileCount++
                val name = entry.name
                val ext = name.substringAfterLast('.', "").lowercase().let { if (it.isEmpty()) "" else ".$it" }

                when {
                    DANGEROUS_EXTENSIONS.contains(ext) -> {
                        suspiciousCount++
                        suspiciousFiles.add(name)
                        entries.add(
                            LogEntry(
                                timestamp = timestamp,
                                fileName = fileName,
                                title = "发现可执行文件: $name",
                                detail = "ZIP 内嵌可执行脚本/程序，可能包含恶意代码",
                                level = ThreatLevel.DANGEROUS,
                                isKeywordHighlighted = true
                            )
                        )
                    }
                    WARNING_EXTENSIONS.contains(ext) -> {
                        suspiciousCount++
                        suspiciousFiles.add(name)
                        entries.add(
                            LogEntry(
                                timestamp = timestamp,
                                fileName = fileName,
                                title = "发现脚本文件: $name",
                                detail = "ZIP 内嵌脚本文件，建议手动审查内容",
                                level = ThreatLevel.WARNING,
                                isKeywordHighlighted = false
                            )
                        )
                    }
                    name.contains("../") || name.contains("..\\") -> {
                        suspiciousCount++
                        suspiciousFiles.add(name)
                        entries.add(
                            LogEntry(
                                timestamp = timestamp,
                                fileName = fileName,
                                title = "路径穿越风险: $name",
                                detail = "文件名包含 '../' 或 '..\\'，可能存在路径穿越攻击",
                                level = ThreatLevel.DANGEROUS,
                                isKeywordHighlighted = true
                            )
                        )
                    }
                    name.startsWith("/") || (name.length > 1 && name[1] == ':') -> {
                        suspiciousCount++
                        suspiciousFiles.add(name)
                        entries.add(
                            LogEntry(
                                timestamp = timestamp,
                                fileName = fileName,
                                title = "绝对路径文件: $name",
                                detail = "ZIP 内嵌文件使用绝对路径，可能导致覆盖系统文件",
                                level = ThreatLevel.WARNING,
                                isKeywordHighlighted = true
                            )
                        )
                    }
                }

                if (entry.size > 10 * 1024 * 1024) {
                    entries.add(
                        LogEntry(
                            timestamp = timestamp,
                            fileName = fileName,
                            title = "大文件警告: $name",
                            detail = "文件大小 ${entry.size / 1024}KB，超过 10MB 阈值",
                            level = ThreatLevel.WARNING
                        )
                    )
                }
            }
        } catch (e: Exception) {
            entries.add(
                LogEntry(
                    timestamp = timestamp,
                    fileName = fileName,
                    title = "解压异常",
                    detail = "无法完整解析 ZIP: ${e.message ?: "未知错误"}",
                    level = ThreatLevel.WARNING
                )
            )
        } finally {
            zis.close()
        }

        if (fileCount == 0) {
            entries.add(
                LogEntry(
                    timestamp = timestamp,
                    fileName = fileName,
                    title = "空压缩包",
                    detail = "ZIP 文件内没有文件条目",
                    level = ThreatLevel.INFO
                )
            )
        } else if (suspiciousCount == 0) {
            entries.add(
                LogEntry(
                    timestamp = timestamp,
                    fileName = fileName,
                    title = "未发现明显风险",
                    detail = "共扫描 $fileCount 个文件，未发现可执行脚本或路径风险",
                    level = ThreatLevel.SAFE
                )
            )
        } else {
            entries.add(
                0,
                LogEntry(
                    timestamp = timestamp,
                    fileName = fileName,
                    title = "发现 $suspiciousCount 个可疑项",
                    detail = "可疑文件: ${suspiciousFiles.take(5).joinToString(", ")}${if (suspiciousFiles.size > 5) "..." else ""}",
                    level = ThreatLevel.DANGEROUS
                )
            )
        }

        return entries
    }
}