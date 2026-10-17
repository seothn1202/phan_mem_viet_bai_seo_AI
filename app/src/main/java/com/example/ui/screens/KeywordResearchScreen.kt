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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.I18n
import com.example.data.KeywordItem
import com.example.data.KeywordPriority
import com.example.data.SearchIntent
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent

@Composable
fun KeywordResearchScreen(
  language: AppLanguage,
  keywords: List<KeywordItem>,
  onAddKeyword: (KeywordItem) -> Unit,
  onDraftWithAi: (KeywordItem) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedIntentFilter by remember { mutableStateOf<SearchIntent?>(null) }
  var selectedMarket by remember { mutableStateOf("VN") }

  val filteredKeywords = keywords.filter {
    (selectedIntentFilter == null || it.intent == selectedIntentFilter) &&
      (searchQuery.isBlank() || it.keyword.contains(searchQuery, ignoreCase = true) || it.cluster.contains(searchQuery, ignoreCase = true))
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Search & Filter Bar
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
            text = I18n.t("kw_title", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = I18n.t("kw_subtitle", language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(I18n.t("kw_input_placeholder", language), fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_keyword_search"),
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Market Selector Chips
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = I18n.t("kw_market", language),
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            listOf("VN", "US", "UK", "Global").forEach { market ->
              val isSelected = selectedMarket == market
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .clickable { selectedMarket = market }
              ) {
                Text(
                  text = market,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // 2. Search Intent Filters
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        item {
          FilterPill(
            label = if (language == AppLanguage.VI) "Tất cả Intent" else "All Intents",
            isSelected = selectedIntentFilter == null,
            onClick = { selectedIntentFilter = null }
          )
        }
        item {
          FilterPill(
            label = "Informational",
            isSelected = selectedIntentFilter == SearchIntent.INFORMATIONAL,
            onClick = { selectedIntentFilter = SearchIntent.INFORMATIONAL }
          )
        }
        item {
          FilterPill(
            label = "Commercial",
            isSelected = selectedIntentFilter == SearchIntent.COMMERCIAL,
            onClick = { selectedIntentFilter = SearchIntent.COMMERCIAL }
          )
        }
        item {
          FilterPill(
            label = "Transactional",
            isSelected = selectedIntentFilter == SearchIntent.TRANSACTIONAL,
            onClick = { selectedIntentFilter = SearchIntent.TRANSACTIONAL }
          )
        }
      }
    }

    // 3. Keyword list count & Disclaimer
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${filteredKeywords.size} ${if (language == AppLanguage.VI) "từ khóa tìm thấy" else "keywords found"}",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Info, contentDescription = null, tint = WpCyanAccent, modifier = Modifier.size(12.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (language == AppLanguage.VI) "Search Volume có đánh dấu ước tính" else "Est. Search Volume flagged",
            fontSize = 10.sp,
            color = WpCyanAccent
          )
        }
      }
    }

    // 4. Keyword Cards
    items(filteredKeywords, key = { it.id }) { item ->
      KeywordCard(
        item = item,
        language = language,
        onDraftWithAi = { onDraftWithAi(item) }
      )
    }
  }
}

@Composable
private fun FilterPill(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(20.dp),
    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
    border = BorderStroke(
      1.dp,
      if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    ),
    modifier = Modifier
      .clip(RoundedCornerShape(20.dp))
      .clickable { onClick() }
  ) {
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    )
  }
}

@Composable
private fun KeywordCard(
  item: KeywordItem,
  language: AppLanguage,
  onDraftWithAi: () -> Unit
) {
  val (intentColor, intentBg) = when (item.intent) {
    SearchIntent.INFORMATIONAL -> Pair(WpBlueLight, WpBlueLight.copy(alpha = 0.15f))
    SearchIntent.COMMERCIAL -> Pair(VioletAi, VioletAi.copy(alpha = 0.15f))
    SearchIntent.TRANSACTIONAL -> Pair(EmeraldSuccess, EmeraldSuccess.copy(alpha = 0.15f))
    SearchIntent.NAVIGATIONAL -> Pair(AmberWarning, AmberWarning.copy(alpha = 0.15f))
  }

  val kdColor = when {
    item.kdPercent < 30 -> EmeraldSuccess
    item.kdPercent < 60 -> AmberWarning
    else -> RoseError
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
            text = item.keyword,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "${item.cluster} • ${item.source}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = intentBg,
          border = BorderStroke(1.dp, intentColor.copy(alpha = 0.4f))
        ) {
          Text(
            text = item.intent.name,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = intentColor,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Volume & KD metrics
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = I18n.t("kw_col_volume", language), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(
            text = "${item.estimatedVolume}/mo",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Column {
          Text(text = I18n.t("kw_col_kd", language), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(kdColor)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${item.kdPercent}%",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = kdColor
            )
          }
        }

        Column {
          Text(text = I18n.t("kw_col_priority", language), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(
            text = item.priority.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (item.priority == KeywordPriority.HIGH) EmeraldSuccess else MaterialTheme.colorScheme.onSurface
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Action: Send to AI Writer
      Button(
        onClick = onDraftWithAi,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("btn_draft_kw_${item.id}")
      ) {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = I18n.t("kw_action_write", language),
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
