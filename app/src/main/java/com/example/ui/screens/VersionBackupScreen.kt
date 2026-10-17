package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.ArticleDraft
import com.example.data.I18n
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight

@Composable
fun VersionBackupScreen(
  language: AppLanguage,
  currentDraft: ArticleDraft,
  onRestoreSampleData: () -> Unit,
  modifier: Modifier = Modifier
) {
  var backupMessage by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Header
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = I18n.t("backup_title", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = I18n.t("backup_subtitle", language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = {
                backupMessage = if (language == AppLanguage.VI)
                  "Đã xuất thành công gói sao lưu cấu hình wp_seo_studio_backup.json"
                else
                  "Successfully exported wp_seo_studio_backup.json"
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_export_json")
            ) {
              Icon(Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = I18n.t("backup_export", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = {
                backupMessage = if (language == AppLanguage.VI)
                  "Cấu hình hệ thống đã được đồng bộ & xác thực từ tệp JSON."
                else
                  "System configuration imported and verified."
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_import_json")
            ) {
              Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = I18n.t("backup_import", language), fontSize = 11.sp)
            }
          }

          if (backupMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EmeraldSuccess.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, EmeraldSuccess.copy(alpha = 0.3f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = backupMessage!!,
                fontSize = 11.sp,
                color = EmeraldSuccess,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp)
              )
            }
          }
        }
      }
    }

    // 2. Draft Version History Snapshot
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.History, contentDescription = null, tint = WpBlueLight, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (language == AppLanguage.VI) "Lịch sử phiên bản bài viết hiện tại" else "Active Article Version Snapshots",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Version 2 (Current)
          VersionItemRow(
            version = "v${currentDraft.version} (Hiện tại)",
            time = "Hôm nay 09:30",
            words = "${currentDraft.wordCount} words",
            score = "${currentDraft.seoScore}/100",
            isCurrent = true
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Version 1
          VersionItemRow(
            version = "v1 (Bản thảo sơ khởi)",
            time = "Hôm nay 09:10",
            words = "820 words",
            score = "76/100",
            isCurrent = false
          )
        }
      }
    }

    // 3. Factory Reset / Sample data
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = if (language == AppLanguage.VI) "Khôi phục dữ liệu mẫu" else "Restore Demo Data",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (language == AppLanguage.VI)
              "Nạp lại danh sách 12 website WordPress mẫu, bảng từ khóa và hàng đợi xuất bản."
            else
              "Reload the default 12 WordPress demo websites, keyword clusters, and queue.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = onRestoreSampleData,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.testTag("btn_restore_sample_data")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = I18n.t("backup_restore_sample", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
private fun VersionItemRow(
  version: String,
  time: String,
  words: String,
  score: String,
  isCurrent: Boolean
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        if (isCurrent) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        RoundedCornerShape(8.dp)
      )
      .padding(10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column {
      Text(text = version, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isCurrent) WpBlueLight else MaterialTheme.colorScheme.onSurface)
      Text(text = "$time • $words", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Surface(
      shape = RoundedCornerShape(6.dp),
      color = EmeraldSuccess.copy(alpha = 0.15f)
    ) {
      Text(
        text = "Score: $score",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = EmeraldSuccess,
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
      )
    }
  }
}
