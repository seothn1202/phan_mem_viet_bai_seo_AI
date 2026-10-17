package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.BacklogStatus
import com.example.data.I18n
import com.example.data.KeywordPriority
import com.example.data.SearchIntent
import com.example.data.WordPressSite
import com.example.data.WritingBacklogItem
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent

@Composable
fun WritingBacklogScreen(
  language: AppLanguage,
  backlogItems: List<WritingBacklogItem>,
  sites: List<WordPressSite>,
  onOpenImportDialog: () -> Unit,
  onOpenAddManualDialog: () -> Unit,
  onWriteNow: (WritingBacklogItem) -> Unit,
  onDeleteBacklogItem: (String) -> Unit,
  onUpdateStatus: (String, BacklogStatus) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }

  val filteredItems = when (selectedTab) {
    1 -> backlogItems.filter { it.status == BacklogStatus.PENDING }
    2 -> backlogItems.filter { it.status == BacklogStatus.DRAFTING }
    3 -> backlogItems.filter { it.status == BacklogStatus.COMPLETED }
    else -> backlogItems
  }

  val pendingCount = backlogItems.count { it.status == BacklogStatus.PENDING }
  val draftingCount = backlogItems.count { it.status == BacklogStatus.DRAFTING }
  val completedCount = backlogItems.count { it.status == BacklogStatus.COMPLETED }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Header & Actions Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = I18n.t("backlog_title", language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = I18n.t("backlog_subtitle", language),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(EmeraldSuccess.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.ListAlt, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(20.dp))
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Two Primary Action Buttons: Import Sheets & Add Manual
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = onOpenImportDialog,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = EmeraldSuccess
              ),
              modifier = Modifier
                .weight(1.2f)
                .testTag("btn_import_sheet")
            ) {
              Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (language == AppLanguage.VI) "Nhập từ Trang Tính" else "Import Sheets",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            OutlinedButton(
              onClick = onOpenAddManualDialog,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_add_backlog_manual")
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (language == AppLanguage.VI) "Thêm từ khóa" else "Add Item",
                fontSize = 11.sp
              )
            }
          }
        }
      }
    }

    // 2. Tab Filter Row
    item {
      ScrollableTabRow(
        selectedTabIndex = selectedTab,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.clip(RoundedCornerShape(10.dp))
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("${I18n.t("backlog_tab_all", language)} (${backlogItems.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("${I18n.t("backlog_tab_pending", language)} ($pendingCount)", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 2,
          onClick = { selectedTab = 2 },
          text = { Text("${I18n.t("backlog_tab_drafting", language)} ($draftingCount)", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 3,
          onClick = { selectedTab = 3 },
          text = { Text("${I18n.t("backlog_tab_completed", language)} ($completedCount)", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
      }
    }

    // 3. Empty State if no items in current tab
    if (filteredItems.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.TableChart, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = if (language == AppLanguage.VI) "Danh sách đang trống" else "Backlog is empty",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (language == AppLanguage.VI)
                "Bấm 'Nhập từ Trang Tính' để dán bảng từ khóa từ Google Sheets hoặc Excel vào kế hoạch viết."
              else
                "Click 'Import Sheets' to paste keywords from Google Sheets or Excel.",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = onOpenImportDialog,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess)
            ) {
              Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (language == AppLanguage.VI) "Dán từ Google Sheets / Excel ngay" else "Paste from Sheets",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // 4. Backlog Items Cards
    items(filteredItems, key = { it.id }) { item ->
      BacklogItemCard(
        item = item,
        language = language,
        onWriteNow = { onWriteNow(item) },
        onDelete = { onDeleteBacklogItem(item.id) },
        onToggleStatus = {
          val next = when (item.status) {
            BacklogStatus.PENDING -> BacklogStatus.DRAFTING
            BacklogStatus.DRAFTING -> BacklogStatus.COMPLETED
            BacklogStatus.COMPLETED -> BacklogStatus.PENDING
          }
          onUpdateStatus(item.id, next)
        }
      )
    }
  }
}

@Composable
private fun BacklogItemCard(
  item: WritingBacklogItem,
  language: AppLanguage,
  onWriteNow: () -> Unit,
  onDelete: () -> Unit,
  onToggleStatus: () -> Unit
) {
  val (statusText, statusColor) = when (item.status) {
    BacklogStatus.PENDING -> Pair(if (language == AppLanguage.VI) "CHỜ VIẾT" else "PENDING", AmberWarning)
    BacklogStatus.DRAFTING -> Pair(if (language == AppLanguage.VI) "ĐANG VIẾT" else "DRAFTING", WpBlueLight)
    BacklogStatus.COMPLETED -> Pair(if (language == AppLanguage.VI) "ĐÃ XONG" else "COMPLETED", EmeraldSuccess)
  }

  val intentColor = when (item.intent) {
    SearchIntent.INFORMATIONAL -> WpBlueLight
    SearchIntent.COMMERCIAL -> VioletAi
    SearchIntent.TRANSACTIONAL -> EmeraldSuccess
    SearchIntent.NAVIGATIONAL -> AmberWarning
  }

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
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = item.keyword,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "Web đích: ${item.targetSiteName} • Cụm: ${item.cluster}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = statusColor.copy(alpha = 0.15f),
          border = BorderStroke(1.dp, statusColor.copy(alpha = 0.4f)),
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onToggleStatus() }
        ) {
          Text(
            text = statusText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = statusColor,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Meta tags row: Intent, Source, Priority
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(4.dp),
            color = intentColor.copy(alpha = 0.15f)
          ) {
            Text(
              text = item.intent.name,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = intentColor,
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = item.source,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Text(
          text = "Ưu tiên: ${item.priority.name}",
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold,
          color = if (item.priority == KeywordPriority.HIGH) EmeraldSuccess else MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Action row: Write Now & Delete
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onDelete,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
            modifier = Modifier.size(18.dp)
          )
        }

        Button(
          onClick = onWriteNow,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
          ),
          modifier = Modifier.testTag("btn_write_backlog_${item.id}")
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (language == AppLanguage.VI) "Soạn thảo bài viết AI" else "Write with AI",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
