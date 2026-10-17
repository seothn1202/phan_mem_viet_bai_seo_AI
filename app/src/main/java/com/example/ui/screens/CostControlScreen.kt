package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppLanguage
import com.example.data.CostSummary
import com.example.data.I18n
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.RoseError
import com.example.ui.theme.VioletAi
import com.example.ui.theme.WpBlueLight
import com.example.ui.theme.WpCyanAccent

@Composable
fun CostControlScreen(
  language: AppLanguage,
  summary: CostSummary,
  onUpdateBudget: (Double, Double, Int, Boolean, String) -> Unit = { _, _, _, _, _ -> },
  onToggleFreeTier: (Boolean) -> Unit = {},
  modifier: Modifier = Modifier
) {
  var monthlyCapInput by remember(summary.budgetCapUsd) { mutableStateOf(summary.budgetCapUsd.toInt().toString()) }
  var monthlySliderValue by remember(summary.budgetCapUsd) { mutableFloatStateOf(summary.budgetCapUsd.toFloat()) }
  var dailyCapInput by remember(summary.dailyBudgetCapUsd) { mutableStateOf(String.format("%.1f", summary.dailyBudgetCapUsd)) }
  var dailySliderValue by remember(summary.dailyBudgetCapUsd) { mutableFloatStateOf(summary.dailyBudgetCapUsd.toFloat()) }
  var alertThreshold by remember(summary.alertThresholdPercent) { mutableIntStateOf(summary.alertThresholdPercent) }
  var autoHaltOnLimit by remember(summary.autoHaltOnLimit) { mutableStateOf(summary.autoHaltOnLimit) }
  var selectedModel by remember(summary.topModel) { mutableStateOf(summary.topModel) }
  var isFreeMode by remember(summary.isFreeTierMode) { mutableStateOf(summary.isFreeTierMode) }
  var showSaveSuccess by remember { mutableStateOf(false) }

  val currentMonthlyBudget = if (isFreeMode) 0.0 else (monthlyCapInput.toDoubleOrNull() ?: monthlySliderValue.toDouble())
  val spendFraction = if (currentMonthlyBudget > 0) {
    (summary.currentSpendUsd / currentMonthlyBudget).toFloat().coerceIn(0f, 1f)
  } else 0f

  val modelsList = listOf(
    Pair("Gemini 1.5 Flash", if (language == AppLanguage.VI) "Tiết kiệm 85% (~$0.015/bài hoặc 100% Free)" else "85% cheaper (~$0.015/post or 100% Free)"),
    Pair("Gemini 1.5 Pro", if (language == AppLanguage.VI) "Chuẩn chuyên sâu (~$0.043/bài)" else "Standard in-depth (~$0.043/post)"),
    Pair("GPT-4o", if (language == AppLanguage.VI) "Mô hình cao cấp (~$0.085/bài)" else "Premium engine (~$0.085/post)")
  )

  val quickBudgetPresets = listOf(0, 25, 50, 75, 100, 150)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 0. 100% Free Tier Mode Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isFreeMode) EmeraldSuccess.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
          1.dp,
          if (isFreeMode) EmeraldSuccess else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(if (isFreeMode) EmeraldSuccess else MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  Icons.Default.AutoAwesome,
                  contentDescription = null,
                  tint = if (isFreeMode) Color.White else MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = if (language == AppLanguage.VI) "Chế độ Miễn Phí (100% Free)" else "100% Free Tier Mode",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = EmeraldSuccess
                  ) {
                    Text(
                      text = "$0.00",
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Black,
                      color = Color.White,
                      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                  }
                }
                Text(
                  text = if (language == AppLanguage.VI)
                    "Dùng Gemini API Free Quota (15 RPM) • Không tốn phí"
                  else
                    "Google Gemini Free Quota (15 RPM) • Zero billing",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Switch(
              checked = isFreeMode,
              onCheckedChange = {
                isFreeMode = it
                onToggleFreeTier(it)
                if (it) {
                  monthlySliderValue = 0f
                } else {
                  monthlySliderValue = 50f
                }
              },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = EmeraldSuccess
              ),
              modifier = Modifier.testTag("switch_free_tier_mode")
            )
          }

          if (isFreeMode) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EmeraldSuccess.copy(alpha = 0.2f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (language == AppLanguage.VI)
                    "Đang kích hoạt: Mọi bài viết AI sẽ tận dụng hạn mức miễn phí 15 requests/phút của Gemini. Chi phí = $0.00."
                  else
                    "Active: All AI drafting routes through Gemini 15 RPM free quota. Total Cost = $0.00.",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = EmeraldSuccess
                )
              }
            }
          }
        }
      }
    }

    // 1. Live Spend Gauge Card
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

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
          ) {
            Text(
              text = "$${String.format("%.2f", summary.currentSpendUsd)}",
              fontSize = 32.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "/ $${String.format("%.2f", currentMonthlyBudget)} ${if (language == AppLanguage.VI) "hạn mức tháng" else "monthly cap"}",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { spendFraction },
            modifier = Modifier
              .fillMaxWidth()
              .height(10.dp)
              .clip(RoundedCornerShape(5.dp)),
            color = if (spendFraction >= 0.85f) RoseError else if (spendFraction >= 0.7f) AmberWarning else EmeraldSuccess,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )

          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "${(spendFraction * 100).toInt()}% ${if (language == AppLanguage.VI) "đã dùng" else "used"}",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (spendFraction >= 0.85f) RoseError else MaterialTheme.colorScheme.onSurfaceVariant
            )
            val remaining = (currentMonthlyBudget - summary.currentSpendUsd).coerceAtLeast(0.0)
            Text(
              text = "${if (language == AppLanguage.VI) "Còn lại" else "Remaining"}: $${String.format("%.2f", remaining)}",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = EmeraldSuccess
            )
          }
        }
      }
    }

    // 2. Interactive Budget Configuration Form (User can edit directly!)
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (language == AppLanguage.VI) "Điều chỉnh Hạn mức Ngân sách" else "Configure Budget Quota",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Monthly Cap Direct Input & Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (language == AppLanguage.VI) "Ngân sách tháng tối đa:" else "Monthly Budget Cap:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
              )
              Text(
                text = if (language == AppLanguage.VI) "Nhập trực tiếp số tiền hoặc kéo thanh trượt" else "Type amount directly or drag slider",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            OutlinedTextField(
              value = monthlyCapInput,
              onValueChange = { input ->
                monthlyCapInput = input.filter { it.isDigit() }
                val num = monthlyCapInput.toFloatOrNull() ?: 0f
                monthlySliderValue = num.coerceIn(0f, 200f)
                showSaveSuccess = false
              },
              prefix = { Text("$", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
              suffix = { Text("USD", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                .width(130.dp)
                .testTag("input_monthly_budget"),
              shape = RoundedCornerShape(10.dp)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Slider(
            value = monthlySliderValue,
            onValueChange = {
              monthlySliderValue = it
              monthlyCapInput = it.toInt().toString()
              showSaveSuccess = false
            },
            valueRange = 0f..200f,
            steps = 20,
            colors = SliderDefaults.colors(
              thumbColor = MaterialTheme.colorScheme.primary,
              activeTrackColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("slider_monthly_budget")
          )

          // Quick preset buttons: $0 (Free), $25, $50, $75, $100, $150
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            quickBudgetPresets.forEach { preset ->
              val isSelected = (monthlyCapInput.toIntOrNull() ?: monthlySliderValue.toInt()) == preset
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .clickable {
                    monthlySliderValue = preset.toFloat()
                    monthlyCapInput = preset.toString()
                    showSaveSuccess = false
                  }
              ) {
                Text(
                  text = if (preset == 0) "Free $0" else "$$preset",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                  textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                  modifier = Modifier.padding(vertical = 6.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Daily Cap Direct Input & Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (language == AppLanguage.VI) "Hạn mức tối đa mỗi ngày:" else "Daily Budget Limit:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
              )
              Text(
                text = if (language == AppLanguage.VI) "Khống chế trần chi tiêu theo ngày" else "Daily spend limit safeguard",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            OutlinedTextField(
              value = dailyCapInput,
              onValueChange = { input ->
                dailyCapInput = input.filter { it.isDigit() || it == '.' }
                val num = dailyCapInput.toFloatOrNull() ?: 0f
                dailySliderValue = num.coerceIn(0f, 20f)
                showSaveSuccess = false
              },
              prefix = { Text("$", fontWeight = FontWeight.Bold, color = VioletAi) },
              suffix = { Text("/ngày", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              modifier = Modifier
                .width(130.dp)
                .testTag("input_daily_budget"),
              shape = RoundedCornerShape(10.dp)
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Slider(
            value = dailySliderValue,
            onValueChange = {
              dailySliderValue = it
              dailyCapInput = String.format("%.1f", it)
              showSaveSuccess = false
            },
            valueRange = 0f..20f,
            steps = 20,
            colors = SliderDefaults.colors(
              thumbColor = VioletAi,
              activeTrackColor = VioletAi
            ),
            modifier = Modifier.testTag("slider_daily_budget")
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Alert Threshold Picker
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = AmberWarning, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (language == AppLanguage.VI) "Cảnh báo khi chạm:" else "Alert Threshold:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
              )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              listOf(70, 80, 85, 90).forEach { pct ->
                val isSelected = alertThreshold == pct
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = if (isSelected) AmberWarning else MaterialTheme.colorScheme.surfaceVariant,
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable {
                      alertThreshold = pct
                      showSaveSuccess = false
                    }
                ) {
                  Text(
                    text = "$pct%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Auto-Halt Toggle
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (language == AppLanguage.VI) "Tự động ngắt khi chạm hạn mức" else "Auto-Halt on Budget Exceeded",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = if (language == AppLanguage.VI) "Tạm dừng viết bài AI để chống tràn hóa đơn" else "Pause generation to prevent over-budget charges",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Switch(
              checked = autoHaltOnLimit,
              onCheckedChange = {
                autoHaltOnLimit = it
                showSaveSuccess = false
              },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = EmeraldSuccess
              ),
              modifier = Modifier.testTag("switch_auto_halt_budget")
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Model Selection Preference
          Text(
            text = if (language == AppLanguage.VI) "Mô hình AI mặc định (Model Routing):" else "Default AI Model Routing:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(8.dp))

          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            modelsList.forEach { (modelName, desc) ->
              val isSelected = selectedModel == modelName
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                border = BorderStroke(
                  1.dp,
                  if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .clickable {
                    selectedModel = modelName
                    showSaveSuccess = false
                  }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                      modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = modelName, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                  Text(text = desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              val targetMonthly = if (isFreeMode) 0.0 else (monthlyCapInput.toDoubleOrNull() ?: monthlySliderValue.toDouble())
              val targetDaily = if (isFreeMode) 0.0 else (dailyCapInput.toDoubleOrNull() ?: dailySliderValue.toDouble())
              onUpdateBudget(
                targetMonthly,
                targetDaily,
                alertThreshold,
                autoHaltOnLimit,
                selectedModel
              )
              showSaveSuccess = true
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("btn_save_budget_settings")
          ) {
            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (language == AppLanguage.VI) "Lưu Hạn Mức & Áp Dụng Ngay" else "Save & Apply Budget",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Save Confirmation Notice
          AnimatedVisibility(visible = showSaveSuccess) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EmeraldSuccess.copy(alpha = 0.15f),
              border = BorderStroke(1.dp, EmeraldSuccess.copy(alpha = 0.4f)),
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (language == AppLanguage.VI)
                    "Đã lưu thành công! Hạn mức mới $$monthlySliderValue/tháng và model $selectedModel đã có hiệu lực."
                  else
                    "Saved successfully! New limit $$monthlySliderValue/mo and model $selectedModel applied.",
                  fontSize = 11.sp,
                  color = EmeraldSuccess,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }
          }
        }
      }
    }

    // 3. Token Breakdown Cards
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
            Text(text = "~ $4.26 (Chiếm 28%)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            Text(text = "~ $10.59 (Chiếm 72%)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }
    }

    // 4. Efficiency & Volume Metric
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
            text = "$${summary.avgCostPerArticle} / bài",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldSuccess
          )
        }
      }
    }

    // 5. Model Routing & Tips
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
