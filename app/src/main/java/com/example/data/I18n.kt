package com.example.data

enum class AppLanguage {
  VI, EN
}

object I18n {
  private val viStrings = mapOf(
    // App & Navigation
    "app_title" to "WP AI SEO Studio",
    "nav_dashboard" to "Tổng quan",
    "nav_websites" to "Websites",
    "nav_keywords" to "Từ khóa",
    "nav_competitors" to "Đối thủ",
    "nav_ai_writer" to "AI Writer",
    "nav_quality_gate" to "Quality Gate",
    "nav_publishing" to "Xuất bản",
    "nav_automation" to "Tự động hóa",
    "nav_cost" to "Chi phí AI",
    "nav_backup" to "Sao lưu & Lịch sử",
    "nav_settings" to "Cài đặt",

    // Dashboard
    "dash_overview_title" to "Bảng điều khiển SEO & Tự động hóa",
    "dash_overview_subtitle" to "Quản lý hệ thống 12+ website WordPress",
    "dash_demo_badge" to "DỮ LIỆU DEMO",
    "dash_emergency_stop" to "DỪNG KHẨN CẤP",
    "dash_emergency_stopped" to "HỆ THỐNG ĐÃ DỪNG",
    "dash_emergency_confirm" to "Xác nhận dừng toàn bộ tiến trình tự động xuất bản?",
    "dash_stat_sites" to "Websites kết nối",
    "dash_stat_keywords" to "Từ khóa theo dõi",
    "dash_stat_articles" to "Bài viết đã tạo",
    "dash_stat_published" to "Đã xuất bản WP",
    "dash_stat_queue" to "Hàng đợi xuất bản",
    "dash_stat_failed" to "Lỗi cần xử lý",
    "dash_stat_cost" to "Chi phí AI tháng",
    "dash_stat_limit" to "Hạn mức $50.00",
    "dash_quick_action_keyword" to "Nghiên cứu từ khóa",
    "dash_quick_action_writer" to "Tạo bài viết AI",
    "dash_quick_action_queue" to "Xem lịch đăng bài",
    "dash_quick_action_add_site" to "Thêm website WP",
    "dash_recent_activity" to "Nhật ký hoạt động gần đây",
    "dash_auto_running" to "Tự động hóa: ĐANG CHẠY",
    "dash_auto_paused" to "Tự động hóa: ĐÃ TẠM DỪNG",

    // Websites
    "sites_title" to "Quản lý Website WordPress",
    "sites_subtitle" to "12 websites trong mạng lưới tự động",
    "sites_add_btn" to "Thêm Website",
    "sites_test_all" to "Kiểm tra toàn bộ REST API",
    "sites_status_connected" to "Kết nối tốt",
    "sites_status_error" to "Lỗi xác thực",
    "sites_status_latency" to "Độ trễ",
    "sites_auth_app_pwd" to "Application Password",
    "sites_auto_publish" to "Tự động đăng",
    "sites_quota" to "Hạn mức ngày",
    "sites_categories" to "Chuyên mục",
    "sites_dialog_add" to "Kết nối Website WordPress mới",
    "sites_dialog_url" to "URL Trang chủ (https://...)",
    "sites_dialog_user" to "Tên đăng nhập Admin",
    "sites_dialog_pwd" to "Application Password",
    "sites_dialog_quota" to "Số bài tối đa mỗi ngày",
    "sites_dialog_test" to "Kiểm tra kết nối REST API",
    "sites_dialog_success" to "REST API v2 hợp lệ (200 OK)",

    // Keyword Research
    "kw_title" to "Nghiên cứu & Phân cụm Từ khóa",
    "kw_subtitle" to "Dữ liệu Google Search Console & SERP Suggest",
    "kw_input_placeholder" to "Nhập chủ đề hoặc từ khóa hạt giống...",
    "kw_search_btn" to "Phân tích",
    "kw_market" to "Thị trường:",
    "kw_language" to "Ngôn ngữ:",
    "kw_col_keyword" to "Từ khóa",
    "kw_col_intent" to "Ý định tìm kiếm",
    "kw_col_volume" to "Volume ước tính",
    "kw_col_kd" to "Độ khó (KD%)",
    "kw_col_cluster" to "Cụm chủ đề",
    "kw_col_priority" to "Ưu tiên",
    "kw_action_write" to "Viết bài bằng AI",

    // Competitor Analysis
    "comp_title" to "Phân tích Đối thủ & Lỗ hổng Nội dung",
    "comp_subtitle" to "Quét cấu trúc HTML và tìm Topic Gaps",
    "comp_url_placeholder" to "Nhập URL bài viết đối thủ (https://...)",
    "comp_analyze_btn" to "Phân tích bài viết",
    "comp_stats_words" to "Số từ công khai",
    "comp_stats_headings" to "Tổng số thẻ H",
    "comp_stats_images" to "Số lượng ảnh",
    "comp_heading_tree" to "Cây cấu trúc tiêu đề (H1 - H3)",
    "comp_topic_gaps" to "Lỗ hổng nội dung (Topic Gaps để vượt đối thủ)",
    "comp_action_outrank" to "Tạo bài viết vượt đối thủ",

    // AI Writer
    "writer_title" to "Soạn thảo Bài viết AI Chuẩn SEO",
    "writer_subtitle" to "Tạo dàn ý, nội dung chuyên sâu và thẻ Schema",
    "writer_target_kw" to "Từ khóa mục tiêu",
    "writer_cluster" to "Cụm chủ đề",
    "writer_length" to "Độ dài mong muốn",
    "writer_tone" to "Văn phong",
    "writer_generate_outline" to "1. Tạo dàn ý H2-H3",
    "writer_generate_full" to "2. Sinh bài viết hoàn chỉnh",
    "writer_tab_editor" to "Trình soạn thảo",
    "writer_tab_preview" to "Xem trước HTML",
    "writer_tab_faq" to "FAQ & Schema",
    "writer_tab_images" to "Đề xuất ảnh AI",
    "writer_meta_title" to "Thẻ tiêu đề SEO (Title Tag)",
    "writer_meta_desc" to "Thẻ mô tả (Meta Description)",
    "writer_btn_gate" to "Chấm điểm Quality Gate",
    "writer_btn_publish" to "Đưa vào hàng đợi đăng",

    // Quality Gate
    "gate_title" to "SEO Quality Gate",
    "gate_subtitle" to "Hệ thống kiểm duyệt tự động trước khi xuất bản",
    "gate_score" to "Điểm chất lượng SEO",
    "gate_pass" to "ĐỦ ĐIỀU KIỆN XUẤT BẢN",
    "gate_fail" to "CẦN CHỈNH SỬA TRƯỚC KHI ĐĂNG",
    "gate_auto_fix" to "AI Tự động tối ưu các lỗi",

    // Publishing Center
    "pub_title" to "Trung tâm Xuất bản WordPress",
    "pub_subtitle" to "Lịch trình tự động, hàng đợi và bài đã đăng",
    "pub_tab_queue" to "Hàng đợi đăng",
    "pub_tab_history" to "Lịch sử đã đăng",
    "pub_btn_publish_now" to "Đăng ngay",
    "pub_btn_retry" to "Thử lại",
    "pub_view_live" to "Xem bài live trên web",

    // Automation Rules
    "auto_title" to "Quy tắc Tự động hóa Toàn trình",
    "auto_subtitle" to "Quy trình khép kín: Nghiên cứu → Viết → Duyệt → Đăng",
    "auto_rule_max_posts" to "Giới hạn tối đa bài/website/ngày:",
    "auto_rule_active_hours" to "Khung giờ chạy tự động:",
    "auto_rule_min_score" to "Ngưỡng điểm Quality Gate tối thiểu để đăng:",
    "auto_rule_stop_budget" to "Dừng khi vượt ngân sách AI:",

    // Cost Control
    "cost_title" to "Quản lý Chi phí API & AI",
    "cost_subtitle" to "Theo dõi tokens và ngân sách các mô hình",
    "cost_spent" to "Đã chi tiêu tháng này",
    "cost_budget" to "Ngân sách tối đa",
    "cost_tokens" to "Tổng Tokens xử lý",
    "cost_articles" to "Chi phí trung bình / bài",

    // Version & Backup
    "backup_title" to "Lịch sử Phiên bản & Sao lưu",
    "backup_subtitle" to "Quản lý snapshot bài viết và sao lưu toàn bộ cấu hình",
    "backup_export" to "Xuất cấu hình & Dữ liệu (JSON)",
    "backup_import" to "Khôi phục từ JSON",
    "backup_restore_sample" to "Nạp lại dữ liệu mẫu 12 Website",

    // Settings
    "settings_title" to "Cài đặt Hệ thống",
    "settings_subtitle" to "Ngôn ngữ, bảo mật khóa API và chế độ hiển thị",
    "settings_lang" to "Ngôn ngữ ứng dụng (Language)",
    "settings_theme" to "Giao diện Tối / Sáng",
    "settings_demo_mode" to "Hiển thị nhãn Chế độ DEMO",
    "settings_api_keys" to "Cấu hình Khóa API & Thông tin xác thực",
    "btn_save" to "Lưu thay đổi",
    "btn_cancel" to "Hủy",
    "btn_close" to "Đóng"
  )

