package mr.cat.setting.utility

import mr.cat.setting.component.model.FontStyleOption

class FontRegistry {
    /**
     * Membangun CSS @font-face secara efisien.
     * Menggunakan String template sederhana karena dipanggil saat injeksi font baru.
     */
    fun buildFontFaceCSS(option: FontStyleOption): String? {
        val fontFile = option.fontFileName ?: return null

        val fontName = option.toFontName()
        val assetPath = resolveFontAssetPath(fontFile)

        if (assetPath.isEmpty()) return null

        return """
            @font-face {
                font-family: '$fontName';
                src: url('$assetPath');
                font-display: swap;
            }
        """.trimIndent()
    }
}
