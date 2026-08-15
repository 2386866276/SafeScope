package com.safescope.scanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhonelinkSetup
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class FeatureItem(
    val icon: ImageVector,
    val title: String,
    val desc: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAppScan: () -> Unit = {},
    onNavigateToUSBDebug: () -> Unit = {},
    onNavigateToFileScan: () -> Unit = {},
    onNavigateToDeviceInfo: () -> Unit = {},
    onNavigateToShAnalysis: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToFilePicker: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val features = listOf(
        FeatureItem(Icons.Default.Apps, "风险应用扫描", "检测已安装应用的风险等级、权限和来源", onNavigateToAppScan),
        FeatureItem(Icons.Default.BugReport, "USB 调试检测", "检测 USB 调试状态和连接风险", onNavigateToUSBDebug),
        FeatureItem(Icons.Default.FolderOpen, "MT2/异常文件扫描", "扫描 MT2 目录、异常文件和可疑脚本", onNavigateToFileScan),
        FeatureItem(Icons.Default.PhonelinkSetup, "设备信息", "查看设备详细信息、系统状态和安全状态", onNavigateToDeviceInfo),
        FeatureItem(Icons.Default.Code, "Shell 脚本分析", "深度分析 .sh 脚本，识别恶意代码并支持 Base64/Hex 解码", onNavigateToShAnalysis),
        FeatureItem(Icons.Default.FolderOpen, "文件扫描", "选择 .sh/.zip 文件进行安全扫描，查看高亮运行日志", onNavigateToFilePicker),
        FeatureItem(Icons.Default.Settings, "设置", "深色模式、配色方案与主题切换", onNavigateToSettings),
        FeatureItem(Icons.Default.Info, "关于软件", "作者信息、联系方式、隐私政策与开源声明", onNavigateToAbout)
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "SafeScope",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "安全检测中心",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            features.forEach { item ->
                Card(
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.desc,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}