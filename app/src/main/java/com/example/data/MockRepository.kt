package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MockRepository {

  // 1. WordPress Sites (12+ websites)
  private val _sites = MutableStateFlow(
    listOf(
      WordPressSite(
        id = "site_1",
        name = "TechTrend Vietnam",
        url = "https://techtrend.vn",
        username = "admin_tech",
        status = ConnectionStatus.ONLINE,
        latencyMs = 86,
        categories = listOf("AI & Công nghệ", "Review Phần Mềm", "Tin Tức"),
        dailyLimit = 4,
        postsPublishedToday = 2,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_2",
        name = "DealHunter Global",
        url = "https://dealhunter.io",
        username = "affiliate_bot",
        status = ConnectionStatus.ONLINE,
        latencyMs = 142,
        categories = listOf("Coupons", "Black Friday", "SaaS Discounts"),
        dailyLimit = 5,
        postsPublishedToday = 3,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_3",
        name = "HealthGuide 365",
        url = "https://healthguide365.com",
        username = "health_editor",
        status = ConnectionStatus.ONLINE,
        latencyMs = 95,
        categories = listOf("Dinh dưỡng", "Thể Thao", "Sống Khỏe"),
        dailyLimit = 3,
        postsPublishedToday = 1,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_4",
        name = "CryptoInsight Daily",
        url = "https://cryptoinsight.net",
        username = "crypto_pulse",
        status = ConnectionStatus.LATENCY_WARNING,
        latencyMs = 480,
        categories = listOf("Bitcoin", "DeFi", "Web3 Analytics"),
        dailyLimit = 3,
        postsPublishedToday = 0,
        isAutoPublishEnabled = false
      ),
      WordPressSite(
        id = "site_5",
        name = "AutoReview Pro",
        url = "https://autoreviewpro.vn",
        username = "auto_analyst",
        status = ConnectionStatus.ONLINE,
        latencyMs = 110,
        categories = listOf("Xe Điện EV", "Đánh Giá Xe", "Phụ Kiện"),
        dailyLimit = 2,
        postsPublishedToday = 1,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_6",
        name = "HomeDeco Studio",
        url = "https://homedecostudio.org",
        username = "interior_design",
        status = ConnectionStatus.ONLINE,
        latencyMs = 135,
        categories = listOf("Nội Thất", "Kiến Trúc", "Phong Thủy"),
        dailyLimit = 3,
        postsPublishedToday = 2,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_7",
        name = "GourmetEats Kitchen",
        url = "https://gourmeteats.kitchen",
        username = "chef_bot",
        status = ConnectionStatus.ONLINE,
        latencyMs = 120,
        categories = listOf("Công Thức Nấu Ăn", "Ẩm Thực Á", "Healthy"),
        dailyLimit = 3,
        postsPublishedToday = 1,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_8",
        name = "SaaS Growth Playbook",
        url = "https://saasgrowth.co",
        username = "growth_hacker",
        status = ConnectionStatus.ONLINE,
        latencyMs = 92,
        categories = listOf("B2B Marketing", "Product Led", "SEO Tips"),
        dailyLimit = 4,
        postsPublishedToday = 2,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_9",
        name = "GreenLiving Hub",
        url = "https://greenlivinghub.org",
        username = "eco_editor",
        status = ConnectionStatus.ONLINE,
        latencyMs = 104,
        categories = listOf("Năng Lượng Xanh", "Zero Waste", "Tái Chế"),
        dailyLimit = 2,
        postsPublishedToday = 0,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_10",
        name = "EduLearn Academy",
        url = "https://edulearn.vn",
        username = "academy_admin",
        status = ConnectionStatus.ONLINE,
        latencyMs = 115,
        categories = listOf("Du Học", "Luyện Thi IELTS", "Khóa Học Online"),
        dailyLimit = 3,
        postsPublishedToday = 1,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_11",
        name = "FitnessTracker Pro",
        url = "https://fitnesstracker.app",
        username = "workout_bot",
        status = ConnectionStatus.ONLINE,
        latencyMs = 99,
        categories = listOf("Gym Workout", "Cardio", "Whey Protein"),
        dailyLimit = 3,
        postsPublishedToday = 2,
        isAutoPublishEnabled = true
      ),
      WordPressSite(
        id = "site_12",
        name = "TravelNomad Asia",
        url = "https://travelnomadasia.blog",
        username = "wanderlust_adm",
        status = ConnectionStatus.AUTH_ERROR,
        latencyMs = 0,
        categories = listOf("Du Lịch Bụi", "Visa", "Khách Sạn"),
        dailyLimit = 2,
        postsPublishedToday = 0,
        isAutoPublishEnabled = false
      )
    )
  )
  val sites: StateFlow<List<WordPressSite>> = _sites.asStateFlow()

  // 2. Keywords
  private val _keywords = MutableStateFlow(
    listOf(
      KeywordItem(
        id = "kw_1",
        keyword = "cách cài wordpress bằng docker",
        seedTopic = "WordPress Hosting",
        market = "VN (google.com.vn)",
        intent = SearchIntent.INFORMATIONAL,
        estimatedVolume = 4800,
        kdPercent = 28,
        cluster = "WordPress Dev",
        priority = KeywordPriority.HIGH,
        source = "Google Search Console"
      ),
      KeywordItem(
        id = "kw_2",
        keyword = "top plugin seo wordpress tốt nhất 2026",
        seedTopic = "WordPress SEO",
        market = "VN (google.com.vn)",
        intent = SearchIntent.COMMERCIAL,
        estimatedVolume = 9200,
        kdPercent = 45,
        cluster = "SEO Plugins",
        priority = KeywordPriority.HIGH,
        source = "Live SERP API"
      ),
      KeywordItem(
        id = "kw_3",
        keyword = "mua hosting gia hạn giá rẻ",
        seedTopic = "Hosting",
        market = "VN (google.com.vn)",
        intent = SearchIntent.TRANSACTIONAL,
        estimatedVolume = 6500,
        kdPercent = 68,
        cluster = "Hosting Deals",
        priority = KeywordPriority.MEDIUM,
        source = "Competitor Gap"
      ),
      KeywordItem(
        id = "kw_4",
        keyword = "hướng dẫn kết nối wordpress rest api application password",
        seedTopic = "WordPress API",
        market = "VN (google.com.vn)",
        intent = SearchIntent.INFORMATIONAL,
        estimatedVolume = 1900,
        kdPercent = 18,
        cluster = "WordPress Dev",
        priority = KeywordPriority.HIGH,
        source = "Google Search Console"
      ),
      KeywordItem(
        id = "kw_5",
        keyword = "tự động hóa viết bài seo bằng gemini ai",
        seedTopic = "AI Automation",
        market = "VN (google.com.vn)",
        intent = SearchIntent.INFORMATIONAL,
        estimatedVolume = 5400,
        kdPercent = 32,
        cluster = "AI SEO",
        priority = KeywordPriority.HIGH,
        source = "Live SERP API"
      ),
      KeywordItem(
        id = "kw_6",
        keyword = "so sánh rank math pro vs yoast seo",
        seedTopic = "WordPress SEO",
        market = "VN (google.com.vn)",
        intent = SearchIntent.COMMERCIAL,
        estimatedVolume = 3800,
        kdPercent = 41,
        cluster = "SEO Plugins",
        priority = KeywordPriority.MEDIUM,
        source = "Live SERP API"
      ),
      KeywordItem(
        id = "kw_7",
        keyword = "đăng bài wordpress hàng loạt không bị spam",
        seedTopic = "Content Automation",
        market = "VN (google.com.vn)",
        intent = SearchIntent.INFORMATIONAL,
        estimatedVolume = 2100,
        kdPercent = 22,
        cluster = "Publishing Policy",
        priority = KeywordPriority.MEDIUM,
        source = "Google Search Console"
      )
    )
  )
  val keywords: StateFlow<List<KeywordItem>> = _keywords.asStateFlow()

  // 3. Competitor Scan
  private val _competitorScan = MutableStateFlow(
    CompetitorScan(
      id = "comp_1",
      url = "https://rivaltech.vn/best-ai-seo-tools-2026",
      title = "Top 7 Công Cụ AI SEO Tự Động Hóa Tốt Nhất Năm 2026 | RivalTech",
      titleLength = 62,
      metaDescription = "Đánh giá chi tiết top 7 công cụ AI SEO giúp tăng trưởng traffic tự động, phân tích đối thủ và tối ưu bài viết chuẩn Google Search.",
      metaScore = 91,
      wordCount = 2340,
      headingsCount = 14,
      imagesCount = 8,
      headings = listOf(
        HeadingItem("H1", "Top 7 Công Cụ AI SEO Tự Động Hóa Tốt Nhất Năm 2026"),
        HeadingItem("H2", "1. AI SEO là gì và tại sao lại bùng nổ năm 2026?"),
        HeadingItem("H2", "2. Tiêu chí chọn công cụ SEO cho website WordPress"),
        HeadingItem("H3", "Khả năng kết nối WordPress REST API"),
        HeadingItem("H3", "Chi phí tính theo token và số lượng website"),
        HeadingItem("H2", "3. Đánh giá chi tiết từng phần mềm"),
        HeadingItem("H2", "4. Bảng so sánh tính năng và bảng giá"),
        HeadingItem("H2", "5. Kết luận và lời khuyên tối ưu chi phí")
      ),
      topicGaps = listOf(
        "Bảo mật thông tin đăng nhập với Application Passwords (Đối thủ chưa đề cập)",
        "Quy trình Quality Gate tự động loại bỏ nội dung rác trước khi đăng",
        "Chi phí thực tế của Gemini Flash so với GPT-4o khi quản lý 10+ website",
        "Cách đặt lịch đăng rải rác tự nhiên để tránh Google Sandbox"
      ),
      analyzedAt = "Hôm nay 08:45 (Public HTML Scan)"
    )
  )
  val competitorScan: StateFlow<CompetitorScan> = _competitorScan.asStateFlow()

  // 4. Current Article Draft
  private val _currentDraft = MutableStateFlow(
    ArticleDraft(
      id = "art_101",
      targetKeyword = "tự động hóa viết bài seo bằng gemini ai",
      secondaryKeywords = listOf("ai seo studio", "wordpress rest api", "quality gate seo", "chi phí gemini api"),
      cluster = "AI SEO",
      targetSiteId = "site_1",
      title = "Hướng Dẫn Tự Động Hóa Viết Bài SEO Bằng Gemini AI & WordPress REST API (2026)",
      metaDescription = "Khám phá cách xây dựng chuỗi tự động hóa viết bài chuẩn SEO bằng Gemini AI kết nối trực tiếp WordPress REST API. Tiết kiệm 80% thời gian và ngân sách.",
      contentMarkdown = """
# Hướng Dẫn Tự Động Hóa Viết Bài SEO Bằng Gemini AI & WordPress REST API (2026)

Tự động hóa nội dung đã bước sang kỷ nguyên mới. Với sức mạnh của **Gemini 1.5 Pro / Flash** kết hợp cùng **WordPress REST API**, các nhà quản trị website giờ đây có thể vận hành mạng lưới hơn 10 trang web mà vẫn đảm bảo tiêu chuẩn chất lượng khắt khe của Google E-E-A-T.

## 1. Tại Sao Nên Chọn Gemini AI Cho Hệ Thống SEO WordPress?

So với các mô hình thế hệ trước, Gemini cung cấp:
- **Cửa sổ ngữ cảnh khổng lồ**: Khả năng phân tích toàn bộ cấu trúc bài viết và đối thủ cạnh tranh trong một lần gọi API.
- **Tốc độ phản hồi cực nhanh**: Phù hợp cho các pipeline xử lý hàng loạt theo lịch.
- **Chi phí tối ưu**: Gemini Flash giúp giảm tới 85% chi phí so với các giải pháp khác khi mở rộng quy mô.

## 2. Thiết Lập Xác Thực An Toàn Qua WordPress Application Passwords

Tuyệt đối không sử dụng mật khẩu tài khoản chính của quản trị viên. Hãy tạo **Application Password** riêng biệt trong mục:
*Users -> Profile -> Application Passwords*.

```bash
curl -X POST https://yourdomain.com/wp-json/wp/v2/posts \
  --user "username:xxxx xxxx xxxx xxxx" \
  -H "Content-Type: application/json" \
  -d '{"title":"Tiêu đề chuẩn SEO","status":"publish"}'
```

## 3. Quy Trình 4 Bước: Từ Từ Khóa Đến Bài Viết Hoàn Chỉnh

1. **Nghiên cứu từ khóa**: Thu thập từ Google Search Console và SERP Suggest.
2. **Tạo dàn ý H2-H3 logic**: Đảm bảo bao phủ các lỗ hổng nội dung (Topic Gaps).
3. **Kiểm duyệt qua SEO Quality Gate**: Đạt tối thiểu 85/100 điểm trước khi xuất bản.
4. **Đăng theo lịch trình thông minh**: Tối đa 3-5 bài/ngày để giữ tín hiệu tự nhiên.

## 4. Câu Hỏi Thường Gặp (FAQ)

### Google có phạt bài viết do AI tạo ra không?
Google tuyên bố rõ ràng: Chỉ cần nội dung mang lại giá trị thực tế cho người đọc, đáp ứng tiêu chuẩn E-E-A-T, không phụ thuộc vào công cụ sản xuất.

### Làm sao ngăn ngừa lỗi trùng lặp khi chạy tự động?
Hệ thống sử dụng cơ chế hash nội dung và so sánh semantic vector trước khi đưa vào hàng đợi.
      """.trimIndent(),
      contentHtml = """
<article class="wp-seo-content">
  <h1>Hướng Dẫn Tự Động Hóa Viết Bài SEO Bằng Gemini AI &amp; WordPress REST API (2026)</h1>
  <p class="lead">Tự động hóa nội dung đã bước sang kỷ nguyên mới. Với sức mạnh của <strong>Gemini 1.5 Pro / Flash</strong> kết hợp cùng <strong>WordPress REST API</strong>...</p>
  <h2>1. Tại Sao Nên Chọn Gemini AI Cho Hệ Thống SEO WordPress?</h2>
  <ul>
    <li><strong>Cửa sổ ngữ cảnh khổng lồ:</strong> Xử lý toàn diện entity ngữ nghĩa.</li>
    <li><strong>Tốc độ phản hồi:</strong> Pipeline xuất bản mượt mà theo lịch.</li>
    <li><strong>Chi phí tối ưu:</strong> Tiết kiệm tới 85% chi phí API.</li>
  </ul>
  <h2>2. Thiết Lập Xác Thực An Toàn</h2>
  <p>Sử dụng cơ chế Application Passwords chuẩn WordPress Core để bảo mật tuyệt đối.</p>
</article>
      """.trimIndent(),
      outline = listOf(
        "H1: Hướng Dẫn Tự Động Hóa Viết Bài SEO Bằng Gemini AI & WordPress REST API",
        "H2: 1. Tại Sao Nên Chọn Gemini AI Cho Hệ Thống SEO WordPress?",
        "H2: 2. Thiết Lập Xác Thực An Toàn Qua WordPress Application Passwords",
        "H2: 3. Quy Trình 4 Bước: Từ Từ Khóa Đến Bài Viết Hoàn Chỉnh",
        "H3: Bước 1: Nghiên cứu từ khóa và Intent",
        "H3: Bước 2: Tạo dàn ý bao phủ Topic Gap",
        "H3: Bước 3: Kiểm duyệt Quality Gate >= 85 điểm",
        "H3: Bước 4: Đặt lịch đăng tự động",
        "H2: 4. Câu Hỏi Thường Gặp (FAQ Schema)"
      ),
      faqs = listOf(
        FaqItem("Google có phạt bài viết do AI tạo ra không?", "Không. Google đánh giá theo chất lượng E-E-A-T và tính hữu ích, không phân biệt ai viết."),
        FaqItem("Hạn mức bài viết an toàn mỗi ngày cho 1 website là bao nhiêu?", "Khuyến nghị từ 2 đến 4 bài mỗi ngày rải đều vào các khung giờ vàng.")
      ),
      imagePrompt = "A high-tech digital studio desk with holographic WordPress logo, glowing AI nodes, and clean analytics growth chart, ultra-modern tech aesthetic, 4k",
      status = ArticleStatus.GATE_CHECKED,
      seoScore = 92,
      wordCount = 1450,
      version = 2
    )
  )
  val currentDraft: StateFlow<ArticleDraft> = _currentDraft.asStateFlow()

  // 5. SEO Quality Gate Checks
  private val _qualityChecks = MutableStateFlow(
    listOf(
      QualityCheckItem(
        id = "chk_1",
        nameVi = "Độ dài thẻ Tiêu đề SEO (Title Tag)",
        nameEn = "SEO Title Tag Length",
        status = CheckStatus.PASS,
        detailVi = "63 ký tự - Nằm trong vùng an toàn (50 - 65 ký tự)",
        detailEn = "63 characters - Inside ideal 50-65 range",
        metric = "63 chars"
      ),
      QualityCheckItem(
        id = "chk_2",
        nameVi = "Chứa từ khóa chính trong Title & H1",
        nameEn = "Primary Keyword in Title & H1",
        status = CheckStatus.PASS,
        detailVi = "Từ khóa 'tự động hóa viết bài seo bằng gemini ai' xuất hiện ở vị trí đầu",
        detailEn = "Primary keyword found at start of title",
        metric = "Vị trí 0-40"
      ),
      QualityCheckItem(
        id = "chk_3",
        nameVi = "Độ dài Thẻ mô tả (Meta Description)",
        nameEn = "Meta Description Length",
        status = CheckStatus.PASS,
        detailVi = "148 ký tự - Tối ưu cho hiển thị trên điện thoại & desktop",
        detailEn = "148 characters - Perfectly truncated",
        metric = "148 chars"
      ),
      QualityCheckItem(
        id = "chk_4",
        nameVi = "Cấu trúc phân cấp Headings (H1 -> H2 -> H3)",
        nameEn = "Heading Hierarchy Integrity",
        status = CheckStatus.PASS,
        detailVi = "Chính xác 1 H1, 4 H2 và 4 H3 logic, không nhảy cóc cấp độ",
        detailEn = "1 H1, 4 H2, 4 H3 clean nesting",
        metric = "1 H1, 4 H2"
      ),
      QualityCheckItem(
        id = "chk_5",
        nameVi = "Mật độ từ khóa chính (Keyword Density)",
        nameEn = "Keyword Density & Semantic Entity",
        status = CheckStatus.PASS,
        detailVi = "1.3% (6 lần lặp / 1,450 từ) - Tự nhiên, không nhồi nhét",
        detailEn = "1.3% (6 occurrences) - Natural density",
        metric = "1.3%"
      ),
      QualityCheckItem(
        id = "chk_6",
        nameVi = "Bao phủ Lỗ hổng đối thủ (Topic Gaps Covered)",
        nameEn = "Competitor Topic Gaps Covered",
        status = CheckStatus.PASS,
        detailVi = "Đã bổ sung mục 'Bảo mật Application Passwords' và 'Kiểm soát chi phí'",
        detailEn = "3 out of 3 target gaps addressed",
        metric = "3/3 Gaps"
      ),
      QualityCheckItem(
        id = "chk_7",
        nameVi = "Liên kết nội bộ & Liên kết nguồn uy tín",
        nameEn = "Internal & External Links",
        status = CheckStatus.WARNING,
        detailVi = "Có 2 liên kết nội bộ, nên bổ sung thêm 1 liên kết tới tài liệu WordPress",
        detailEn = "Found 2 internal links; suggest adding 1 outbound authority link",
        metric = "2 Links"
      ),
      QualityCheckItem(
        id = "chk_8",
        nameVi = "Tính độc bản & Dấu chân trùng lặp (Duplicate Check)",
        nameEn = "Originality & Plagiarism Safety",
        status = CheckStatus.PASS,
        detailVi = "Độ tương đồng < 4% so với các bài viết đã xuất bản trong hệ thống",
        detailEn = "96% unique against published archive",
        metric = "96% Unique"
      )
    )
  )
  val qualityChecks: StateFlow<List<QualityCheckItem>> = _qualityChecks.asStateFlow()

  // 6. Publishing Center Queue
  private val _publishQueue = MutableStateFlow(
    listOf(
      PublishQueueItem(
        id = "pub_1",
        articleId = "art_101",
        articleTitle = "Hướng Dẫn Tự Động Hóa Viết Bài SEO Bằng Gemini AI & WordPress REST API (2026)",
        siteId = "site_1",
        siteName = "TechTrend Vietnam",
        scheduledTime = "Hôm nay 14:00",
        status = PublishStatus.SCHEDULED,
        liveUrl = null
      ),
      PublishQueueItem(
        id = "pub_2",
        articleId = "art_102",
        articleTitle = "Top 10 Mã Giảm Giá Hosting WordPress Giá Rẻ Tốt Nhất Tháng 10",
        siteId = "site_2",
        siteName = "DealHunter Global",
        scheduledTime = "Hôm nay 16:30",
        status = PublishStatus.SCHEDULED,
        liveUrl = null
      ),
      PublishQueueItem(
        id = "pub_3",
        articleId = "art_103",
        articleTitle = "Chế Độ Ăn Địa Trung Hải Giúp Giảm Cân Và Tăng Năng Lượng",
        siteId = "site_3",
        siteName = "HealthGuide 365",
        scheduledTime = "Hôm qua 19:15",
        status = PublishStatus.PUBLISHED,
        liveUrl = "https://healthguide365.com/che-do-an-dia-trung-hai-giam-can-2026",
        httpStatus = 201
      ),
      PublishQueueItem(
        id = "pub_4",
        articleId = "art_104",
        articleTitle = "Đánh Giá Xe Điện VinFast VF3: Có Đáng Mua Đi Lại Hàng Ngày?",
        siteId = "site_5",
        siteName = "AutoReview Pro",
        scheduledTime = "Hôm qua 11:00",
        status = PublishStatus.PUBLISHED,
        liveUrl = "https://autoreviewpro.vn/danh-gia-vinfast-vf3-thuc-te",
        httpStatus = 201
      ),
      PublishQueueItem(
        id = "pub_5",
        articleId = "art_105",
        articleTitle = "Cập Nhật Thị Trường Bitcoin & Phân Tích On-Chain Tuần 42",
        siteId = "site_4",
        siteName = "CryptoInsight Daily",
        scheduledTime = "Hôm nay 08:00",
        status = PublishStatus.FAILED,
        liveUrl = null,
        httpStatus = 504,
        errorMessage = "504 Gateway Timeout: Server phản hồi chậm quá 30s",
        retryCount = 1
      )
    )
  )
  val publishQueue: StateFlow<List<PublishQueueItem>> = _publishQueue.asStateFlow()

  // 7. Automation Rules
  private val _automationConfig = MutableStateFlow(AutomationConfig())
  val automationConfig: StateFlow<AutomationConfig> = _automationConfig.asStateFlow()

  // 8. Cost Summary
  private val _costSummary = MutableStateFlow(CostSummary())
  val costSummary: StateFlow<CostSummary> = _costSummary.asStateFlow()

  // 9. Activity Logs
  private val _activities = MutableStateFlow(
    listOf(
      ActivityLog(
        id = "act_1",
        timestamp = "09:30",
        titleVi = "Đã tạo bài viết AI mới chuẩn SEO",
        titleEn = "Generated new AI SEO article",
        siteName = "TechTrend Vietnam",
        type = ActivityType.AI_GENERATED
      ),
      ActivityLog(
        id = "act_2",
        timestamp = "09:15",
        titleVi = "Quality Gate thông qua: 92/100 điểm",
        titleEn = "Quality Gate passed: 92/100 score",
        siteName = "TechTrend Vietnam",
        type = ActivityType.GATE_AUDIT
      ),
      ActivityLog(
        id = "act_3",
        timestamp = "08:00",
        titleVi = "Lỗi xuất bản: 504 Gateway Timeout",
        titleEn = "Publish failed: 504 Gateway Timeout",
        siteName = "CryptoInsight Daily",
        type = ActivityType.ERROR
      ),
      ActivityLog(
        id = "act_4",
        timestamp = "Hôm qua 19:15",
        titleVi = "Xuất bản thành công bài viết lên WordPress (HTTP 201)",
        titleEn = "Successfully published article to WordPress (HTTP 201)",
        siteName = "HealthGuide 365",
        type = ActivityType.PUBLISHED
      ),
      ActivityLog(
        id = "act_5",
        timestamp = "Hôm qua 16:00",
        titleVi = "Đồng bộ từ khóa mới từ Google Search Console",
        titleEn = "Synced new keywords from Google Search Console",
        siteName = "DealHunter Global",
        type = ActivityType.SYSTEM
      )
    )
  )
  val activities: StateFlow<List<ActivityLog>> = _activities.asStateFlow()

  // --- ACTIONS ---

  fun toggleEmergencyStop() {
    _automationConfig.update { it.copy(isEmergencyStop = !it.isEmergencyStop) }
  }

  fun updateAutomationRules(
    maxPosts: Int,
    minScore: Int,
    activeWindow: String,
    budgetCap: Double
  ) {
    _automationConfig.update {
      it.copy(
        maxPostsPerSitePerDay = maxPosts,
        minQualityScore = minScore,
        activeTimeWindow = activeWindow,
        monthlyBudgetCapUsd = budgetCap
      )
    }
  }

  fun addWebsite(newSite: WordPressSite) {
    _sites.update { listOf(newSite) + it }
  }

  fun deleteWebsite(siteId: String) {
    _sites.update { it.filter { site -> site.id != siteId } }
  }

  fun toggleSiteAutoPublish(siteId: String) {
    _sites.update { list ->
      list.map {
        if (it.id == siteId) it.copy(isAutoPublishEnabled = !it.isAutoPublishEnabled)
        else it
      }
    }
  }

  fun testSiteConnection(siteId: String): Pair<Boolean, String> {
    val site = _sites.value.find { it.id == siteId } ?: return Pair(false, "Site not found")
    return if (site.status == ConnectionStatus.AUTH_ERROR) {
      Pair(false, "401 Unauthorized: Application Password không hợp lệ hoặc đã bị thu hồi.")
    } else {
      Pair(true, "200 OK: Kết nối REST API v2 thành công (Ping: ${site.latencyMs}ms).")
    }
  }

  fun addKeyword(kw: KeywordItem) {
    _keywords.update { listOf(kw) + it }
  }

  fun setDraftFromKeyword(keyword: KeywordItem) {
    _currentDraft.update {
      it.copy(
        targetKeyword = keyword.keyword,
        cluster = keyword.cluster,
        title = "Hướng Dẫn Tối Ưu: ${keyword.keyword.replaceFirstChar { char -> char.uppercase() }} (2026)",
        metaDescription = "Tìm hiểu chi tiết về ${keyword.keyword}. Cẩm nang toàn diện với phương pháp thực thi từng bước và đánh giá chuyên gia.",
        status = ArticleStatus.OUTLINE_GENERATED,
        seoScore = 86
      )
    }
  }

  fun updateDraftContent(
    title: String,
    metaDesc: String,
    markdown: String,
    html: String
  ) {
    _currentDraft.update {
      it.copy(
        title = title,
        metaDescription = metaDesc,
        contentMarkdown = markdown,
        contentHtml = html,
        version = it.version + 1,
        status = ArticleStatus.DRAFT
      )
    }
  }

  fun autoFixQualityGate() {
    _qualityChecks.update { checks ->
      checks.map {
        if (it.status == CheckStatus.WARNING || it.status == CheckStatus.FAIL) {
          it.copy(status = CheckStatus.PASS, detailVi = it.detailVi + " [Đã tự động tối ưu]")
        } else it
      }
    }
    _currentDraft.update { it.copy(seoScore = 96, status = ArticleStatus.GATE_CHECKED) }
  }

  fun queueCurrentDraftForPublishing(targetSiteId: String, scheduleTime: String) {
    val draft = _currentDraft.value
    val site = _sites.value.find { it.id == targetSiteId } ?: _sites.value.first()
    val newItem = PublishQueueItem(
      id = "pub_${System.currentTimeMillis()}",
      articleId = draft.id,
      articleTitle = draft.title,
      siteId = site.id,
      siteName = site.name,
      scheduledTime = scheduleTime,
      status = PublishStatus.SCHEDULED
    )
    _publishQueue.update { listOf(newItem) + it }
    _currentDraft.update { it.copy(status = ArticleStatus.QUEUED) }
  }

  fun publishItemNow(queueId: String) {
    _publishQueue.update { list ->
      list.map { item ->
        if (item.id == queueId) {
          item.copy(
            status = PublishStatus.PUBLISHED,
            liveUrl = "https://${item.siteName.lowercase().replace(" ", "")}.vn/bai-viet-${System.currentTimeMillis() % 1000}",
            httpStatus = 201,
            errorMessage = null
          )
        } else item
      }
    }
    _activities.update {
      listOf(
        ActivityLog(
          id = "act_${System.currentTimeMillis()}",
          timestamp = "Vừa xong",
          titleVi = "Xuất bản tức thì thành công lên WordPress",
          titleEn = "Instant published to WordPress",
          siteName = _publishQueue.value.find { it.id == queueId }?.siteName ?: "WordPress Site",
          type = ActivityType.PUBLISHED
        )
      ) + it
    }
  }

  fun retryItem(queueId: String) {
    _publishQueue.update { list ->
      list.map { item ->
        if (item.id == queueId) {
          item.copy(
            status = PublishStatus.SCHEDULED,
            errorMessage = null,
            retryCount = item.retryCount + 1
          )
        } else item
      }
    }
  }
}
