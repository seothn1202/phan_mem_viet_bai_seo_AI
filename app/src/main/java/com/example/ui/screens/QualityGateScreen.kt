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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
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
import com.example.data.AppLanguage
import com.example.data.CheckStatus
import com.example.data.I18n
import com.example.data.QualityCheckItem
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi

@Composable
fun QualityGateScreen(
  language: AppLanguage,
  score: Int,
  checks: List<QualityCheckItem>,
  onAutoFixClick: () -> Unit,
  onProceedToPublish: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isPassed = score >= 85
  val statusColor = if (isPassed) EmeraldSuccess else RoseError

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Score Badge Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, statusColor.copy(alpha = 0.5f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape)
              .background(statusColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "$score",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = statusColor
              )
              Text(
                text = "/100",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = if (isPassed) I18n.t("gate_pass", language) else I18n.t("gate_fail", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = statusColor
          )

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (isPassed)
              (if (language == AppLanguage.VI) "Bài viết đáp ứng đầy đủ tiêu chuẩn E-E-A-T và thuật toán xếp hạng" else "Article meets Google E-E-A-T requirements")
            else
              (if (language == AppLanguage.VI) "Cần tối ưu thêm các thẻ Heading và mật độ từ khóa" else "Action required on headings and density"),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = onAutoFixClick,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = VioletAi
              ),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_autofix_gate")
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = I18n.t("gate_auto_fix", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = onProceedToPublish,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = EmeraldSuccess
              ),
              modifier = Modifier
                .weight(1f)
                .testTag("btn_gate_publish")
            ) {
              Icon(Icons.Default.Publish, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = if (language == AppLanguage.VI) "Lên lịch đăng" else "Schedule", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 2. Checklist Header
    item {
      Text(
        text = if (language == AppLanguage.VI) "Tiêu chí kiểm định chi tiết (${checks.size})" else "Detailed Compliance Criteria (${checks.size})",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
    }

    // 3. Checks List
    items(checks, key = { it.id }) { check ->
      QualityCheckCard(check = check, language = language)
    }
  }
}

@Composable
private fun QualityCheckCard(
  check: QualityCheckItem,
  language: AppLanguage
) {
  val (icon, color) = when (check.status) {
    CheckStatus.PASS -> Pair(Icons.Default.Check, EmeraldSuccess)
    CheckStatus.WARNING -> Pair(Icons.Default.Warning, AmberWarning)
    CheckStatus.FAIL -> Pair(Icons.Default.Close, RoseError)
  }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(14.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (language == AppLanguage.VI) check.nameVi else check.nameEn,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Surface(
            shape = RoundedCornerShape(4.dp),
            color = color.copy(alpha = 0.15f)
          ) {
            Text(
              text = check.metric,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = color,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = if (language == AppLanguage.VI) check.detailVi else check.detailEn,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
