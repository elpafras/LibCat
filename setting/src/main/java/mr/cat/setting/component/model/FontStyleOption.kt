package mr.cat.setting.component.model

import android.content.Context
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import mr.cat.setting.R

enum class FontStyleOption {
    DEFAULT, ARIMO, MERRIWEATHER, TITILLIUM_WEB
}

// untuk compose
fun FontStyleOption.toFontFamily(): FontFamily? = when (this) {
    FontStyleOption.DEFAULT -> null  // null = pakai font sistem Android
    FontStyleOption.ARIMO -> FontFamily(Font(R.font.arimo, FontWeight.Normal))
    FontStyleOption.MERRIWEATHER -> FontFamily(Font(R.font.merriweather, FontWeight.Normal))
    FontStyleOption.TITILLIUM_WEB -> FontFamily(Font(R.font.titilliumweb, FontWeight.Normal))
}

// untuk androidview dan webview
fun FontStyleOption.toBase64Font(context: Context): String? {
    val fontFile = when (this) {
        FontStyleOption.ARIMO -> "font/arimo.ttf"
        FontStyleOption.MERRIWEATHER -> "font/merriweather.ttf"
        FontStyleOption.TITILLIUM_WEB -> "font/titillium_web.ttf"
        FontStyleOption.DEFAULT -> return null
    }

    return try {
        val bytes = context.assets.open(fontFile).readBytes()
        android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
    } catch (e: Exception) {
        null
    }
}

fun FontStyleOption.toLabel(): String = when (this) {
    FontStyleOption.DEFAULT -> "Default"
    FontStyleOption.ARIMO -> "Arimo"
    FontStyleOption.MERRIWEATHER -> "Merriweather"
    FontStyleOption.TITILLIUM_WEB -> "Titillium Web"
}