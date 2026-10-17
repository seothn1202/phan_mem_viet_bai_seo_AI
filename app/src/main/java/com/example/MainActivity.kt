package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.I18n
import com.example.data.MockRepository
import com.example.ui.components.AddWebsiteDialog
import com.example.ui.components.AppTopBar
import com.example.ui.components.EmergencyStopConfirmDialog
import com.example.ui.components.LiveUrlDialog
import com.example.ui.components.PublishScheduleDialog
import com.example.ui.components.TestConnectionDialog
import com.example.ui.screens.AiWriterScreen
import com.example.ui.screens.AutomationRulesScreen
import com.example.ui.screens.CompetitorAnalysisScreen
import com.example.ui.screens.CostControlScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.KeywordResearchScreen
import com.example.ui.screens.PublishingCenterScreen
import com.example.ui.screens.QualityGateScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.VersionBackupScreen
import com.example.ui.screens.WebsiteManagerScreen
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent
import kotlinx.coroutines.launch

enum class Screen {
  DASHBOARD,
  WEBSITES,
  KEYWORDS,
  COMPETITORS,
  AI_WRITER,
  QUALITY_GATE,
  PUBLISHING,
  AUTOMATION,
  COST,
  BACKUP,
  SETTINGS
}

class MainActivity : ComponentActivity() {
  private val repository = MockRepository()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      var isDarkTheme by remember { mutableStateOf(true) }
      var currentLanguage by remember { mutableStateOf(AppLanguage.VI) }
      var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }
      var showMoreSheet by remember { mutableStateOf(false) }

      // Dialog states
      var showAddSiteDialog by remember { mutableStateOf(false) }
      var showEmergencyStopDialog by remember { mutableStateOf(false) }
      var showScheduleDialog by remember { mutableStateOf(false) }
      var activeLiveUrl by remember { mutableStateOf<String?>(null) }
      var testResultData by remember { mutableStateOf<Triple<String, Boolean, String>?>(null) }

      // State from Repository
      val sites by repository.sites.collectAsState()
      val keywords by repository.keywords.collectAsState()
      val competitorScan by repository.competitorScan.collectAsState()
      val currentDraft by repository.currentDraft.collectAsState()
      val qualityChecks by repository.qualityChecks.collectAsState()
      val publishQueue by repository.publishQueue.collectAsState()
      val automationConfig by repository.automationConfig.collectAsState()
      val costSummary by repository.costSummary.collectAsState()
      val activities by repository.activities.collectAsState()

      // Back handler: Return to Dashboard if on another screen
      BackHandler(enabled = currentScreen != Screen.DASHBOARD) {
        currentScreen = Screen.DASHBOARD
      }

      MyApplicationTheme(darkTheme = isDarkTheme) {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            AppTopBar(
              currentLanguage = currentLanguage,
              isEmergencyStop = automationConfig.isEmergencyStop,
              onLanguageToggle = {
                currentLanguage = if (currentLanguage == AppLanguage.VI) AppLanguage.EN else AppLanguage.VI
              },
              onEmergencyStopClick = {
                showEmergencyStopDialog = true
              }
            )
          },
          bottomBar = {
            NavigationBar(
              containerColor = MaterialTheme.colorScheme.surface,
              contentColor = MaterialTheme.colorScheme.onSurface,
              tonalElevation = 8.dp,
              modifier = Modifier
                .navigationBarsPadding()
                .testTag("app_navigation_bar")
            ) {
              // 1. Dashboard
              NavigationBarItem(
                selected = currentScreen == Screen.DASHBOARD,
                onClick = { currentScreen = Screen.DASHBOARD },
                icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                label = { Text(I18n.t("nav_dashboard", currentLanguage), fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("nav_item_dashboard")
              )

              // 2. Websites
              NavigationBarItem(
                selected = currentScreen == Screen.WEBSITES,
                onClick = { currentScreen = Screen.WEBSITES },
                icon = { Icon(Icons.Default.Language, contentDescription = "Websites") },
                label = { Text(I18n.t("nav_websites", currentLanguage), fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("nav_item_websites")
              )

              // 3. Keywords
              NavigationBarItem(
                selected = currentScreen == Screen.KEYWORDS,
                onClick = { currentScreen = Screen.KEYWORDS },
                icon = { Icon(Icons.Default.Search, contentDescription = "Keywords") },
                label = { Text(I18n.t("nav_keywords", currentLanguage), fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("nav_item_keywords")
              )

              // 4. AI Writer
              NavigationBarItem(
                selected = currentScreen == Screen.AI_WRITER,
                onClick = { currentScreen = Screen.AI_WRITER },
                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Writer") },
                label = { Text(I18n.t("nav_ai_writer", currentLanguage), fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("nav_item_ai_writer")
              )

              // 5. Publishing
              NavigationBarItem(
                selected = currentScreen == Screen.PUBLISHING,
                onClick = { currentScreen = Screen.PUBLISHING },
                icon = { Icon(Icons.Default.Publish, contentDescription = "Publishing") },
                label = { Text(I18n.t("nav_publishing", currentLanguage), fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("nav_item_publishing")
              )

              // 6. More Hub (Menu)
              NavigationBarItem(
                selected = currentScreen in listOf(Screen.COMPETITORS, Screen.QUALITY_GATE, Screen.AUTOMATION, Screen.COST, Screen.BACKUP, Screen.SETTINGS),
                onClick = { showMoreSheet = true },
                icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                label = { Text(if (currentLanguage == AppLanguage.VI) "Thêm" else "More", fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                  indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                  selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.testTag("nav_item_more")
              )
            }
          }
        ) { paddingValues ->
          Crossfade(
            targetState = currentScreen,
            modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues),
            label = "screen_crossfade"
          ) { screen ->
            when (screen) {
              Screen.DASHBOARD -> DashboardScreen(
                language = currentLanguage,
                sites = sites,
                automationConfig = automationConfig,
                costSummary = costSummary,
                activities = activities,
                onEmergencyStopClick = { showEmergencyStopDialog = true },
                onNavigateToKeywords = { currentScreen = Screen.KEYWORDS },
                onNavigateToWriter = { currentScreen = Screen.AI_WRITER },
                onNavigateToPublishing = { currentScreen = Screen.PUBLISHING },
                onNavigateToWebsites = { currentScreen = Screen.WEBSITES }
              )
              Screen.WEBSITES -> WebsiteManagerScreen(
                language = currentLanguage,
                sites = sites,
                onAddSiteClick = { showAddSiteDialog = true },
                onToggleAutoPublish = { siteId -> repository.toggleSiteAutoPublish(siteId) },
                onTestConnection = { siteId ->
                  val site = sites.find { it.id == siteId }
                  val (ok, msg) = repository.testSiteConnection(siteId)
                  testResultData = Triple(site?.name ?: "Website", ok, msg)
                },
                onDeleteSite = { siteId -> repository.deleteWebsite(siteId) }
              )
              Screen.KEYWORDS -> KeywordResearchScreen(
                language = currentLanguage,
                keywords = keywords,
                onAddKeyword = { kw -> repository.addKeyword(kw) },
                onDraftWithAi = { kw ->
                  repository.setDraftFromKeyword(kw)
                  currentScreen = Screen.AI_WRITER
                }
              )
              Screen.COMPETITORS -> CompetitorAnalysisScreen(
                language = currentLanguage,
                scanData = competitorScan,
                onGenerateOutrankingArticle = {
                  currentScreen = Screen.AI_WRITER
                }
              )
              Screen.AI_WRITER -> AiWriterScreen(
                language = currentLanguage,
                draft = currentDraft,
                onSaveDraft = { title, meta, md, html ->
                  repository.updateDraftContent(title, meta, md, html)
                },
                onNavigateToQualityGate = {
                  currentScreen = Screen.QUALITY_GATE
                },
                onQueueForPublishing = {
                  showScheduleDialog = true
                }
              )
              Screen.QUALITY_GATE -> QualityGateScreen(
                language = currentLanguage,
                score = currentDraft.seoScore,
                checks = qualityChecks,
                onAutoFixClick = { repository.autoFixQualityGate() },
                onProceedToPublish = { showScheduleDialog = true }
              )
              Screen.PUBLISHING -> PublishingCenterScreen(
                language = currentLanguage,
                queueItems = publishQueue,
                onPublishNow = { qId -> repository.publishItemNow(qId) },
                onRetry = { qId -> repository.retryItem(qId) },
                onViewLiveUrl = { url -> activeLiveUrl = url }
              )
              Screen.AUTOMATION -> AutomationRulesScreen(
                language = currentLanguage,
                config = automationConfig,
                onUpdateConfig = { max, min, window, budget ->
                  repository.updateAutomationRules(max, min, window, budget)
                },
                onToggleEmergencyStop = { showEmergencyStopDialog = true }
              )
              Screen.COST -> CostControlScreen(
                language = currentLanguage,
                summary = costSummary
              )
              Screen.BACKUP -> VersionBackupScreen(
                language = currentLanguage,
                currentDraft = currentDraft,
                onRestoreSampleData = {
                  // Restore initial default sample
                  currentScreen = Screen.DASHBOARD
                }
              )
              Screen.SETTINGS -> SettingsScreen(
                language = currentLanguage,
                isDarkTheme = isDarkTheme,
                onLanguageChange = { currentLanguage = it },
                onThemeChange = { isDarkTheme = it }
              )
            }
          }
        }

        // More Tools Bottom Sheet
        if (showMoreSheet) {
          ModalBottomSheet(
            onDismissRequest = { showMoreSheet = false },
            sheetState = rememberModalBottomSheetState()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = if (currentLanguage == AppLanguage.VI) "Công cụ & Quản trị mở rộng" else "Extended Studio Tools",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { showMoreSheet = false }) {
                  Icon(Icons.Default.Close, contentDescription = "Close")
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              MoreToolRow(
                title = I18n.t("nav_competitors", currentLanguage),
                subtitle = if (currentLanguage == AppLanguage.VI) "Quét cấu trúc HTML và tìm Topic Gaps" else "Scan HTML and identify outranking gaps",
                icon = Icons.Default.TrendingUp,
                color = WpCyanAccent,
                onClick = {
                  currentScreen = Screen.COMPETITORS
                  showMoreSheet = false
                }
              )

              MoreToolRow(
                title = I18n.t("nav_quality_gate", currentLanguage),
                subtitle = if (currentLanguage == AppLanguage.VI) "Kiểm duyệt E-E-A-T và điểm SEO trước khi đăng" else "Pre-flight compliance score and checklist",
                icon = Icons.Default.Security,
                color = EmeraldSuccess,
                onClick = {
                  currentScreen = Screen.QUALITY_GATE
                  showMoreSheet = false
                }
              )

              MoreToolRow(
                title = I18n.t("nav_automation", currentLanguage),
                subtitle = if (currentLanguage == AppLanguage.VI) "Cấu hình chuỗi tự động và hạn mức bài/ngày" else "Pipeline rules, posting schedule, daily limits",
                icon = Icons.Default.SmartToy,
                color = VioletAi,
                onClick = {
                  currentScreen = Screen.AUTOMATION
                  showMoreSheet = false
                }
              )

              MoreToolRow(
                title = I18n.t("nav_cost", currentLanguage),
                subtitle = if (currentLanguage == AppLanguage.VI) "Giám sát ngân sách AI, tokens và quota" else "Monitor Gemini/OpenAI tokens and spend",
                icon = Icons.Default.MonetizationOn,
                color = AmberWarning,
                onClick = {
                  currentScreen = Screen.COST
                  showMoreSheet = false
                }
              )

              MoreToolRow(
                title = I18n.t("nav_backup", currentLanguage),
                subtitle = if (currentLanguage == AppLanguage.VI) "Lịch sử phiên bản bài viết, sao lưu JSON" else "Draft version control and JSON backup/restore",
                icon = Icons.Default.Backup,
                color = WpBlueLight,
                onClick = {
                  currentScreen = Screen.BACKUP
                  showMoreSheet = false
                }
              )

              MoreToolRow(
                title = I18n.t("nav_settings", currentLanguage),
                subtitle = if (currentLanguage == AppLanguage.VI) "Ngôn ngữ, bảo mật khóa API và giao diện" else "Bilingual toggle, API credentials, theme",
                icon = Icons.Default.Settings,
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                  currentScreen = Screen.SETTINGS
                  showMoreSheet = false
                }
              )

              Spacer(modifier = Modifier.height(24.dp))
            }
          }
        }

        // Add Website Modal
        if (showAddSiteDialog) {
          AddWebsiteDialog(
            language = currentLanguage,
            onDismiss = { showAddSiteDialog = false },
            onAdd = { newSite ->
              repository.addWebsite(newSite)
              showAddSiteDialog = false
            }
          )
        }

        // Emergency Stop Confirm Modal
        if (showEmergencyStopDialog) {
          EmergencyStopConfirmDialog(
            language = currentLanguage,
            isCurrentlyStopped = automationConfig.isEmergencyStop,
            onDismiss = { showEmergencyStopDialog = false },
            onConfirm = {
              repository.toggleEmergencyStop()
              showEmergencyStopDialog = false
            }
          )
        }

        // Schedule Publishing Modal
        if (showScheduleDialog) {
          PublishScheduleDialog(
            language = currentLanguage,
            sites = sites,
            articleTitle = currentDraft.title,
            onDismiss = { showScheduleDialog = false },
            onScheduleConfirm = { targetSiteId, time ->
              repository.queueCurrentDraftForPublishing(targetSiteId, time)
              showScheduleDialog = false
              currentScreen = Screen.PUBLISHING
            }
          )
        }

        // Live URL Modal
        if (activeLiveUrl != null) {
          LiveUrlDialog(
            liveUrl = activeLiveUrl!!,
            onDismiss = { activeLiveUrl = null }
          )
        }

        // Test REST API Result Modal
        if (testResultData != null) {
          val (name, ok, msg) = testResultData!!
          TestConnectionDialog(
            siteName = name,
            isSuccess = ok,
            detailMessage = msg,
            onDismiss = { testResultData = null }
          )
        }
      }
    }
  }
}

@Composable
private fun MoreToolRow(
  title: String,
  subtitle: String,
  icon: ImageVector,
  color: Color,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 5.dp)
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() },
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }
}
