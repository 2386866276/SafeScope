package com.safescope.scanner.scanner

import android.os.Environment
import com.safescope.scanner.model.RiskLevel
import com.safescope.scanner.model.SuspiciousFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SuspiciousFileScanner {

    private val MT2_DIRECTORIES = listOf(
        "MT2",
        "mt2",
        "MT管理器",
        "mt管理器",
        "Backup",
        "backup",
        "TWRP",
        "twrp",
        "Magisk",
        "magisk",
        "Xposed",
        "xposed",
        "LSPosed",
        "lsposed",
        "zygisk",
        "mods",
        "module"
    )

    private val SUSPICIOUS_FILENAMES = listOf(
        "su",
        "busybox",
        "magisk",
        "xposed",
        "substrate",
        "frida",
        "gdb",
        "strace",
        "ltrace",
        "tcpdump",
        "wireshark",
        "burp",
        "sqlmap",
        "nmap",
        "metasploit",
        "payload",
        "reverse_shell",
        "backdoor",
        "rootkit",
        "keylogger",
        "spyware",
        "malware",
        "exploit",
        "inject",
        "hook",
        "bypass",
        "crack",
        "patch",
        "破解",
        "外挂",
        "辅助",
        "脚本",
        "脱壳",
        "dump",
        "dex",
        "oat",
        "vdex",
        "art",
        "boot",
        "recovery",
        "ramdisk"
    )

    private val SUSPICIOUS_EXTENSIONS = listOf(
        ".sh", ".bash", ".zsh", ".fish",
        ".py", ".pyc", ".pyo",
        ".pl", ".rb", ".php",
        ".so", ".dylib", ".dll",
        ".exe", ".bat", ".cmd", ".ps1", ".vbs",
        ".jar", ".apk", ".dex",
        ".zip", ".tar", ".gz", ".bz2", ".xz",
        ".7z", ".rar",
        ".db", ".sqlite", ".sqlite3",
        ".log", ".txt", ".conf", ".config",
        ".xml", ".json", ".yaml", ".yml"
    )

    fun scanSuspiciousFiles(baseDir: File = Environment.getExternalStorageDirectory()): List<SuspiciousFile> {
        val results = mutableListOf<SuspiciousFile>()
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        try {
            scanDirectory(baseDir, results, timestamp, depth = 0)
        } catch (e: SecurityException) {
            // 忽略无权限访问的目录
        }

        return results.sortedByDescending { it.riskLevel.ordinal }
    }

    private fun scanDirectory(
        directory: File,
        results: MutableList<SuspiciousFile>,
        timestamp: String,
        depth: Int
    ) {
        if (depth > 5) return // 限制扫描深度

        try {
            val files = directory.listFiles() ?: return

            for (file in files) {
                if (file.isDirectory) {
                    if (isMT2Related(file)) {
                        results.add(
                            SuspiciousFile(
                                path = file.absolutePath,
                                fileName = file.name,
                                fileSize = 0,
                                lastModified = file.lastModified(),
                                riskLevel = RiskLevel.HIGH,
                                reason = "发现 MT2/管理器相关目录",
                                isMT2Related = true
                            )
                        )
                    }
                    scanDirectory(file, results, timestamp, depth + 1)
                } else {
                    val risk = evaluateFileRisk(file)
                    if (risk != null) {
                        results.add(risk)
                    }
                }
            }
        } catch (e: SecurityException) {
            // 忽略无权限访问的目录
        }
    }

    private fun isMT2Related(file: File): Boolean {
        return MT2_DIRECTORIES.any { file.name.contains(it, ignoreCase = true) }
    }

    private fun evaluateFileRisk(file: File): SuspiciousFile? {
        val fileName = file.name.lowercase()
        val ext = file.extension.lowercase()

        val isSuspiciousName = SUSPICIOUS_FILENAMES.any { fileName.contains(it, ignoreCase = true) }
        val isSuspiciousExt = SUSPICIOUS_EXTENSIONS.any { ".$ext".equals(it, ignoreCase = true) }
        val isHidden = file.name.startsWith(".")
        val isSystemFile = fileName == "build.prop" || fileName == "default.prop" || fileName == "init.rc"

        val riskLevel = when {
            isSystemFile -> RiskLevel.HIGH
            isMT2Related(file) -> RiskLevel.HIGH
            isSuspiciousName && isSuspiciousExt -> RiskLevel.CRITICAL
            isSuspiciousName -> RiskLevel.MEDIUM
            isSuspiciousExt && isHidden -> RiskLevel.MEDIUM
            isHidden -> RiskLevel.LOW
            else -> null
        } ?: return null

        val reason = buildFileReason(file, isSuspiciousName, isSuspiciousExt, isHidden, isSystemFile)

        return SuspiciousFile(
            path = file.absolutePath,
            fileName = file.name,
            fileSize = file.length(),
            lastModified = file.lastModified(),
            riskLevel = riskLevel,
            reason = reason,
            isMT2Related = isMT2Related(file)
        )
    }

    private fun buildFileReason(
        file: File,
        isSuspiciousName: Boolean,
        isSuspiciousExt: Boolean,
        isHidden: Boolean,
        isSystemFile: Boolean
    ): String {
        val reasons = mutableListOf<String>()
        if (isSystemFile) reasons.add("系统关键文件")
        if (isMT2Related(file)) reasons.add("MT2/管理器相关")
        if (isSuspiciousName) reasons.add("文件名可疑")
        if (isSuspiciousExt) reasons.add("扩展名敏感")
        if (isHidden) reasons.add("隐藏文件")
        return reasons.joinToString("; ")
    }
}