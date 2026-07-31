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
    val highlight: Color,
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
     * Memastikan warna foreground memiliki kontras yang cukup terhadap background.
     * Jika rasio kontras di bawah ambang batas (default 4.5), kembalikan Hitam atau Putih.
     */
    fun ensureContrast(foreground: Color, background: Color, threshold: Double = 4.5): Color {
        val currentRatio = calculateContrastRatio(foreground, background)
        if (currentRatio >= threshold) return foreground

        val whiteRatio = calculateContrastRatio(Color.White, background)
        val blackRatio = calculateContrastRatio(Color.Black, background)

        return if (whiteRatio > blackRatio) Color.White else Color.Black
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

        val bg = colors.background
        val txt = ensureContrast(colors.text, bg)
        val tb = colors.topBar
        val tbt = ensureContrast(colors.topBarText, tb)

        return when (themeOption.id) {
            "default" -> SettingThemeColors(
                background = bg,
                onBackground = txt,
                surface = bg,
                onSurface = txt,
                surfaceContainer = Color(0xFFE2E4E9), // Light grayish indigo
                onSurfaceContainer = txt,
                surfaceVariant = Color(0xFFDEE1E9),
                onSurfaceVariant = txt.copy(alpha = 0.8f),
                primary = tb,
                onPrimary = tbt,
                primaryContainer = tb.copy(alpha = 0.12f),
                onPrimaryContainer = tb,
                outline = txt.copy(alpha = 0.12f),
                error = Color(0xFFB00020),
                onError = Color.White,
                highlight = Color(0xFFFFE082), // Amber 200
                isDark = isDark
            )
            "hvs" -> SettingThemeColors(
                background = bg,
                onBackground = txt,
                surface = bg,
                onSurface = txt,
                surfaceContainer = Color(0xFFF2F2F2),
                onSurfaceContainer = txt,
                surfaceVariant = Color(0xFFEBEBEB),
                onSurfaceVariant = txt.copy(alpha = 0.8f),
                primary = tb,
                onPrimary = tbt,
                primaryContainer = Color(0xFFE0E0E0),
                onPrimaryContainer = tb,
                outline = Color(0xFFCCCCCC),
                error = Color(0xFFB00020),
                onError = Color.White,
                highlight = Color(0xFFFFF176), // Yellow 300
                isDark = isDark
            )
            "papan_tulis" -> SettingThemeColors(
                background = bg,
                onBackground = txt,
                surface = bg,
                onSurface = txt,
                surfaceContainer = Color(0xFF252525),
                onSurfaceContainer = txt,
                surfaceVariant = Color(0xFF333333),
                onSurfaceVariant = txt.copy(alpha = 0.8f),
                primary = tb,
                onPrimary = tbt,
                primaryContainer = Color(0xFF404040),
                onPrimaryContainer = txt,
                outline = txt.copy(alpha = 0.2f),
                error = Color(0xFFCF6679),
                onError = Color.Black,
                highlight = Color(0xFFFFD54F).copy(alpha = 0.4f),
                isDark = isDark
            )
            else -> {
                val (sc, osc) = if (isDark) {
                    Color(0xFF2C2C2C) to txt
                } else {
                    Color(0xFFF5F5F5) to txt
                }

                SettingThemeColors(
                    background = bg,
                    onBackground = txt,
                    surface = bg,
                    onSurface = txt,
                    surfaceContainer = sc,
                    onSurfaceContainer = osc,
                    surfaceVariant = sc.copy(alpha = 0.8f),
                    onSurfaceVariant = osc.copy(alpha = 0.7f),
                    primary = tb,
                    onPrimary = tbt,
                    primaryContainer = tb.copy(alpha = 0.2f),
                    onPrimaryContainer = if (isDark) txt else tb,
                    outline = txt.copy(alpha = 0.3f),
                    error = if (isDark) Color(0xFFCF6679) else Color(0xFFB00020),
                    onError = if (isDark) Color.Black else Color.White,
                    highlight = if (isDark) tb.copy(alpha = 0.4f) else tb.copy(alpha = 0.2f),
                    isDark = isDark
                )
            }
        }
    }
}
