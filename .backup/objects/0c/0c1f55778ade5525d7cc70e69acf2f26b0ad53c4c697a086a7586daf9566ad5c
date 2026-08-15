package com.safescope.scanner.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.safescope.scanner.model.LogEntry
import com.safescope.scanner.model.ThreatLevel
import com.safescope.scanner.scanner.ShScanner
import com.safescope.scanner.scanner.ZipScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScanPickerScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var logEntries by remember { mutableStateOf<List<LogEntry>>(emptyList()) }
    var isScanning by remember { mutableStateOf(false) }
    var scannedFileName by remember { mutableStateOf("") }

    val fileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        if (uri != null) {
            isScanning = true; scannedFileName = uri.lastPathSegment ?: uri.toString()
            scope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        if (inputStream != null) {
                            val fileName = uri.lastPathSegment ?: "unknown"
                            logEntries = if (fileName.endsWith(".sh", true)) ShScanner.scan(inputStream, fileName)
                            else if (fileName.endsWith(".zip", true)) ZipScanner.scan(inputStream, fileName)
                            else listOf(LogEntry(timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), fileName = fileName, title = "不支持的文件类型", detail = "请选择 .sh 或 .zip 文件", level = ThreatLevel.INFO))
                            inputStream.close()
                        }
                    } catch (e: Exception) {
                        logEntries = listOf(LogEntry(timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()), fileName = scannedFileName, title = "扫描异常", detail = e.message ?: "未知错误", level = ThreatLevel.WARNING))
                    } finally { isScanning = false }
                }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("文件扫描") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "返回") } }) }
    ) { innerPadding ->
        if (isScanning) {
            Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) { CircularProgressIndicator(); Spacer(Modifier.height(16.dp)); Text("正在扫描...") }
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(vertical = 16.dp)) {
                item {
                    Card(Modifier.fillMaxWidth(), RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(Modifier.padding(20.dp), Arrangement.spacedBy(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Icon(Icons.Default.FolderOpen, null, Modifier.height(24.dp), tint = MaterialTheme.colorScheme.primary)
                                Text("选择文件进行扫描", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                            Text("支持 .sh 和 .zip 文件的安全扫描与解密分析", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Button(onClick = { fileLauncher.launch(arrayOf("text/*", "application/zip", "application/octet-stream")) }, Modifier.fillMaxWidth()) { Text("选择文件") }
                        }
                    }
                }
                if (logEntries.isNotEmpty()) {
                    item { Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) { Text("扫描日志 (${logEntries.size})", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold); Text(scannedFileName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) } }
                    items(logEntries) { LogEntryCard(it) }
                }
            }
        }
    }
}

@Composable
private fun LogEntryCard(entry: LogEntry) {
    Card(Modifier.fillMaxWidth(), RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = entry.level.bgColor.copy(alpha = 0.35f))) {
        Column(Modifier.padding(14.dp), Arrangement.spacedBy(4.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Text(entry.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = entry.level.color)
                Text(entry.timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(entry.detail, style = MaterialTheme.typography.bodySmall, fontFamily = if (entry.isKeywordHighlighted) FontFamily.Monospace else FontFamily.Default, color = if (entry.isKeywordHighlighted) entry.level.color else MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}