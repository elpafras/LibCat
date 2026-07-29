package mr.cat.setting.compose

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import mr.cat.setting.utility.ThemeRegistry
import mr.cat.setting.viewmodel.SettingViewModel

/**
 * LibCatTheme adalah Composable pengganti MaterialTheme yang secara reaktif 
 * menyesuaikan seluruh komponen UI berdasarkan pengaturan pengguna di [SettingViewModel].
 *
 * @IMPORTANT
 * LibCatTheme WAJIB dipasang di root aplikasi (menggantikan MaterialTheme bawaan project),
 * supaya seluruh komponen Material3 di bawahnya (Card, Scaffold, Button, dll) 
 * otomatis mengikuti tema (warna, font, ukuran) dari LibCat.
 *
 * @NOTE
 * Untuk konten di dalam WebView, LibCatTheme TIDAK berlaku. Anda tetap perlu
 * menggunakan SettingManager terpisah untuk sinkronisasi CSS ke dalam WebView.
 *
 * Example Usage:
 * ```kotlin
 * setContent {
 *     LibCatTheme(viewModel = settingViewModel) {
 *         MainScreenContent()
 *     }
 * }
 * ```
 */
@Composable
fun LibCatTheme(
    viewModel: SettingViewModel,
    content: @Composable () -> Unit,
) {
    val themeId by viewModel.themeId.collectAsState()
    val fontStyle by viewModel.fontStyle.collectAsState()
    val fontSizeOption by viewModel.fontSize.collectAsState()

    // 1. Resolve Colors
    val settingColors = remember(themeId) {
        ThemeRegistry.resolveThemeColors(themeId)
    }

    val colorScheme = remember(settingColors) {
        // Menggunakan lightColorScheme sebagai base, lalu override dengan settingColors
        lightColorScheme(
            primary = settingColors.primary,
            onPrimary = settingColors.onPrimary,
            background = settingColors.background,
            onBackground = settingColors.textColor,
            surface = settingColors.surface,
            onSurface = settingColors.onSurface,
            surfaceVariant = settingColors.surfaceVariant,
            onSurfaceVariant = settingColors.onSurfaceVariant,
            secondary = settingColors.secondary,
            onSecondary = settingColors.onSecondary,
            outline = settingColors.outline,
            error = settingColors.error,
            onError = settingColors.onError,
        )
    }

    // 2. Resolve Font & Typography
    var fontFamily: FontFamily by remember { mutableStateOf(FontFamily.Default) }
    
    LaunchedEffect(fontStyle) {
        fontFamily = loadFontFamily(fontStyle)
    }

    val baseSize = fontSizeOption
    
    val typography = remember(fontFamily, baseSize) {
        // Skala proporsional berdasarkan base fontSize (default 14sp)
        val scale = baseSize / 14f
        
        Typography(
            displayLarge = TextStyle(fontFamily = fontFamily, fontSize = (57 * scale).sp),
            displayMedium = TextStyle(fontFamily = fontFamily, fontSize = (45 * scale).sp),
            displaySmall = TextStyle(fontFamily = fontFamily, fontSize = (36 * scale).sp),
            headlineLarge = TextStyle(fontFamily = fontFamily, fontSize = (32 * scale).sp),
            headlineMedium = TextStyle(fontFamily = fontFamily, fontSize = (28 * scale).sp),
            headlineSmall = TextStyle(fontFamily = fontFamily, fontSize = (24 * scale).sp),
            titleLarge = TextStyle(fontFamily = fontFamily, fontSize = (22 * scale).sp),
            titleMedium = TextStyle(fontFamily = fontFamily, fontSize = (16 * scale).sp),
            titleSmall = TextStyle(fontFamily = fontFamily, fontSize = (14 * scale).sp),
            bodyLarge = TextStyle(fontFamily = fontFamily, fontSize = (16 * scale).sp),
            bodyMedium = TextStyle(fontFamily = fontFamily, fontSize = (14 * scale).sp),
            bodySmall = TextStyle(fontFamily = fontFamily, fontSize = (12 * scale).sp),
            labelLarge = TextStyle(fontFamily = fontFamily, fontSize = (14 * scale).sp),
            labelMedium = TextStyle(fontFamily = fontFamily, fontSize = (12 * scale).sp),
            labelSmall = TextStyle(fontFamily = fontFamily, fontSize = (11 * scale).sp),
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