  private val enStrings = mapOf(
    // App & Navigation
    "app_title" to "WP AI SEO Studio",
    "nav_dashboard" to "Dashboard",
    "nav_websites" to "Websites",
    "nav_keywords" to "Keywords",
    "nav_competitors" to "Competitors",
    "nav_ai_writer" to "AI Writer",
    "nav_quality_gate" to "Quality Gate",
    "nav_publishing" to "Publishing",
    "nav_automation" to "Automation",
    "nav_cost" to "AI Cost",
    "nav_backup" to "Backup & History",
    "nav_settings" to "Settings",

    // Dashboard
    "dash_overview_title" to "SEO & Automation Dashboard",
    "dash_overview_subtitle" to "Managing 12+ WordPress affiliate & niche sites",
    "dash_demo_badge" to "DEMO DATA",
    "dash_emergency_stop" to "EMERGENCY STOP",
    "dash_emergency_stopped" to "AUTOMATION STOPPED",
    "dash_emergency_confirm" to "Confirm stopping all automated publishing jobs immediately?",
    "dash_stat_sites" to "Connected Websites",
    "dash_stat_keywords" to "Tracked Keywords",
    "dash_stat_articles" to "Generated Articles",
    "dash_stat_published" to "Published to WP",
    "dash_stat_queue" to "Publishing Queue",
    "dash_stat_failed" to "Failed / Alerts",
    "dash_stat_cost" to "Monthly AI Cost",
    "dash_stat_limit" to "$50.00 Limit",
    "dash_quick_action_keyword" to "Research Keywords",
    "dash_quick_action_writer" to "Create AI Article",
    "dash_quick_action_queue" to "View Schedule",
    "dash_quick_action_add_site" to "Add WP Site",
    "dash_recent_activity" to "Recent System Activity",
    "dash_auto_running" to "Automation: RUNNING",
    "dash_auto_paused" to "Automation: PAUSED",

    // Websites
    "sites_title" to "WordPress Website Manager",
    "sites_subtitle" to "12 multi-niche websites connected via REST API",
    "sites_add_btn" to "Add Website",
    "sites_test_all" to "Ping All REST APIs",
    "sites_status_connected" to "Connected",
    "sites_status_error" to "Auth Error",
    "sites_status_latency" to "Latency",
    "sites_auth_app_pwd" to "Application Password",
    "sites_auto_publish" to "Auto-Publish",
    "sites_quota" to "Daily Quota",
    "sites_categories" to "Categories",
    "sites_dialog_add" to "Connect New WordPress Site",
    "sites_dialog_url" to "Homepage URL (https://...)",
    "sites_dialog_user" to "Admin Username",
    "sites_dialog_pwd" to "Application Password",
    "sites_dialog_quota" to "Max posts per day",
    "sites_dialog_test" to "Test REST API Connection",
    "sites_dialog_success" to "REST API v2 Verified (200 OK)",

    // Keyword Research
    "kw_title" to "Keyword Research & Clustering",
    "kw_subtitle" to "Google Search Console & Live SERP Suggestion Feeds",
    "kw_input_placeholder" to "Enter seed topic or keyword...",
    "kw_search_btn" to "Analyze",
    "kw_market" to "Target Market:",
    "kw_language" to "Language:",
    "kw_col_keyword" to "Keyword",
    "kw_col_intent" to "Search Intent",
    "kw_col_volume" to "Est. Volume",
    "kw_col_kd" to "KD%",
    "kw_col_cluster" to "Topic Cluster",
    "kw_col_priority" to "Priority",
    "kw_action_write" to "Draft with AI",

    // Competitor Analysis
    "comp_title" to "Competitor Analysis & Topic Gaps",
    "comp_subtitle" to "Scan competitor HTML structure to find ranking gaps",
    "comp_url_placeholder" to "Enter competitor article URL (https://...)",
    "comp_analyze_btn" to "Inspect URL",
    "comp_stats_words" to "Public Words",
    "comp_stats_headings" to "Heading Tags",
    "comp_stats_images" to "Images Found",
    "comp_heading_tree" to "Heading Tree (H1 - H3)",
    "comp_topic_gaps" to "Missing Content Gaps (To Outrank Competitor)",
    "comp_action_outrank" to "Generate Outranking Article",

    // AI Writer
    "writer_title" to "AI SEO Article Writer",
    "writer_subtitle" to "Generate high-ranking content, meta tags, and FAQ schema",
    "writer_target_kw" to "Target Keyword",
    "writer_cluster" to "Topic Cluster",
    "writer_length" to "Target Word Count",
    "writer_tone" to "Tone of Voice",
    "writer_generate_outline" to "1. Generate Outline",
    "writer_generate_full" to "2. Generate Full Article",
    "writer_tab_editor" to "Content Editor",
    "writer_tab_preview" to "HTML Preview",
    "writer_tab_faq" to "FAQ & Schema",
    "writer_tab_images" to "AI Image Prompts",
    "writer_meta_title" to "SEO Title Tag",
    "writer_meta_desc" to "Meta Description",
    "writer_btn_gate" to "Audit with Quality Gate",
    "writer_btn_publish" to "Queue for Publishing",

    // Quality Gate
    "gate_title" to "SEO Quality Gate",
    "gate_subtitle" to "Automated pre-flight compliance & ranking checks",
    "gate_score" to "SEO Quality Score",
    "gate_pass" to "PASSED - READY FOR PUBLISH",
    "gate_fail" to "FAILED - ACTION REQUIRED",
    "gate_auto_fix" to "AI Auto-Fix Issues",

    // Publishing Center
    "pub_title" to "WordPress Publishing Center",
    "pub_subtitle" to "Scheduled queue, live URLs, and execution logs",
    "pub_tab_queue" to "Publish Queue",
    "pub_tab_history" to "Live Published",
    "pub_btn_publish_now" to "Publish Now",
    "pub_btn_retry" to "Retry",
    "pub_view_live" to "View Live Post",

    // Automation Rules
    "auto_title" to "End-to-End Automation Rules",
    "auto_subtitle" to "Closed loop: Discovery → Drafting → Quality Gate → Publishing",
    "auto_rule_max_posts" to "Max posts per site per day:",
    "auto_rule_active_hours" to "Publishing active schedule window:",
    "auto_rule_min_score" to "Minimum Quality Gate score to publish:",
    "auto_rule_stop_budget" to "Halt if AI monthly budget exceeded:",

    // Cost Control
    "cost_title" to "AI & API Cost Control",
    "cost_subtitle" to "Monitor token usage, model distribution, and quotas",
    "cost_spent" to "Current Month Spend",
    "cost_budget" to "Monthly Budget Cap",
    "cost_tokens" to "Total Tokens Processed",
    "cost_articles" to "Average Cost / Article",

    // Version & Backup
    "backup_title" to "Version History & Backup",
    "backup_subtitle" to "Draft version control and full JSON system backup",
    "backup_export" to "Export System Backup (JSON)",
    "backup_import" to "Import System from JSON",
    "backup_restore_sample" to "Restore 12 Sample Websites",

    // Settings
    "settings_title" to "System Settings",
    "settings_subtitle" to "Language, API credentials, and runtime preferences",
    "settings_lang" to "Application Language",
    "settings_theme" to "Dark / Light Theme",
    "settings_demo_mode" to "Show DEMO Data Badge",
    "settings_api_keys" to "API Credentials & Secret Keys",
    "btn_save" to "Save Changes",
    "btn_cancel" to "Cancel",
    "btn_close" to "Close"
  )

  fun t(key: String, lang: AppLanguage): String {
    val dict = if (lang == AppLanguage.VI) viStrings else enStrings
    return dict[key] ?: key
  }
}
