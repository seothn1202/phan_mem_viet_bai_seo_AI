package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.data.I18n
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpNavyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  currentLanguage: AppLanguage,
  isEmergencyStop: Boolean,
  onLanguageToggle: () -> Unit,
  onEmergencyStopClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    TopAppBar(
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface
      ),
      title = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(vertical = 4.dp)
        ) {
          // Studio Logo Icon
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(WpBlueLight),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "W",
              fontWeight = FontWeight.Black,
              fontSize = 20.sp,
              color = Color(0xFF0B132B)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = I18n.t("app_title", currentLanguage),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.width(8.dp))
              // DEMO DATA Pill
              Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.testTag("badge_demo_mode")
              ) {
                Text(
                  text = I18n.t("dash_demo_badge", currentLanguage),
                  color = MaterialTheme.colorScheme.onSecondaryContainer,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            Text(
              text = if (isEmergencyStop) I18n.t("dash_emergency_stopped", currentLanguage)
              else I18n.t("dash_auto_running", currentLanguage),
              fontSize = 11.sp,
              color = if (isEmergencyStop) RoseError else EmeraldSuccess,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      },
      actions = {
        // Bilingual Switcher Pill: VI / EN
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = MaterialTheme.colorScheme.surfaceVariant,
          modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onLanguageToggle() }
            .testTag("lang_toggle_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Language,
              contentDescription = "Language",
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (currentLanguage == AppLanguage.VI) "VI" else "EN",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }

        // Emergency Stop Trigger Button
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = if (isEmergencyStop) RoseError else MaterialTheme.colorScheme.errorContainer,
          modifier = Modifier
            .padding(end = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onEmergencyStopClick() }
            .testTag("emergency_stop_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Block,
              contentDescription = "Emergency Stop",
              tint = if (isEmergencyStop) Color.White else MaterialTheme.colorScheme.onErrorContainer,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isEmergencyStop) "STOPPED" else "STOP",
              color = if (isEmergencyStop) Color.White else MaterialTheme.colorScheme.onErrorContainer,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            )
          }
        }
      }
    )

    // Emergency Warning Banner if activated
    AnimatedVisibility(visible = isEmergencyStop) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(RoseError)
          .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (currentLanguage == AppLanguage.VI)
            "CẢNH BÁO: Tự động hóa và Hàng đợi xuất bản đã dừng khẩn cấp!"
          else
            "ALERT: Automation & Publishing Queue are halted by Emergency Stop!",
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}
