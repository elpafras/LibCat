package mr.cat.setting.compose

import androidx.compose.ui.text.font.FontFamily
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontFamily

/**
 * Implementasi Android: Menggunakan toFontFamily() yang sudah ada
 * (berbasis Resource ID) sebagai fallback yang stabil.
 */
actual suspend fun loadFontFamily(option: FontStyleOption): FontFamily {
    return option.toFontFamily() ?: FontFamily.Default
}
