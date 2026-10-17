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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Warning
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
import com.example.data.CompetitorScan
import com.example.data.I18n
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent

@Composable
fun CompetitorAnalysisScreen(
  language: AppLanguage,
  scanData: CompetitorScan,
  onGenerateOutrankingArticle: () -> Unit,
  modifier: Modifier = Modifier
) {
  var urlInput by remember { mutableStateOf(scanData.url) }
  var isScanning by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. URL Input Box
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
            text = I18n.t("comp_title", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = I18n.t("comp_subtitle", language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = urlInput,
            onValueChange = { urlInput = it },
            placeholder = { Text(I18n.t("comp_url_placeholder", language), fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_competitor_url"),
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = scanData.analyzedAt,
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
              onClick = { isScanning = true },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("btn_inspect_competitor")
            ) {
              Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = I18n.t("comp_analyze_btn", language), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. High-Level Metrics
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        ScanStatBadge(
          label = I18n.t("comp_stats_words", language),
          value = "${scanData.wordCount}",
          icon = Icons.Default.TextFields,
          color = WpBlueLight,
          modifier = Modifier.weight(1f)
        )
        ScanStatBadge(
          label = I18n.t("comp_stats_headings", language),
          value = "${scanData.headingsCount}",
          icon = Icons.Default.FormatListNumbered,
          color = VioletAi,
          modifier = Modifier.weight(1f)
        )
        ScanStatBadge(
          label = I18n.t("comp_stats_images", language),
          value = "${scanData.imagesCount}",
          icon = Icons.Default.Image,
          color = EmeraldSuccess,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Title & Meta Preview
    item {
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
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "SERP Title Tag",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "${scanData.titleLength} chars",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (scanData.titleLength <= 65) EmeraldSuccess else AmberWarning
            )
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = scanData.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = WpBlueLight
          )

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Meta Description",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = scanData.metaDescription,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }

    // 4. Topic Gaps Card (Critical for outranking!)
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = null, tint = AmberWarning, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = I18n.t("comp_topic_gaps", language),
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          scanData.topicGaps.forEach { gap ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.Top
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(EmeraldSuccess)
                  .padding(top = 6.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = gap,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = onGenerateOutrankingArticle,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primary,
              contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("btn_outrank_article")
          ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = I18n.t("comp_action_outrank", language),
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // 5. Headings Structure Tree
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = I18n.t("comp_heading_tree", language),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(10.dp))

          scanData.headings.forEach { h ->
            val indent = when (h.level) {
              "H1" -> 0.dp
              "H2" -> 14.dp
              else -> 28.dp
            }
            val tagColor = when (h.level) {
              "H1" -> WpBlueLight
              "H2" -> VioletAi
              else -> WpCyanAccent
            }

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(start = indent, top = 4.dp, bottom = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = tagColor.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, tagColor.copy(alpha = 0.4f))
              ) {
                Text(
                  text = h.level,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = tagColor,
                  modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = h.text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ScanStatBadge(
  label: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier.padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
      Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}
