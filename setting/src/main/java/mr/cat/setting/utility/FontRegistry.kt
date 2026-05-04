package mr.cat.setting.utility

import android.content.Context
import android.util.Base64
import android.util.Log
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontName

class FontRegistry(private val context: Context) {

    private val cache = mutableMapOf<FontStyleOption, String?>()

    fun getBase64(option: FontStyleOption): String? {
        if (cache.containsKey(option)) {
            Log.d("FontRegistry", "cache hit: $option")
            return cache[option]
        }

        val fontFile = option.fontFileName
            ?: return null.also { cache[option] = null }

        return try {
            val bytes = context.assets.open("font/$fontFile.ttf").readBytes()
            Log.d("FontRegistry", "encode success: $option, size: ${bytes.size}")
            Base64.encodeToString(bytes, Base64.NO_WRAP).also { cache[option] = it }
        } catch (e: Exception) {
            Log.e("FontRegistry", "encode failed: $option, error: ${e.message}")
            null.also { cache[option] = null }
        }
    }

    fun preloadAll() {
        Log.d("FontRegistry", "preloadAll start")
        FontStyleOption.entries.forEach { getBase64(it) }
        Log.d("FontRegistry", "preloadAll done")
    }

    fun buildFontFaceCSS(): String {
        return FontStyleOption.entries
            .filter { it != FontStyleOption.DEFAULT }
            .mapNotNull { option ->
                val base64 = getBase64(option) ?: return@mapNotNull null
                val name = option.toFontName()
                """
                @font-face {
                    font-family: '$name';
                    src: url('data:font/ttf;base64,$base64');
                }
                """.trimIndent()
            }
            .joinToString("\n")
    }
}