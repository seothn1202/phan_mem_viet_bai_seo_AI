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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.I18n
import com.example.data.KeywordItem
import com.example.data.KeywordPriority
import com.example.data.SearchIntent
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KeywordResearchScreen(
  language: AppLanguage,
  keywords: List<KeywordItem>,
  onAddKeyword: (KeywordItem) -> Unit,
  onAnalyzeTopic: (String, String) -> Unit,
  onAddToBacklog: (KeywordItem) -> Unit = {},
  onDraftWithAi: (KeywordItem) -> Unit,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  var inputTopic by remember { mutableStateOf("") }
  var activeFilterQuery by remember { mutableStateOf("") }
  var selectedIntentFilter by remember { mutableStateOf<SearchIntent?>(null) }
  var selectedMarket by remember { mutableStateOf("VN") }
  var isAnalyzing by remember { mutableStateOf(false) }
  var successNotice by remember { mutableStateOf<String?>(null) }

  val sampleSuggestions = listOf(
    "WordPress SEO",
    "Hosting Giá Rẻ",
    "AI Content",
    "Du Lịch Đà Nẵng",
    "Xe Điện EV",
    "iPhone 16 Pro"
  )

  fun triggerAnalysis(topicToAnalyze: String) {
    val topic = topicToAnalyze.trim()
    if (topic.isBlank()) return
    isAnalyzing = true
    successNotice = null
    coroutineScope.launch {
      delay(600) // Brief realistic animation
      onAnalyzeTopic(topic, selectedMarket)
      activeFilterQuery = topic
      isAnalyzing = false
      successNotice = if (language == AppLanguage.VI)
        "Đã phân tích và tạo cụm từ khóa thành công cho: '$topic'!"
      else
        "Successfully clustered keywords for: '$topic'!"
    }
  }

  val filteredKeywords = keywords.filter { item ->
    val matchesIntent = selectedIntentFilter == null || item.intent == selectedIntentFilter
    val matchesQuery = activeFilterQuery.isBlank() ||
      item.keyword.contains(activeFilterQuery, ignoreCase = true) ||
      item.cluster.contains(activeFilterQuery, ignoreCase = true) ||
      item.seedTopic.contains(activeFilterQuery, ignoreCase = true)
    matchesIntent && matchesQuery
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Search & Topic Analysis Form
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
            value = inputTopic,
            onValueChange = {
              inputTopic = it
              activeFilterQuery = it
            },
            placeholder = { Text(I18n.t("kw_input_placeholder", language), fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
              if (inputTopic.isNotEmpty()) {
                IconButton(onClick = {
                  inputTopic = ""
                  activeFilterQuery = ""
                  successNotice = null
                }) {
                  Icon(Icons.Default.Clear, contentDescription = "Clear", modifier = Modifier.size(18.dp))
                }
              }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
              triggerAnalysis(inputTopic)
            }),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_keyword_search"),
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Market Selector Chips & Primary Analyze Button
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
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
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                  )
                }
              }
            }

            // Prominent Analyze Button
            Button(
              onClick = { triggerAnalysis(inputTopic) },
              enabled = !isAnalyzing && inputTopic.isNotBlank(),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
              ),
              modifier = Modifier.testTag("btn_analyze_keywords")
            ) {
              if (isAnalyzing) {
                CircularProgressIndicator(
                  modifier = Modifier.size(14.dp),
                  color = Color.White,
                  strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (language == AppLanguage.VI) "Đang quét..." else "Scanning...",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              } else {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                  text = I18n.t("kw_search_btn", language),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Suggested Topic Chips
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = if (language == AppLanguage.VI) "Gợi ý:" else "Quick:",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            LazyRow(
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              items(sampleSuggestions) { suggestion ->
                Surface(
                  shape = RoundedCornerShape(14.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                  border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                  modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                      inputTopic = suggestion
                      activeFilterQuery = suggestion
                      triggerAnalysis(suggestion)
                    }
                ) {
                  Text(
                    text = suggestion,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
              }
            }
          }

          // Progress indicator when scanning
          AnimatedVisibility(visible = isAnalyzing) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
              LinearProgressIndicator(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(4.dp)
                  .clip(RoundedCornerShape(2.dp)),
                color = VioletAi
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (language == AppLanguage.VI)
                  "Đang phân tích cụm ngữ nghĩa, Search Volume & Intent từ Google Search Console..."
                else
                  "Analyzing semantic clusters, Search Volume & Intent via SERP feeds...",
                fontSize = 10.sp,
                color = VioletAi
              )
            }
          }

          // Success notification banner
          if (successNotice != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EmeraldSuccess.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, EmeraldSuccess.copy(alpha = 0.3f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = successNotice!!,
                fontSize = 11.sp,
                color = EmeraldSuccess,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
              )
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

    // 3. Keyword list count, Clear Filter & Disclaimer
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "${filteredKeywords.size} ${if (language == AppLanguage.VI) "từ khóa hiển thị" else "keywords found"}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          if (activeFilterQuery.isNotBlank() || selectedIntentFilter != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                  inputTopic = ""
                  activeFilterQuery = ""
                  selectedIntentFilter = null
                  successNotice = null
                }
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(10.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (language == AppLanguage.VI) "Hiện tất cả" else "Show All",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Info, contentDescription = null, tint = WpCyanAccent, modifier = Modifier.size(12.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (language == AppLanguage.VI) "Volume & KD ước tính" else "Est. Volume & KD",
            fontSize = 10.sp,
            color = WpCyanAccent
          )
        }
      }
    }

    // 4. Empty State Card if no results match
    if (filteredKeywords.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          ),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(VioletAi.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = VioletAi,
                modifier = Modifier.size(24.dp)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = if (inputTopic.isNotBlank())
                (if (language == AppLanguage.VI) "Chưa có từ khóa cho: '$inputTopic'" else "No cached keywords for: '$inputTopic'")
              else
                (if (language == AppLanguage.VI) "Chưa có từ khóa phù hợp bộ lọc" else "No matching keywords"),
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = if (inputTopic.isNotBlank())
                (if (language == AppLanguage.VI)
                  "Hệ thống sẵn sàng quét Google SERP API và tạo cụm 7 từ khóa mới chuẩn SEO cho chủ đề này."
                else
                  "Click below to scan live SERP data and generate 7 SEO clustered keywords.")
              else
                (if (language == AppLanguage.VI)
                  "Hãy nhập chủ đề bạn muốn SEO hoặc chọn từ khóa gợi ý phía trên."
                else
                  "Enter a topic above or pick a quick suggestion to analyze."),
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            if (inputTopic.isNotBlank()) {
              Spacer(modifier = Modifier.height(14.dp))
              Button(
                onClick = { triggerAnalysis(inputTopic) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VioletAi),
                modifier = Modifier.testTag("btn_empty_state_analyze")
              ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (language == AppLanguage.VI) "🚀 Phân tích ngay: '$inputTopic'" else "🚀 Analyze Now: '$inputTopic'",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            } else {
              Spacer(modifier = Modifier.height(12.dp))
              OutlinedButton(
                onClick = {
                  inputTopic = "WordPress SEO"
                  activeFilterQuery = "WordPress SEO"
                  triggerAnalysis("WordPress SEO")
                },
                shape = RoundedCornerShape(8.dp)
              ) {
                Text(text = if (language == AppLanguage.VI) "Phân tích thử 'WordPress SEO'" else "Try 'WordPress SEO'", fontSize = 11.sp)
              }
            }
          }
        }
      }
    }

    // 5. Keyword Cards List
    items(filteredKeywords, key = { it.id }) { item ->
      KeywordCard(
        item = item,
        language = language,
        onAddToBacklog = {
          onAddToBacklog(item)
          successNotice = if (language == AppLanguage.VI)
            "Đã thêm '${item.keyword}' vào Danh sách cần viết bài!"
          else
            "Added '${item.keyword}' to Writing Backlog!"
        },
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
  onAddToBacklog: () -> Unit = {},
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

      // Actions: Add to Writing Backlog OR Draft with AI
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = onAddToBacklog,
          shape = RoundedCornerShape(8.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("btn_add_backlog_kw_${item.id}")
        ) {
          Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (language == AppLanguage.VI) "+ DS cần viết" else "+ Backlog",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Button(
          onClick = onDraftWithAi,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
          ),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
          modifier = Modifier
            .weight(1f)
            .testTag("btn_draft_kw_${item.id}")
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = I18n.t("kw_action_write", language),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
