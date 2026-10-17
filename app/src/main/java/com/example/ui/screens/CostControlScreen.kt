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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.CostSummary
import com.example.data.I18n
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight

@Composable
fun CostControlScreen(
  language: AppLanguage,
  summary: CostSummary,
  modifier: Modifier = Modifier
) {
  val spendFraction = (summary.currentSpendUsd / summary.budgetCapUsd).toFloat().coerceIn(0f, 1f)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Spend Gauge Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = I18n.t("cost_title", language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = I18n.t("cost_subtitle", language),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(VioletAi.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = VioletAi, modifier = Modifier.size(20.dp))
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
          ) {
            Text(
              text = "$${summary.currentSpendUsd}",
              fontSize = 30.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "/ $${summary.budgetCapUsd} ${if (language == AppLanguage.VI) "hạn mức tháng" else "monthly cap"}",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          LinearProgressIndicator(
            progress = { spendFraction },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = if (spendFraction > 0.8f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "${(spendFraction * 100).toInt()}% đã sử dụng • Còn lại: $${String.format("%.2f", summary.budgetCapUsd - summary.currentSpendUsd)}",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // 2. Token Breakdown Cards
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Card(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Prompt Tokens", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "1.42M", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = WpBlueLight)
            Text(text = "~ $4.26", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }

        Card(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Completion Tokens", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "890K", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = VioletAi)
            Text(text = "~ $10.59", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }
    }

    // 3. Efficiency Metric
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(EmeraldSuccess.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.TrendingUp, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(text = I18n.t("cost_articles", language), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
              Text(text = "${summary.totalArticlesGenerated} bài đã tạo trong tháng", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }

          Text(
            text = "$0.043 / bài",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldSuccess
          )
        }
      }
    }

    // 4. Model Distribution & Tip
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = null, tint = VioletAi, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (language == AppLanguage.VI) "Mẹo tối ưu chi phí AI" else "Cost Optimization Tip",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = if (language == AppLanguage.VI)
              "Sử dụng Gemini Flash cho bước Quét dàn ý & Tạo thẻ Meta, chỉ dùng Gemini 1.5 Pro cho khâu Viết chuyên sâu để giảm tới 65% tổng hóa đơn token hàng tháng."
            else
              "Route outline generation and meta tag creation to Gemini Flash, reserving Gemini 1.5 Pro strictly for in-depth body content to cut token cost by 65%.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  }
}
