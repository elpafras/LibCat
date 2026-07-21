package mr.cat.setting.compose

import androidx.compose.ui.text.font.FontFamily
import mr.cat.setting.component.model.FontStyleOption

/**
 * Expect function untuk memuat FontFamily lintas platform.
 * Implementasi menggunakan mekanisme native masing-masing platform.
 */
expect suspend fun loadFontFamily(option: FontStyleOption): FontFamily
