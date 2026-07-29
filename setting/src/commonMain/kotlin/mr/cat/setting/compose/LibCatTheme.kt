package mr.cat.setting.compose

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import mr.cat.setting.utility.SystemBarsController
import mr.cat.setting.utility.SystemBarsUtils
import mr.cat.setting.utility.ThemeRegistry
import mr.cat.setting.utility.rememberSystemBarsController
import mr.cat.setting.viewmodel.SettingViewModel

/**
 * LibCatTheme adalah Composable pengganti MaterialTheme yang secara reaktif 
 * menyesuaikan seluruh komponen UI berdasarkan pengaturan pengguna di [SettingViewModel].
 *
 * @IMPORTANT
 * LibCatTheme SENGAJA mengabaikan pengaturan mode gelap/terang sistem operasi 
 * dan Dynamic Color (Material You). Seluruh tampilan visual aplikasi yang memakai 
 * LibCat WAJIB mengikuti themeId yang dipilih user melalui SettingBottomSheet, 
 * terlepas dari pengaturan sistem perangkat. Ini untuk menjaga konsistensi 
 * pengalaman baca/konten lintas device dan mencegah konflik antara dua sumber 
 * preferensi tema (sistem vs aplikasi).
 *
 * Developer TIDAK PERLU dan TIDAK DISARANKAN memanggil isSystemInDarkTheme() 
 * secara manual di dalam scope LibCatTheme, karena state ColorScheme sudah dikunci 
 * oleh ThemeRegistry.
 *
 * Mulai versi ini, Status Bar dan Navigation Bar sistem OTOMATIS sinkron dengan tema aktif 
 * di Android. Untuk iOS, sinkronisasi visual mengikuti perilaku standar platform 
 * (membutuhkan konfigurasi manual preferredStatusBarStyle di sisi project jika diperlukan).
 *
 * LibCatTheme menyediakan [SystemBarsController] via [SystemBarsUtils.LocalSystemBarsController]
 * untuk akses manual jika diperlukan.
 *
 * @IMPORTANT
 * LibCatTheme WAJIB dipasang di root aplikasi (menggantikan MaterialTheme bawaan project),
 * supaya seluruh komponen Material3 di bawahnya (Card, Scaffold, Button, dll) 
 * otomatis mengikuti tema (warna, font, ukuran) dari LibCat.
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

    // 2. System Bars Sync
    val systemBarsController = rememberSystemBarsController()
    SideEffect {
        systemBarsController?.applyTheme(
            statusBarColor = settingColors.background,
            navigationBarColor = settingColors.background, // Match background for edge-to-edge feel
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

    // 3. Resolve Font & Typography
    var fontFamily: FontFamily by remember { mutableStateOf(FontFamily.Default) }
    
    LaunchedEffect(fontStyle) {
        fontFamily = loadFontFamily(fontStyle)
    }

    val baseSize = fontSizeOption
    val typography = remember(fontFamily, baseSize) {
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

    CompositionLocalProvider(
        SystemBarsUtils.LocalSystemBarsController provides systemBarsController
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
