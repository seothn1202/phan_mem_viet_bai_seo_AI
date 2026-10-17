package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ActivityLog
import com.example.data.ActivityType
import com.example.data.AppLanguage
import com.example.data.AutomationConfig
import com.example.data.CostSummary
import com.example.data.I18n
import com.example.data.WordPressSite
import com.example.ui.components.MetricCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent

@Composable
fun DashboardScreen(
  language: AppLanguage,
  sites: List<WordPressSite>,
  automationConfig: AutomationConfig,
  costSummary: CostSummary,
  activities: List<ActivityLog>,
  onEmergencyStopClick: () -> Unit,
  onNavigateToKeywords: () -> Unit,
  onNavigateToWriter: () -> Unit,
  onNavigateToPublishing: () -> Unit,
  onNavigateToWebsites: () -> Unit,
  modifier: Modifier = Modifier
) {
  val activeSitesCount = sites.count { it.isAutoPublishEnabled }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Hero Automation Status Banner
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (automationConfig.isEmergencyStop)
            MaterialTheme.colorScheme.errorContainer
          else
            MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(
          1.dp,
          if (automationConfig.isEmergencyStop) RoseError
          else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = I18n.t("dash_overview_title", language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${sites.size} ${I18n.t("dash_stat_sites", language)} • $activeSitesCount ${if (language == AppLanguage.VI) "đang tự động" else "auto active"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            // Big Emergency Kill Switch
            Button(
              onClick = onEmergencyStopClick,
              colors = ButtonDefaults.buttonColors(
                containerColor = if (automationConfig.isEmergencyStop) RoseError else MaterialTheme.colorScheme.error
              ),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.testTag("btn_hero_emergency_stop")
            ) {
              Icon(
                imageVector = Icons.Default.Block,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (automationConfig.isEmergencyStop)
                  I18n.t("dash_emergency_stopped", language)
                else
                  I18n.t("dash_emergency_stop", language),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Pipeline progress dots
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                RoundedCornerShape(10.dp)
              )
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            PipelineStage(
              number = "1",
              title = if (language == AppLanguage.VI) "Nghiên cứu" else "Research",
              isActive = !automationConfig.isEmergencyStop
            )
            Text("→", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            PipelineStage(
              number = "2",
              title = if (language == AppLanguage.VI) "Viết AI" else "AI Draft",
              isActive = !automationConfig.isEmergencyStop
            )
            Text("→", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            PipelineStage(
              number = "3",
              title = "Quality Gate",
              isActive = !automationConfig.isEmergencyStop
            )
            Text("→", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            PipelineStage(
              number = "4",
              title = if (language == AppLanguage.VI) "Xuất bản" else "Publish",
              isActive = !automationConfig.isEmergencyStop
            )
          }
        }
      }
    }

    // 2. Metrics Grid (2 columns)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = I18n.t("dash_stat_sites", language),
          value = "${sites.size}",
          subtitle = "10 Online • 1 Auth Err",
          icon = Icons.Default.Language,
          accentColor = WpBlueLight,
          modifier = Modifier.weight(1f)
        )
        MetricCard(
          title = I18n.t("dash_stat_keywords", language),
          value = "1,480",
          subtitle = "+120 GSC Sync",
          icon = Icons.Default.Search,
          accentColor = WpCyanAccent,
          modifier = Modifier.weight(1f)
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = I18n.t("dash_stat_published", language),
          value = "298",
          subtitle = "Hôm nay: 15 bài",
          icon = Icons.Default.CloudDone,
          accentColor = EmeraldSuccess,
          modifier = Modifier.weight(1f)
        )
        MetricCard(
          title = I18n.t("dash_stat_queue", language),
          value = "18",
          subtitle = "Khung giờ: 14h-21h",
          icon = Icons.Default.HourglassTop,
          accentColor = AmberWarning,
          modifier = Modifier.weight(1f)
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = I18n.t("dash_stat_failed", language),
          value = "1",
          subtitle = "504 Timeout (Retry 1)",
          icon = Icons.Default.Error,
          accentColor = RoseError,
          modifier = Modifier.weight(1f)
        )
        MetricCard(
          title = I18n.t("dash_stat_cost", language),
          value = "$${costSummary.currentSpendUsd}",
          subtitle = I18n.t("dash_stat_limit", language),
          icon = Icons.Default.MonetizationOn,
          accentColor = VioletAi,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Quick Action Launchers
    item {
      SectionHeader(
        title = if (language == AppLanguage.VI) "Thao tác nhanh" else "Quick Actions"
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        QuickActionButton(
          label = I18n.t("dash_quick_action_keyword", language),
          icon = Icons.Default.Search,
          color = WpCyanAccent,
          onClick = onNavigateToKeywords,
          modifier = Modifier.weight(1f)
        )
        QuickActionButton(
          label = I18n.t("dash_quick_action_writer", language),
          icon = Icons.Default.AutoAwesome,
          color = VioletAi,
          onClick = onNavigateToWriter,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        QuickActionButton(
          label = I18n.t("dash_quick_action_queue", language),
          icon = Icons.Default.Schedule,
          color = AmberWarning,
          onClick = onNavigateToPublishing,
          modifier = Modifier.weight(1f)
        )
        QuickActionButton(
          label = I18n.t("dash_quick_action_add_site", language),
          icon = Icons.Default.Add,
          color = WpBlueLight,
          onClick = onNavigateToWebsites,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 4. Recent Activities Feed
    item {
      SectionHeader(
        title = I18n.t("dash_recent_activity", language),
        actionText = if (language == AppLanguage.VI) "Làm mới" else "Refresh",
        onActionClick = {}
      )
    }

    items(activities) { activity ->
      ActivityItemCard(activity = activity, language = language)
    }
  }
}

@Composable
private fun PipelineStage(
  number: String,
  title: String,
  isActive: Boolean
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Box(
      modifier = Modifier
        .size(18.dp)
        .clip(CircleShape)
        .background(if (isActive) EmeraldSuccess else MaterialTheme.colorScheme.outline),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = number,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
    }
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = title,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}

@Composable
private fun QuickActionButton(
  label: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() },
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(16.dp)
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
  }
}

@Composable
private fun ActivityItemCard(
  activity: ActivityLog,
  language: AppLanguage
) {
  val icon = when (activity.type) {
    ActivityType.PUBLISHED -> Icons.Default.CloudDone
    ActivityType.AI_GENERATED -> Icons.Default.AutoAwesome
    ActivityType.GATE_AUDIT -> Icons.Default.CheckCircle
    ActivityType.ERROR -> Icons.Default.Error
    ActivityType.WARNING -> Icons.Default.TrendingUp
    ActivityType.SYSTEM -> Icons.Default.Language
  }

  val color = when (activity.type) {
    ActivityType.PUBLISHED, ActivityType.GATE_AUDIT -> EmeraldSuccess
    ActivityType.AI_GENERATED -> VioletAi
    ActivityType.ERROR -> RoseError
    ActivityType.WARNING -> AmberWarning
    ActivityType.SYSTEM -> WpBlueLight
  }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = if (language == AppLanguage.VI) activity.titleVi else activity.titleEn,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "${activity.siteName} • ${activity.timestamp}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
