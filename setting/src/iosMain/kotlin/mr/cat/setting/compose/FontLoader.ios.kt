package mr.cat.setting.compose

import androidx.compose.ui.text.font.FontFamily
import mr.cat.setting.component.model.FontStyleOption

/**
 * Implementasi iOS: Untuk saat ini menggunakan FontFamily.Default.
 * Pada implementasi produksi, ini akan menggunakan mekanisme Compose Resources
 * (org.jetbrains.compose.resources.Font) setelah resource di-generate.
 */
actual suspend fun loadFontFamily(option: FontStyleOption): FontFamily {
    // TODO: Implementasi dengan Compose Resources atau custom UIFont bridging
    return FontFamily.Default
}
