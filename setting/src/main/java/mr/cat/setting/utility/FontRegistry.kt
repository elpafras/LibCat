package mr.cat.setting.utility

import android.content.Context
import android.util.Base64
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontName

class FontRegistry(private val context: Context) {

    private val cache = mutableMapOf<FontStyleOption, String?>()

    fun getBase64(option: FontStyleOption): String? {
        if (cache.containsKey(option)) return cache[option]

        val fontFile = when (option) {
            FontStyleOption.ARIMO -> "font/arimo.ttf"
            FontStyleOption.MERRIWEATHER -> "font/merriweather.ttf"
            FontStyleOption.TITILLIUM_WEB -> "font/titillium_web.ttf"
            FontStyleOption.DEFAULT -> return null.also { cache[option] = null }
        }

        return try {
            val bytes = context.assets.open(fontFile).readBytes()
            Base64.encodeToString(bytes, Base64.NO_WRAP).also { cache[option] = it }
        } catch (e: Exception) {
            null.also { cache[option] = null }
        }
    }

    fun preloadAll() {
        FontStyleOption.entries.forEach { getBase64(it) }
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