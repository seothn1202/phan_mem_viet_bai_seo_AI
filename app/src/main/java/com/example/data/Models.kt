package com.example.data

enum class SiteAuthType {
  APPLICATION_PASSWORD, JWT_BEARER, BASIC_AUTH
}

enum class ConnectionStatus {
  ONLINE, LATENCY_WARNING, AUTH_ERROR, OFFLINE
}

data class WordPressSite(
  val id: String,
  val name: String,
  val url: String,
  val username: String,
  val appPasswordMasked: String = "••••••••••••••••",
  val authType: SiteAuthType = SiteAuthType.APPLICATION_PASSWORD,
  val status: ConnectionStatus = ConnectionStatus.ONLINE,
  val latencyMs: Int = 120,
  val categories: List<String> = listOf("Technology", "Tips", "Reviews"),
  val dailyLimit: Int = 3,
  val postsPublishedToday: Int = 1,
  val isAutoPublishEnabled: Boolean = true,
  val isDemo: Boolean = true
)

enum class SearchIntent {
  INFORMATIONAL, TRANSACTIONAL, COMMERCIAL, NAVIGATIONAL
}

enum class KeywordPriority {
  HIGH, MEDIUM, LOW
}

data class KeywordItem(
  val id: String,
  val keyword: String,
  val seedTopic: String,
  val market: String = "VN (google.com.vn)",
  val language: String = "vi",
  val intent: SearchIntent,
  val estimatedVolume: Int,
  val kdPercent: Int, // 0 - 100
  val cluster: String,
  val priority: KeywordPriority,
  val source: String = "Google Search Console",
  val isDemo: Boolean = true
)

data class HeadingItem(
  val level: String, // "H1", "H2", "H3"
  val text: String
)

data class CompetitorScan(
  val id: String,
  val url: String,
  val title: String,
  val titleLength: Int,
  val metaDescription: String,
  val metaScore: Int,
  val wordCount: Int,
  val headingsCount: Int,
  val imagesCount: Int,
  val headings: List<HeadingItem>,
  val topicGaps: List<String>,
  val analyzedAt: String,
  val isDemo: Boolean = true
)

data class FaqItem(
  val question: String,
  val answer: String
)

enum class ArticleStatus {
  DRAFT, OUTLINE_GENERATED, GATE_CHECKED, QUEUED, PUBLISHED
}

data class ArticleDraft(
  val id: String,
  val targetKeyword: String,
  val secondaryKeywords: List<String>,
  val cluster: String,
  val targetSiteId: String,
  val title: String,
  val metaDescription: String,
  val contentMarkdown: String,
  val contentHtml: String,
  val outline: List<String>,
  val faqs: List<FaqItem>,
  val imagePrompt: String,
  val status: ArticleStatus = ArticleStatus.DRAFT,
  val seoScore: Int = 88,
  val wordCount: Int = 1450,
  val version: Int = 1,
  val updatedAt: String = "Hôm nay 09:30"
)

enum class CheckStatus {
  PASS, WARNING, FAIL
}

data class QualityCheckItem(
  val id: String,
  val nameVi: String,
  val nameEn: String,
  val status: CheckStatus,
  val detailVi: String,
  val detailEn: String,
  val metric: String
)

enum class PublishStatus {
  SCHEDULED, PUBLISHING, PUBLISHED, FAILED
}

data class PublishQueueItem(
  val id: String,
  val articleId: String,
  val articleTitle: String,
  val siteId: String,
  val siteName: String,
  val scheduledTime: String,
  val status: PublishStatus,
  val liveUrl: String? = null,
  val httpStatus: Int? = 201,
  val errorMessage: String? = null,
  val retryCount: Int = 0
)

data class AutomationConfig(
  val isEmergencyStop: Boolean = false,
  val isAutoPilotRunning: Boolean = true,
  val maxPostsPerSitePerDay: Int = 3,
  val activeTimeWindow: String = "07:00 - 22:00",
  val minQualityScore: Int = 85,
  val monthlyBudgetCapUsd: Double = 50.0,
  val notifyOnFailure: Boolean = true
)

data class CostSummary(
  val currentSpendUsd: Double = 14.85,
  val budgetCapUsd: Double = 50.0,
  val promptTokens: Long = 1_420_000,
  val completionTokens: Long = 890_000,
  val totalArticlesGenerated: Int = 342,
  val avgCostPerArticle: Double = 0.043,
  val topModel: String = "Gemini 1.5 Pro"
)

enum class ActivityType {
  PUBLISHED, AI_GENERATED, GATE_AUDIT, ERROR, WARNING, SYSTEM
}

data class ActivityLog(
  val id: String,
  val timestamp: String,
  val titleVi: String,
  val titleEn: String,
  val siteName: String,
  val type: ActivityType
)
