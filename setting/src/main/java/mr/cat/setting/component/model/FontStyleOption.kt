package mr.cat.setting.component.model

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import mr.cat.setting.R

enum class FontStyleOption {
    DEFAULT, ARIMO, MERRIWEATHER, TITILLIUM_WEB
}

fun FontStyleOption.toFontFamily(): FontFamily? = when (this) {
    FontStyleOption.DEFAULT -> null  // null = pakai font sistem Android
    FontStyleOption.ARIMO -> FontFamily(Font(R.font.arimo, FontWeight.Normal))
    FontStyleOption.MERRIWEATHER -> FontFamily(Font(R.font.merriweather, FontWeight.Normal))
    FontStyleOption.TITILLIUM_WEB -> FontFamily(Font(R.font.titilliumweb, FontWeight.Normal))
}

fun FontStyleOption.toLabel(): String = when (this) {
    FontStyleOption.DEFAULT -> "Default"
    FontStyleOption.ARIMO -> "Arimo"
    FontStyleOption.MERRIWEATHER -> "Merriweather"
    FontStyleOption.TITILLIUM_WEB -> "Titillium Web"
}