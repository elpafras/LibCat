package mr.cat.setting.utility

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import mr.cat.setting.component.model.ThemeColors
import mr.cat.setting.component.model.defaultThemes
import kotlin.math.max
import kotlin.math.min

/**
 * Data class untuk merepresentasikan warna tema di sisi Jetpack Compose.
 * Mendukung slot ColorScheme Material3 secara komprehensif untuk memastikan 
 * konsistensi pada komponen container seperti Drawer dan Bottom Sheet.
 */
data class SettingThemeColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceContainer: Color,
    val onSurfaceContainer: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val outline: Color,
    val error: Color,
    val onError: Color,
    val isDark: Boolean
)

/**
 * Registry untuk memetakan themeId ke objek [SettingThemeColors].
 */
object ThemeRegistry {

    /**
     * Menghitung rasio kontras antara dua warna berdasarkan standar WCAG.
     * Rasio yang dikembalikan berkisar antara 1.0 hingga 21.0.
     */
    fun calculateContrastRatio(foreground: Color, background: Color): Double {
        val lum1 = foreground.luminance()
        val lum2 = background.luminance()
        val brightest = max(lum1, lum2)
        val darkest = min(lum1, lum2)
        return (brightest + 0.05) / (darkest + 0.05)
    }

    /**
     * Validasi kontras tema. Mengembalikan daftar pesan error jika ada pasangan 
     * warna yang tidak memenuhi rasio kontras minimum 4.5:1 (WCAG AA).
     */
    fun validateThemeContrast(theme: SettingThemeColors): List<String> {
        val errors = mutableListOf<String>()
        val check = { fg: Color, bg: Color, label: String ->
            val ratio = calculateContrastRatio(fg, bg)
            if (ratio < 4.5) {
                // Manually format to 2 decimal places for common code compatibility
                val ratioText = ((ratio * 100).toInt() / 100.0).toString()
                errors.add("$label contrast ratio is too low: $ratioText:1 (Min 4.5:1)")
            }
        }

        check(theme.onBackground, theme.background, "Background")
        check(theme.onSurface, theme.surface, "Surface")
        check(theme.onSurfaceContainer, theme.surfaceContainer, "Surface Container")
        check(theme.onSurfaceVariant, theme.surfaceVariant, "Surface Variant")
        check(theme.onPrimary, theme.primary, "Primary")
        check(theme.onPrimaryContainer, theme.primaryContainer, "Primary Container")
        
        return errors
    }

    /**
     * Menyelesaikan (resolve) warna tema berdasarkan [themeId].
     * Seluruh slot container didefinisikan secara eksplisit untuk menjaga konsistensi.
     */
    fun resolveThemeColors(themeId: String): SettingThemeColors {
        val themeOption = defaultThemes.find { it.id == themeId } ?: defaultThemes.first()
        val colors = themeOption.colors
        val isDark = colors.isDark

        return when (themeOption.id) {
            "default" -> SettingThemeColors(
                background = colors.background,
                onBackground = colors.text,
                surface = colors.background,
                onSurface = colors.text,
                surfaceContainer = Color(0xFFE2E4E9), // Light grayish indigo
                onSurfaceContainer = colors.text,
                surfaceVariant = Color(0xFFDEE1E9),
                onSurfaceVariant = colors.text.copy(alpha = 0.8f),
                primary = colors.topBar,
                onPrimary = colors.topBarText,
                primaryContainer = colors.topBar.copy(alpha = 0.12f),
                onPrimaryContainer = colors.topBar,
                outline = colors.text.copy(alpha = 0.12f),
                error = Color(0xFFB00020),
                onError = Color.White,
                isDark = isDark
            )
            "hvs" -> SettingThemeColors(
                background = colors.background,
                onBackground = colors.text,
                surface = colors.background,
                onSurface = colors.text,
                surfaceContainer = Color(0xFFF2F2F2),
                onSurfaceContainer = colors.text,
                surfaceVariant = Color(0xFFEBEBEB),
                onSurfaceVariant = colors.text.copy(alpha = 0.8f),
                primary = colors.topBar,
                onPrimary = colors.topBarText,
                primaryContainer = Color(0xFFE0E0E0),
                onPrimaryContainer = colors.topBar,
                outline = Color(0xFFCCCCCC),
                error = Color(0xFFB00020),
                onError = Color.White,
                isDark = isDark
            )
            "papan_tulis" -> SettingThemeColors(
                background = colors.background,
                onBackground = colors.text,
                surface = colors.background,
                onSurface = colors.text,
                surfaceContainer = Color(0xFF252525),
                onSurfaceContainer = colors.text,
                surfaceVariant = Color(0xFF333333),
                onSurfaceVariant = colors.text.copy(alpha = 0.8f),
                primary = colors.topBar,
                onPrimary = colors.topBarText,
                primaryContainer = Color(0xFF404040),
                onPrimaryContainer = colors.text,
                outline = colors.text.copy(alpha = 0.2f),
                error = Color(0xFFCF6679),
                onError = Color.Black,
                isDark = isDark
            )
            else -> {
                val (sc, osc) = if (isDark) {
                    Color(0xFF2C2C2C) to colors.text
                } else {
                    Color(0xFFF5F5F5) to colors.text
                }

                SettingThemeColors(
                    background = colors.background,
                    onBackground = colors.text,
                    surface = colors.background,
                    onSurface = colors.text,
                    surfaceContainer = sc,
                    onSurfaceContainer = osc,
                    surfaceVariant = sc.copy(alpha = 0.8f),
                    onSurfaceVariant = osc.copy(alpha = 0.7f),
                    primary = colors.topBar,
                    onPrimary = colors.topBarText,
                    primaryContainer = colors.topBar.copy(alpha = 0.2f),
                    onPrimaryContainer = if (isDark) colors.text else colors.topBar,
                    outline = colors.text.copy(alpha = 0.3f),
                    error = if (isDark) Color(0xFFCF6679) else Color(0xFFB00020),
                    onError = if (isDark) Color.Black else Color.White,
                    isDark = isDark
                )
            }
        }
    }
}
