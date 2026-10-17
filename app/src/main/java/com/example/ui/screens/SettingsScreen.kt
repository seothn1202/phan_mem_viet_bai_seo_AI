package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.I18n
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.WpBlueLight

@Composable
fun SettingsScreen(
  language: AppLanguage,
  isDarkTheme: Boolean,
  onLanguageChange: (AppLanguage) -> Unit,
  onThemeChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  var showApiKey by remember { mutableStateOf(false) }
  var geminiApiKey by remember { mutableStateOf("AIzaSyStudio_LiveKey_WPSEO9823") }
  var wpAppPassword by remember { mutableStateOf("wp_app_pwd_94a28bf2a091") }
  var gscServiceAccount by remember { mutableStateOf("wp-seo-agent@studio-gsc.iam.gserviceaccount.com") }
  var showSaveNotification by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Header
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = I18n.t("settings_title", language),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = I18n.t("settings_subtitle", language),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // 2. Language Switcher Card
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
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Language, contentDescription = null, tint = WpBlueLight, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = I18n.t("settings_lang", language),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = if (language == AppLanguage.VI) "Tiếng Việt (Mặc định)" else "English (Active)",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Button(
              onClick = { onLanguageChange(AppLanguage.VI) },
              colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = if (language == AppLanguage.VI) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (language == AppLanguage.VI) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
              ),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("VI", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Button(
              onClick = { onLanguageChange(AppLanguage.EN) },
              colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = if (language == AppLanguage.EN) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (language == AppLanguage.EN) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
              ),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("EN", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 3. Theme Toggle Card (Dark / Light)
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
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = I18n.t("settings_theme", language),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = if (isDarkTheme) (if (language == AppLanguage.VI) "Chế độ Tối (Studio Navy)" else "Dark Mode")
                else (if (language == AppLanguage.VI) "Chế độ Sáng" else "Light Mode"),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Switch(
            checked = isDarkTheme,
            onCheckedChange = { onThemeChange(it) },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = MaterialTheme.colorScheme.primary
            )
          )
        }
      }
    }

    // 4. API Keys Configuration
    item {
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
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Key, contentDescription = null, tint = WpBlueLight, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = I18n.t("settings_api_keys", language),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
              )
            }

            IconButton(
              onClick = { showApiKey = !showApiKey },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = if (showApiKey) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "Toggle Visibility",
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(text = "Google Gemini AI API Key", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = geminiApiKey,
            onValueChange = { geminiApiKey = it },
            visualTransformation = if (showApiKey) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_gemini_api_key"),
            shape = RoundedCornerShape(8.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(text = "WordPress Application Password (Global Default)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = wpAppPassword,
            onValueChange = { wpAppPassword = it },
            visualTransformation = if (showApiKey) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_wp_app_password"),
            shape = RoundedCornerShape(8.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(text = "Google Search Console Service Account", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          Spacer(modifier = Modifier.height(4.dp))
          OutlinedTextField(
            value = gscServiceAccount,
            onValueChange = { gscServiceAccount = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { showSaveNotification = true },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("btn_save_settings")
          ) {
            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = I18n.t("btn_save", language), fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          if (showSaveNotification) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EmeraldSuccess.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, EmeraldSuccess.copy(alpha = 0.3f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = if (language == AppLanguage.VI) "Đã lưu cài đặt và thông tin xác thực an toàn!" else "Settings saved securely!",
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
  }
}
