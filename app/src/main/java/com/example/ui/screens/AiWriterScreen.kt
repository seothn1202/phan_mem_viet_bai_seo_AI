package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontFamily
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
fun AiWriterScreen(
  language: AppLanguage,
  draft: ArticleDraft,
  onSaveDraft: (String, String, String, String) -> Unit,
  onNavigateToQualityGate: () -> Unit,
  onQueueForPublishing: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  var titleText by remember { mutableStateOf(draft.title) }
  var metaDescText by remember { mutableStateOf(draft.metaDescription) }
  var markdownContent by remember { mutableStateOf(draft.contentMarkdown) }
  var htmlContent by remember { mutableStateOf(draft.contentHtml) }
  var isGeneratingOutline by remember { mutableStateOf(false) }
  var isGeneratingArticle by remember { mutableStateOf(false) }

  val tabs = listOf(
    I18n.t("writer_tab_editor", language),
    I18n.t("writer_tab_preview", language),
    I18n.t("writer_tab_faq", language),
    I18n.t("writer_tab_images", language)
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Generation Parameters Header
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = I18n.t("writer_title", language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${I18n.t("writer_target_kw", language)}: ${draft.targetKeyword}",
                style = MaterialTheme.typography.bodySmall,
                color = WpBlueLight,
                fontWeight = FontWeight.SemiBold
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = VioletAi.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, VioletAi.copy(alpha = 0.4f))
            ) {
              Text(
                text = "v${draft.version} • ${draft.wordCount} words",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = VioletAi,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // 2 Step Action Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedButton(
              onClick = { isGeneratingOutline = true },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_gen_outline")
            ) {
              Icon(Icons.Default.FormatListBulleted, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = I18n.t("writer_generate_outline", language), fontSize = 11.sp)
            }

            Button(
              onClick = { isGeneratingArticle = true },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = VioletAi
              ),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_gen_article")
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = I18n.t("writer_generate_full", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. SEO Title & Meta Fields
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
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = I18n.t("writer_meta_title", language),
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${titleText.length}/60",
              fontSize = 11.sp,
              color = if (titleText.length in 50..65) EmeraldSuccess else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_article_title"),
            shape = RoundedCornerShape(8.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = I18n.t("writer_meta_desc", language),
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${metaDescText.length}/160",
              fontSize = 11.sp,
              color = if (metaDescText.length in 120..160) EmeraldSuccess else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = metaDescText,
            onValueChange = { metaDescText = it },
            maxLines = 3,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_article_meta_desc"),
            shape = RoundedCornerShape(8.dp)
          )
        }
      }
    }

    // 3. Tab switcher
    item {
      ScrollableTabRow(
        selectedTabIndex = selectedTab,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .fillMaxWidth()
      ) {
        tabs.forEachIndexed { index, title ->
          Tab(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            text = { Text(title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
          )
        }
      }
    }

    // 4. Tab Contents
    item {
      when (selectedTab) {
        0 -> { // Markdown Editor
          OutlinedTextField(
            value = markdownContent,
            onValueChange = { markdownContent = it },
            modifier = Modifier
              .fillMaxWidth()
              .height(340.dp)
              .testTag("input_article_content_markdown"),
            shape = RoundedCornerShape(12.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
              fontFamily = FontFamily.Monospace,
              fontSize = 12.sp,
              lineHeight = 18.sp
            )
          )
        }
        1 -> { // HTML Preview (Gutenberg styled)
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .height(340.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = "<!-- WordPress Gutenberg Clean HTML -->",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = htmlContent,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
        2 -> { // FAQ Schema
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(text = "Schema FAQPage JSON-LD Validated", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              Spacer(modifier = Modifier.height(10.dp))
              draft.faqs.forEach { faq ->
                Text(text = "Q: ${faq.question}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = WpBlueLight)
                Text(text = "A: ${faq.answer}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
              }
            }
          }
        }
        3 -> { // Image Prompts
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Image, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Đề xuất ảnh bìa AI (Featured Image Prompt)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
              Spacer(modifier = Modifier.height(8.dp))
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = draft.imagePrompt,
                  fontSize = 11.sp,
                  fontFamily = FontFamily.Monospace,
                  modifier = Modifier.padding(10.dp)
                )
              }
            }
          }
        }
      }
    }

    // 5. Action Buttons (Save Draft, Quality Gate, Queue)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = { onSaveDraft(titleText, metaDescText, markdownContent, htmlContent) },
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = I18n.t("btn_save", language), fontSize = 11.sp)
        }

        Button(
          onClick = onNavigateToQualityGate,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = EmeraldSuccess
          ),
          modifier = Modifier
            .weight(1f)
            .testTag("btn_audit_gate")
        ) {
          Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = I18n.t("writer_btn_gate", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Button(
        onClick = onQueueForPublishing,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("btn_queue_publishing")
      ) {
        Icon(Icons.Default.Publish, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = I18n.t("writer_btn_publish", language), fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}
