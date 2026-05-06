package mr.cat.setting.utility

import android.util.Log
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontName

class FontRegistry() {
    fun buildFontFaceCSS(option: FontStyleOption): String? {
        val fontFile = option.fontFileName
            ?: return null.also {
                Log.d("FontRegistry", "skip DEFAULT or null font: $option")
            }

        val fontName = option.toFontName()

        val assetPath = "file:///android_asset/font/$fontFile.ttf"

        Log.d("FontRegistry", "build CSS for: $fontName -> $assetPath")

        return """
            @font-face {
                font-family: '$fontName';
                src: url('$assetPath');
            }
        """.trimIndent()
    }
}