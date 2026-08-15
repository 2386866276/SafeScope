package com.safescope.scanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PrivacyPolicyDialog(
    onAgree: () -> Unit,
    onDisagree: () -> Unit
) {
    var remainingSeconds by remember { mutableFloatStateOf(5f) }
    var canAgree by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0f) {
            delay(100L)
            remainingSeconds -= 0.1f
        }
        canAgree = true
    }

    AlertDialog(
        onDismissRequest = onDisagree,
        title = {
            Text(
                text = "隐私政策与用户协议",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "欢迎使用 SafeScope 安全检测应用。在使用本应用前，请仔细阅读以下隐私政策与用户协议：",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "1. 数据收集\n本应用仅在您主动授权的情况下访问设备信息、已安装应用列表和存储文件。所有数据均在本地处理，不会上传至任何服务器。",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "2. 权限说明\n本应用需要以下权限以实现安全检测功能：READ_PHONE_STATE（设备信息）、PACKAGE_USAGE_STATS（应用使用统计）、MANAGE_EXTERNAL_STORAGE（文件扫描）。所有权限均为可选，您可随时在系统设置中撤回。",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "3. 安全声明\n本应用仅供学习与安全研究使用。使用者应遵守当地法律法规，不得将本应用用于非法用途。开发者不对任何滥用行为承担责任。",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "4. 免责声明\n本应用的安全检测结果基于静态分析，无法保证 100% 准确。请结合多种安全工具综合判断。",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "5. 联系方式\n作者：林映雪\n邮箱：2386866276@qq.com",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (!canAgree) {
                    LinearProgressIndicator(
                        progress = { 1f - (remainingSeconds / 5f) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "请等待 ${remainingSeconds.toInt() + 1} 秒后可同意...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "您已阅读完毕，可以继续操作。",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onAgree,
                enabled = canAgree,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("同意并继续")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDisagree) {
                Text("不同意")
            }
        }
    )
}