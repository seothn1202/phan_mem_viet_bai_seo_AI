package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = WpBlueLight,
  onPrimary = DarkBackground,
  primaryContainer = WpBluePrimary,
  onPrimaryContainer = Color.White,
  secondary = VioletAiLight,
  onSecondary = Color.Black,
  secondaryContainer = VioletAiBg,
  onSecondaryContainer = Color.White,
  tertiary = WpCyanAccent,
  onTertiary = Color.Black,
  background = DarkBackground,
  onBackground = DarkTextPrimary,
  surface = DarkSurface,
  onSurface = DarkTextPrimary,
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkBorder,
  error = RoseError,
  onError = Color.White,
  errorContainer = RoseErrorBg,
  onErrorContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
  primary = WpBluePrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE0F2FE),
  onPrimaryContainer = Color(0xFF0369A1),
  secondary = VioletAi,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFEDE9FE),
  onSecondaryContainer = Color(0xFF5B21B6),
  tertiary = WpCyanAccent,
  onTertiary = Color.White,
  background = LightBackground,
  onBackground = LightTextPrimary,
  surface = LightSurface,
  onSurface = LightTextPrimary,
  surfaceVariant = LightSurfaceVariant,
  onSurfaceVariant = LightTextSecondary,
  outline = LightBorder,
  error = RoseError,
  onError = Color.White,
  errorContainer = Color(0xFFFEE2E2),
  onErrorContainer = Color(0xFF991B1B)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to sleek tech studio dark mode
  dynamicColor: Boolean = false, // Keep consistent high-contrast branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
