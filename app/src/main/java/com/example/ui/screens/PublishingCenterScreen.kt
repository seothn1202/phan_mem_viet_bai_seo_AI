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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.I18n
import com.example.data.PublishQueueItem
import com.example.data.PublishStatus
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.WpBlueLight

@Composable
fun PublishingCenterScreen(
  language: AppLanguage,
  queueItems: List<PublishQueueItem>,
  onPublishNow: (String) -> Unit,
  onRetry: (String) -> Unit,
  onViewLiveUrl: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }

  val filteredItems = when (selectedTab) {
    0 -> queueItems.filter { it.status == PublishStatus.SCHEDULED || it.status == PublishStatus.PUBLISHING || it.status == PublishStatus.FAILED }
    else -> queueItems.filter { it.status == PublishStatus.PUBLISHED }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Header & Summary
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
            text = I18n.t("pub_title", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = I18n.t("pub_subtitle", language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(12.dp))

          TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
          ) {
            Tab(
              selected = selectedTab == 0,
              onClick = { selectedTab = 0 },
              text = {
                Text(
                  text = "${I18n.t("pub_tab_queue", language)} (${queueItems.count { it.status != PublishStatus.PUBLISHED }})",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            )
            Tab(
              selected = selectedTab == 1,
              onClick = { selectedTab = 1 },
              text = {
                Text(
                  text = "${I18n.t("pub_tab_history", language)} (${queueItems.count { it.status == PublishStatus.PUBLISHED }})",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            )
          }
        }
      }
    }

    // 2. Queue or Published Items List
    items(filteredItems, key = { it.id }) { item ->
      PublishQueueCard(
        item = item,
        language = language,
        onPublishNow = { onPublishNow(item.id) },
        onRetry = { onRetry(item.id) },
        onViewLiveUrl = { item.liveUrl?.let { onViewLiveUrl(it) } }
      )
    }
  }
}

@Composable
private fun PublishQueueCard(
  item: PublishQueueItem,
  language: AppLanguage,
  onPublishNow: () -> Unit,
  onRetry: () -> Unit,
  onViewLiveUrl: () -> Unit
) {
  val (statusText, statusColor) = when (item.status) {
    PublishStatus.SCHEDULED -> Pair(if (language == AppLanguage.VI) "ĐÃ LÊN LỊCH" else "SCHEDULED", AmberWarning)
    PublishStatus.PUBLISHING -> Pair(if (language == AppLanguage.VI) "ĐANG ĐĂNG..." else "PUBLISHING...", WpBlueLight)
    PublishStatus.PUBLISHED -> Pair(if (language == AppLanguage.VI) "ĐÃ XUẤT BẢN" else "PUBLISHED", EmeraldSuccess)
    PublishStatus.FAILED -> Pair(if (language == AppLanguage.VI) "LỖI" else "FAILED", RoseError)
  }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = item.articleTitle,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Website: ${item.siteName} • ${item.scheduledTime}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        StatusBadge(text = statusText, color = statusColor)
      }

      // If failed, show error message
      if (item.errorMessage != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = RoseError.copy(alpha = 0.15f),
          border = BorderStroke(1.dp, RoseError.copy(alpha = 0.3f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = item.errorMessage,
            fontSize = 11.sp,
            color = RoseError,
            modifier = Modifier.padding(8.dp)
          )
        }
      }

      // If published, show live URL link
      if (item.liveUrl != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(EmeraldSuccess.copy(alpha = 0.1f))
            .clickable { onViewLiveUrl() }
            .padding(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = item.liveUrl,
            fontSize = 11.sp,
            color = EmeraldSuccess,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
          )
          Icon(Icons.Default.OpenInBrowser, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Action buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (item.status == PublishStatus.FAILED) {
          Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AmberWarning),
            modifier = Modifier.testTag("btn_retry_pub_${item.id}")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = I18n.t("pub_btn_retry", language), fontSize = 11.sp)
          }
        } else if (item.status == PublishStatus.SCHEDULED) {
          Button(
            onClick = onPublishNow,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.testTag("btn_publish_now_${item.id}")
          ) {
            Icon(Icons.Default.Publish, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = I18n.t("pub_btn_publish_now", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
