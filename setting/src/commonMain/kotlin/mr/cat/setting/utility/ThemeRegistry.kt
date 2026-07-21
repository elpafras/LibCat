package mr.cat.setting.utility

import androidx.compose.ui.graphics.Color
import mr.cat.setting.component.model.ThemeColors
import mr.cat.setting.component.model.defaultThemes

/**
 * Data class untuk merepresentasikan warna tema di sisi Jetpack Compose.
 * Mendukung slot ColorScheme Material3 secara komprehensif.
 */
data class SettingThemeColors(
    val background: Color,
    val textColor: Color,
    val accentColor: Color,
    val topBarBackground: Color,
    val topBarText: Color,
    val surface: Color = background,
    val onSurface: Color = textColor,
    val surfaceVariant: Color = background.copy(alpha = 0.9f),
    val onSurfaceVariant: Color = textColor.copy(alpha = 0.8f),
    val primary: Color = topBarBackground,
    val onPrimary: Color = topBarText,
    val secondary: Color = accentColor,
    val onSecondary: Color = topBarText,
    val outline: Color = textColor.copy(alpha = 0.5f),
    val error: Color = Color(0xFFB00020), // Default error color
    val onError: Color = Color.White
)

/**
 * Registry untuk memetakan themeId ke objek [SettingThemeColors].
 */
object ThemeRegistry {

    /**
     * Menyelesaikan (resolve) warna tema berdasarkan [themeId].
     * Jika ID tidak ditemukan, akan mengembalikan tema default (HVS/Putih).
     */
    fun resolveThemeColors(themeId: String): SettingThemeColors {
        val themeOption = defaultThemes.find { it.id == themeId } ?: defaultThemes.first()
        val colors = themeOption.colors

        return SettingThemeColors(
            background = colors.background,
            textColor = colors.text,
            accentColor = colors.topBar,
            topBarBackground = colors.topBar,
            topBarText = colors.topBarText,
            surface = colors.background,
            onSurface = colors.text,
            primary = colors.topBar,
            onPrimary = colors.topBarText
        )
    }
}
