package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.data.ConnectionStatus
import com.example.data.I18n
import com.example.data.WordPressSite
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.WpBlueLight

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WebsiteManagerScreen(
  language: AppLanguage,
  sites: List<WordPressSite>,
  onAddSiteClick: () -> Unit,
  onToggleAutoPublish: (String) -> Unit,
  onTestConnection: (String) -> Unit,
  onDeleteSite: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Header Info
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
          )
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = I18n.t("sites_title", language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${sites.size} ${if (language == AppLanguage.VI) "website trong mạng lưới" else "sites in network"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Button(
              onClick = onAddSiteClick,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("btn_add_site_top")
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = I18n.t("sites_add_btn", language), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      // Sites list
      items(sites, key = { it.id }) { site ->
        SiteCard(
          site = site,
          language = language,
          onToggleAutoPublish = { onToggleAutoPublish(site.id) },
          onTestConnection = { onTestConnection(site.id) },
          onDeleteSite = { onDeleteSite(site.id) }
        )
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SiteCard(
  site: WordPressSite,
  language: AppLanguage,
  onToggleAutoPublish: () -> Unit,
  onTestConnection: () -> Unit,
  onDeleteSite: () -> Unit
) {
  val (statusText, statusColor) = when (site.status) {
    ConnectionStatus.ONLINE -> Pair(
      if (language == AppLanguage.VI) "ONLINE" else "ONLINE",
      EmeraldSuccess
    )
    ConnectionStatus.LATENCY_WARNING -> Pair(
      if (language == AppLanguage.VI) "CHẬM" else "SLOW",
      AmberWarning
    )
    ConnectionStatus.AUTH_ERROR -> Pair(
      if (language == AppLanguage.VI) "LỖI AUTH" else "AUTH ERR",
      RoseError
    )
    ConnectionStatus.OFFLINE -> Pair(
      if (language == AppLanguage.VI) "OFFLINE" else "OFFLINE",
      RoseError
    )
  }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Top row: Title, Status badge & Switch
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = site.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = site.url,
            style = MaterialTheme.typography.bodySmall,
            color = WpBlueLight
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          StatusBadge(text = statusText, color = statusColor)
          Spacer(modifier = Modifier.width(10.dp))
          Switch(
            checked = site.isAutoPublishEnabled,
            onCheckedChange = { onToggleAutoPublish() },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = EmeraldSuccess
            ),
            modifier = Modifier.testTag("switch_autopublish_${site.id}")
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Stats row: Latency, User, Daily Limit
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Speed, contentDescription = null, tint = statusColor, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (site.latencyMs > 0) "${site.latencyMs}ms" else "N/A",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.VpnKey, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = site.username,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Text(
          text = "${I18n.t("sites_quota", language)}: ${site.postsPublishedToday}/${site.dailyLimit}",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Categories pills
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        site.categories.forEach { category ->
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
          ) {
            Text(
              text = category,
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSecondaryContainer,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Action buttons: Test Ping & Delete
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedButton(
          onClick = onTestConnection,
          shape = RoundedCornerShape(8.dp),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
          modifier = Modifier.testTag("btn_test_site_${site.id}")
        ) {
          Icon(Icons.Default.NetworkCheck, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = if (language == AppLanguage.VI) "Kiểm tra REST API" else "Test REST API", fontSize = 11.sp)
        }

        IconButton(
          onClick = onDeleteSite,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
