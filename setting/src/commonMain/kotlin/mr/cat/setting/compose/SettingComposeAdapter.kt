package mr.cat.setting.compose

import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.utility.SettingThemeColors
import mr.cat.setting.utility.ThemeRegistry
import mr.cat.setting.viewmodel.SettingViewModel

/**
 * Data class yang membungkus semua properti gaya teks yang dihasilkan oleh LibCat.
 */
data class SettingTextStyle(
    val fontSize: TextUnit,
    val fontFamily: FontFamily,
    val themeColors: SettingThemeColors
)

/**
 * Cache internal untuk menyimpan FontFamily yang sudah dimuat.
 */
private val fontCache = mutableMapOf<FontStyleOption, FontFamily>()

/**
 * Compose-native adapter untuk LibCat (Shared antara Android & iOS).
 */
@Composable
fun rememberSettingTextStyle(
    viewModel: SettingViewModel
): SettingTextStyle {
    val fontSizeOption by viewModel.fontSize.collectAsState()
    val fontStyleOption by viewModel.fontStyle.collectAsState()
    val themeId by viewModel.themeId.collectAsState()

    // 1. Resolve Font Size
    val fontSize = remember(fontSizeOption) {
        fontSizeOption.toTextUnit()
    }

    // 2. Resolve Font Family with Caching and Async Loading
    var fontFamily by remember { mutableStateOf(fontCache[fontStyleOption] ?: FontFamily.Default) }
    
    LaunchedEffect(fontStyleOption) {
        if (!fontCache.containsKey(fontStyleOption)) {
            val loadedFont = loadFontFamily(fontStyleOption)
            fontCache[fontStyleOption] = loadedFont
            fontFamily = loadedFont
        } else {
            fontFamily = fontCache[fontStyleOption]!!
        }
    }

    // 3. Resolve Theme Colors
    val themeColors = remember(themeId) {
        ThemeRegistry.resolveThemeColors(themeId)
    }

    return remember(fontSize, fontFamily, themeColors) {
        SettingTextStyle(
            fontSize = fontSize,
            fontFamily = fontFamily,
            themeColors = themeColors
        )
    }
}
