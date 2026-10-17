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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.AutomationConfig
import com.example.data.I18n
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight

@Composable
fun AutomationRulesScreen(
  language: AppLanguage,
  config: AutomationConfig,
  onUpdateConfig: (Int, Int, String, Double) -> Unit,
  onToggleEmergencyStop: () -> Unit,
  modifier: Modifier = Modifier
) {
  var maxPosts by remember { mutableFloatStateOf(config.maxPostsPerSitePerDay.toFloat()) }
  var minScore by remember { mutableFloatStateOf(config.minQualityScore.toFloat()) }
  var isAutoPilot by remember { mutableStateOf(config.isAutoPilotRunning) }

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
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = I18n.t("auto_title", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = I18n.t("auto_subtitle", language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Emergency Kill Switch Card
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (config.isEmergencyStop) RoseError.copy(alpha = 0.15f)
              else MaterialTheme.colorScheme.surfaceVariant
            ),
            border = BorderStroke(1.dp, if (config.isEmergencyStop) RoseError else Color.Transparent)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (config.isEmergencyStop) RoseError else EmeraldSuccess),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = if (config.isEmergencyStop) Icons.Default.Block else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = if (config.isEmergencyStop) I18n.t("dash_emergency_stopped", language)
                    else I18n.t("dash_auto_running", language),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (config.isEmergencyStop) RoseError else EmeraldSuccess
                  )
                  Text(
                    text = if (language == AppLanguage.VI) "Dừng khẩn cấp toàn bộ tác vụ" else "Emergency kill-switch",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }

              Button(
                onClick = onToggleEmergencyStop,
                colors = ButtonDefaults.buttonColors(
                  containerColor = if (config.isEmergencyStop) EmeraldSuccess else RoseError
                ),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text(
                  text = if (config.isEmergencyStop) (if (language == AppLanguage.VI) "Khôi phục" else "Resume")
                  else (if (language == AppLanguage.VI) "Dừng ngay" else "Stop Now"),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    // 2. Rule: Max Posts per site per day
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = I18n.t("auto_rule_max_posts", language),
              style = MaterialTheme.typography.bodyMedium,
              fontWeight = FontWeight.Bold
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = MaterialTheme.colorScheme.primaryContainer
            ) {
              Text(
                text = "${maxPosts.toInt()} ${if (language == AppLanguage.VI) "bài/ngày" else "posts/day"}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Slider(
            value = maxPosts,
            onValueChange = {
              maxPosts = it
              onUpdateConfig(it.toInt(), minScore.toInt(), config.activeTimeWindow, config.monthlyBudgetCapUsd)
            },
            valueRange = 1f..10f,
            steps = 8,
            colors = SliderDefaults.colors(
              thumbColor = WpBlueLight,
              activeTrackColor = WpBlueLight
            ),
            modifier = Modifier.testTag("slider_max_posts")
          )
          Text(
            text = if (language == AppLanguage.VI)
              "Khuyến nghị: 2-4 bài/ngày để giữ tín hiệu tự nhiên, không bị Google Sandbox."
            else
              "Recommended: 2-4 posts/day to maintain natural search indexing velocity.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // 3. Rule: Min Quality Gate Score
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = I18n.t("auto_rule_min_score", language),
              style = MaterialTheme.typography.bodyMedium,
              fontWeight = FontWeight.Bold
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = EmeraldSuccess.copy(alpha = 0.15f)
            ) {
              Text(
                text = "≥ ${minScore.toInt()} / 100",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldSuccess,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Slider(
            value = minScore,
            onValueChange = {
              minScore = it
              onUpdateConfig(maxPosts.toInt(), it.toInt(), config.activeTimeWindow, config.monthlyBudgetCapUsd)
            },
            valueRange = 70f..95f,
            steps = 5,
            colors = SliderDefaults.colors(
              thumbColor = EmeraldSuccess,
              activeTrackColor = EmeraldSuccess
            ),
            modifier = Modifier.testTag("slider_min_score")
          )
          Text(
            text = if (language == AppLanguage.VI)
              "Chỉ các bài viết đạt trên điểm số này mới được tự động đưa vào lịch đăng WordPress."
            else
              "Only articles scoring above this threshold will automatically proceed to publishing.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // 4. Rule: Publishing Window & Safe hours
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Schedule, contentDescription = null, tint = WpBlueLight, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = I18n.t("auto_rule_active_hours", language),
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Khung giờ chạy: 07:00 - 22:00 (Giờ Việt Nam / ICT)",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (language == AppLanguage.VI)
              "Tự động dừng xuất bản vào ban đêm (22h - 7h) để phòng ngừa spam tín hiệu."
            else
              "Halts publishing during nighttime (22:00 - 07:00) to emulate natural human publishing.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}
