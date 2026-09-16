package mr.cat.libcat.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.rememberSettingState
import mr.cat.setting.utility.ThemeRegistry
import mr.cat.setting.utility.rememberSystemBarsController

/**
 * LibCatTheme (modul app) SENGAJA mengabaikan pengaturan mode gelap/terang sistem 
 * dan Dynamic Color. Seluruh tampilan visual mengunci pada pilihan tema pengguna.
 *
 * Mulai versi ini, Status Bar dan Navigation Bar sistem OTOMATIS sinkron dengan tema aktif.
 */
@Composable
fun LibCatTheme(
    content: @Composable () -> Unit,
) {
    val settings = rememberSettingState()
    val themeId = settings.theme.id
    val fontFamily = settings.fontStyle.toFontFamily()
    val fontSize = settings.fontSize.sp
    val fontWeight = FontWeight.Normal

    val settingColors = remember(themeId) {
        ThemeRegistry.resolveThemeColors(themeId)
    }

    // System Bars Sync
    val systemBarsController = rememberSystemBarsController()
    SideEffect {
        systemBarsController?.applyTheme(
            statusBarColor = settingColors.background,
            navigationBarColor = settingColors.surfaceContainer,
            useDarkIcons = ThemeRegistry.calculateContrastRatio(Color.Black, settingColors.background) > 4.5
        )
    }

    val colorScheme = remember(settingColors) {
        val base = if (settingColors.isDark) darkColorScheme() else lightColorScheme()
        base.copy(
            primary = settingColors.primary,
            onPrimary = settingColors.onPrimary,
            primaryContainer = settingColors.primaryContainer,
            onPrimaryContainer = settingColors.onPrimaryContainer,
            background = settingColors.background,
            onBackground = settingColors.onBackground,
            surface = settingColors.surface,
            onSurface = settingColors.onSurface,
            surfaceVariant = settingColors.surfaceVariant,
            onSurfaceVariant = settingColors.onSurfaceVariant,
            surfaceContainer = settingColors.surfaceContainer,
            surfaceContainerHigh = settingColors.surfaceContainer,
            surfaceContainerLow = settingColors.surfaceContainer,
            surfaceContainerLowest = settingColors.surfaceContainer,
            surfaceContainerHighest = settingColors.surfaceContainer,
            outline = settingColors.outline,
            error = settingColors.error,
            onError = settingColors.onError,
        )
    }

    val typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontSize = fontSize,
            lineHeight = (fontSize.value + 8).sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontSize = (fontSize.value + 6).sp,
            lineHeight = (fontSize.value + 14).sp,
            letterSpacing = 0.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontSize = (fontSize.value - 2).sp,
            lineHeight = (fontSize.value + 4).sp,
            letterSpacing = 0.5.sp
        )
    )

    CompositionLocalProvider(LocalLibCatSettings provides settings) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
