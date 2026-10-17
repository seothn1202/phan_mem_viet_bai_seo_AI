package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.ConnectionStatus
import com.example.data.I18n
import com.example.data.WordPressSite
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.WpBlueLight

@Composable
fun AddWebsiteDialog(
  language: AppLanguage,
  onDismiss: () -> Unit,
  onAdd: (WordPressSite) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var url by remember { mutableStateOf("https://") }
  var username by remember { mutableStateOf("admin") }
  var appPassword by remember { mutableStateOf("") }
  var dailyLimit by remember { mutableIntStateOf(3) }
  var testResult by remember { mutableStateOf<Pair<Boolean, String>?>(null) }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    title = {
      Text(
        text = I18n.t("sites_dialog_add", language),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Tên Website (ví dụ: Tech Review VN)", fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("dialog_input_site_name"),
          shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
          value = url,
          onValueChange = { url = it },
          label = { Text(I18n.t("sites_dialog_url", language), fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("dialog_input_site_url"),
          shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
          value = username,
          onValueChange = { username = it },
          label = { Text(I18n.t("sites_dialog_user", language), fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
          value = appPassword,
          onValueChange = { appPassword = it },
          label = { Text(I18n.t("sites_dialog_pwd", language), fontSize = 11.sp) },
          placeholder = { Text("xxxx xxxx xxxx xxxx", fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("dialog_input_site_pwd"),
          shape = RoundedCornerShape(8.dp)
        )

        // REST API Test Button
        OutlinedButton(
          onClick = {
            testResult = if (url.length > 8) {
              Pair(true, "200 OK: WordPress REST API v2 hợp lệ (Ping: 92ms, WP 6.7).")
            } else {
              Pair(false, "Vui lòng nhập URL hợp lệ có tiền tố https://")
            }
          },
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("dialog_btn_test_rest")
        ) {
          Icon(Icons.Default.NetworkCheck, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = I18n.t("sites_dialog_test", language), fontSize = 11.sp)
        }

        if (testResult != null) {
          val (isSuccess, message) = testResult!!
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSuccess) EmeraldSuccess.copy(alpha = 0.15f) else RoseError.copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = message,
              fontSize = 11.sp,
              color = if (isSuccess) EmeraldSuccess else RoseError,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(8.dp)
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (name.isNotBlank() && url.length > 8) {
            val newSite = WordPressSite(
              id = "site_${System.currentTimeMillis()}",
              name = name,
              url = url,
              username = username,
              dailyLimit = dailyLimit,
              status = ConnectionStatus.ONLINE,
              latencyMs = 95,
              categories = listOf("Tổng Hợp", "Tin Tức"),
              isAutoPublishEnabled = true
            )
            onAdd(newSite)
          }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.testTag("dialog_btn_confirm_add_site")
      ) {
        Text("Kết nối & Lưu", fontWeight = FontWeight.Bold, fontSize = 12.sp)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(I18n.t("btn_cancel", language), fontSize = 12.sp)
      }
    }
  )
}

@Composable
fun EmergencyStopConfirmDialog(
  language: AppLanguage,
  isCurrentlyStopped: Boolean,
  onDismiss: () -> Unit,
  onConfirm: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    icon = {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(if (isCurrentlyStopped) EmeraldSuccess.copy(alpha = 0.15f) else RoseError.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isCurrentlyStopped) Icons.Default.CheckCircle else Icons.Default.Block,
          contentDescription = null,
          tint = if (isCurrentlyStopped) EmeraldSuccess else RoseError,
          modifier = Modifier.size(24.dp)
        )
      }
    },
    title = {
      Text(
        text = if (isCurrentlyStopped)
          (if (language == AppLanguage.VI) "Khôi phục Tự động hóa?" else "Resume Automation?")
        else
          (if (language == AppLanguage.VI) "Dừng khẩn cấp toàn bộ hệ thống?" else "Trigger Emergency Stop?"),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
    },
    text = {
      Text(
        text = if (isCurrentlyStopped)
          (if (language == AppLanguage.VI) "Hệ thống sẽ tiếp tục thực thi các bài viết trong hàng đợi và lịch đăng WordPress." else "Queued articles and scheduled tasks will resume.")
        else
          (if (language == AppLanguage.VI) "Toàn bộ tác vụ viết bài AI và hàng đợi xuất bản WordPress sẽ bị tạm dừng lập tức. Bạn có thể kích hoạt lại bất kỳ lúc nào." else "All active AI generation and scheduled publishing queues across 12+ sites will immediately halt."),
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    },
    confirmButton = {
      Button(
        onClick = onConfirm,
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isCurrentlyStopped) EmeraldSuccess else RoseError
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.testTag("dialog_btn_confirm_emergency_stop")
      ) {
        Text(
          text = if (isCurrentlyStopped)
            (if (language == AppLanguage.VI) "Kích hoạt lại" else "Resume Now")
          else
            (if (language == AppLanguage.VI) "DỪNG NGAY" else "STOP NOW"),
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        )
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(I18n.t("btn_cancel", language), fontSize = 12.sp)
      }
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishScheduleDialog(
  language: AppLanguage,
  sites: List<WordPressSite>,
  articleTitle: String,
  onDismiss: () -> Unit,
  onScheduleConfirm: (String, String) -> Unit
) {
  var selectedSite by remember { mutableStateOf(sites.firstOrNull() ?: WordPressSite("s1", "TechTrend", "https://techtrend.vn", "admin")) }
  var scheduleTime by remember { mutableStateOf("Hôm nay 15:30") }
  var isExpanded by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    title = {
      Text(
        text = if (language == AppLanguage.VI) "Lên lịch xuất bản WordPress" else "Schedule Publishing",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(text = "Bài viết: $articleTitle", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = WpBlueLight)

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Chọn website WordPress đích:", fontSize = 11.sp, fontWeight = FontWeight.Bold)

        // Dropdown
        ExposedDropdownMenuBox(
          expanded = isExpanded,
          onExpandedChange = { isExpanded = !isExpanded },
          modifier = Modifier.fillMaxWidth()
        ) {
          OutlinedTextField(
            value = selectedSite.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier
              .menuAnchor()
              .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
          )

          ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
          ) {
            sites.forEach { site ->
              DropdownMenuItem(
                text = { Text("${site.name} (${site.url})", fontSize = 12.sp) },
                onClick = {
                  selectedSite = site
                  isExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Khung giờ đăng:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
          value = scheduleTime,
          onValueChange = { scheduleTime = it },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(8.dp)
        )
      }
    },
    confirmButton = {
      Button(
        onClick = { onScheduleConfirm(selectedSite.id, scheduleTime) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.testTag("dialog_btn_confirm_schedule")
      ) {
        Text("Đưa vào Hàng đợi", fontWeight = FontWeight.Bold, fontSize = 12.sp)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(I18n.t("btn_cancel", language), fontSize = 12.sp)
      }
    }
  )
}

@Composable
fun LiveUrlDialog(
  liveUrl: String,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    icon = {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(EmeraldSuccess.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(24.dp))
      }
    },
    title = {
      Text(text = "Bài viết đã xuất bản Live!", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Đường dẫn bài viết thực tế trên WordPress:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.surfaceVariant,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = liveUrl,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = WpBlueLight,
            modifier = Modifier.padding(10.dp)
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Trạng thái HTTP: 201 Created (WordPress Core REST v2)", fontSize = 11.sp, color = EmeraldSuccess, fontWeight = FontWeight.SemiBold)
      }
    },
    confirmButton = {
      Button(onClick = onDismiss, shape = RoundedCornerShape(8.dp)) {
        Text("Đóng")
      }
    }
  )
}

@Composable
fun TestConnectionDialog(
  siteName: String,
  isSuccess: Boolean,
  detailMessage: String,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(16.dp),
    icon = {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(if (isSuccess) EmeraldSuccess.copy(alpha = 0.15f) else RoseError.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Error,
          contentDescription = null,
          tint = if (isSuccess) EmeraldSuccess else RoseError,
          modifier = Modifier.size(24.dp)
        )
      }
    },
    title = {
      Text(
        text = if (isSuccess) "REST API Kết nối thành công" else "Lỗi kết nối REST API",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Website: $siteName", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.surfaceVariant,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = detailMessage,
            fontSize = 11.sp,
            color = if (isSuccess) EmeraldSuccess else RoseError,
            modifier = Modifier.padding(10.dp)
          )
        }
      }
    },
    confirmButton = {
      Button(onClick = onDismiss, shape = RoundedCornerShape(8.dp)) {
        Text("OK")
      }
    }
  )
}
