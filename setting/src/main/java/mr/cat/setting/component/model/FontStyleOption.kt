package mr.cat.setting.component.model

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import mr.cat.setting.R

enum class FontCategory(val label: String) {
    MODERN("Modern"),
    KLASIK("Klasik"),
    DEKORATIF("Dekoratif")
}

enum class FontStyleOption(
    val category: FontCategory,
    val fontFileName: String?
) {
    DEFAULT(FontCategory.MODERN, null),
    OPEN_SANS(FontCategory.MODERN, "opensans"),
    MONTSERRAT(FontCategory.MODERN, "montserrat"),
    NOTO_SANS(FontCategory.MODERN, "notosans"),
    DM_SANS(FontCategory.MODERN, "dmsans"),
    ARIMO(FontCategory.MODERN, "arimo"),
    ROBOTO_SLAB(FontCategory.KLASIK, "robotoslab"),
    TITILLIUM_WEB(FontCategory.KLASIK, "titiliumweb"),
    MERRIWEATHER(FontCategory.KLASIK, "merriweather"),
    LUSTRIA(FontCategory.KLASIK, "lustria"),
    TINOS(FontCategory.KLASIK, "tinos"),
    PLAYFAIR_DISPLAY(FontCategory.KLASIK, "playfairdisplay"),
    COMIC_NEUE(FontCategory.DEKORATIF, "comicneue"),
    CAVEAT(FontCategory.DEKORATIF, "caveat"),
    YESEVA_ONE(FontCategory.DEKORATIF, "yesevaone"),
    FJALLA_ONE(FontCategory.DEKORATIF, "fjallaone"),
    GLORIA_HALLELUJAH(FontCategory.DEKORATIF, "gloriahallelujah"),
    OLEO_SCRIPT(FontCategory.DEKORATIF, "oleoscript")
}

// untuk compose
fun FontStyleOption.toFontFamily(): FontFamily? = when (this) {
    FontStyleOption.DEFAULT -> null
    FontStyleOption.OPEN_SANS -> FontFamily(Font(R.font.opensans, FontWeight.Normal))
    FontStyleOption.MONTSERRAT -> FontFamily(Font(R.font.montserrat, FontWeight.Normal))
    FontStyleOption.NOTO_SANS -> FontFamily(Font(R.font.notosans, FontWeight.Normal))
    FontStyleOption.DM_SANS -> FontFamily(Font(R.font.dmsans, FontWeight.Normal))
    FontStyleOption.ARIMO -> FontFamily(Font(R.font.arimo, FontWeight.Normal))
    FontStyleOption.ROBOTO_SLAB -> FontFamily(Font(R.font.robotoslab, FontWeight.Normal))
    FontStyleOption.TITILLIUM_WEB -> FontFamily(Font(R.font.titilliumweb, FontWeight.Normal))
    FontStyleOption.MERRIWEATHER -> FontFamily(Font(R.font.merriweather, FontWeight.Normal))
    FontStyleOption.LUSTRIA -> FontFamily(Font(R.font.lustira, FontWeight.Normal))
    FontStyleOption.TINOS -> FontFamily(Font(R.font.tinos, FontWeight.Normal))
    FontStyleOption.PLAYFAIR_DISPLAY -> FontFamily(Font(R.font.playfairdisplay, FontWeight.Normal))
    FontStyleOption.COMIC_NEUE -> FontFamily(Font(R.font.comicneue, FontWeight.Normal))
    FontStyleOption.CAVEAT -> FontFamily(Font(R.font.caveat, FontWeight.Normal))
    FontStyleOption.YESEVA_ONE -> FontFamily(Font(R.font.yesevaone, FontWeight.Normal))
    FontStyleOption.FJALLA_ONE -> FontFamily(Font(R.font.fjallaone, FontWeight.Normal))
    FontStyleOption.GLORIA_HALLELUJAH -> FontFamily(Font(R.font.gloriahallelujah, FontWeight.Normal))
    FontStyleOption.OLEO_SCRIPT -> FontFamily(Font(R.font.oleoscript, FontWeight.Normal))
}

// untuk nama font di CSS/WebView/AndroidView
fun FontStyleOption.toFontName(): String = when (this) {
    FontStyleOption.DEFAULT -> "sans-serif"
    FontStyleOption.OPEN_SANS -> "Open Sans"
    FontStyleOption.MONTSERRAT -> "Montserrat"
    FontStyleOption.NOTO_SANS -> "Noto Sans"
    FontStyleOption.DM_SANS -> "DM Sans"
    FontStyleOption.ARIMO -> "Arimo"
    FontStyleOption.ROBOTO_SLAB -> "Roboto Slab"
    FontStyleOption.TITILLIUM_WEB -> "Titillium Web"
    FontStyleOption.MERRIWEATHER -> "Merriweather"
    FontStyleOption.LUSTRIA -> "Lustria"
    FontStyleOption.TINOS -> "Tinos"
    FontStyleOption.PLAYFAIR_DISPLAY -> "Playfair Display"
    FontStyleOption.COMIC_NEUE -> "Comic Neue"
    FontStyleOption.CAVEAT -> "Caveat"
    FontStyleOption.YESEVA_ONE -> "Yeseva One"
    FontStyleOption.FJALLA_ONE -> "Fjalla One"
    FontStyleOption.GLORIA_HALLELUJAH -> "Gloria Hallelujah"
    FontStyleOption.OLEO_SCRIPT -> "Oleo Script"
}

fun FontStyleOption.toLabel(): String = when (this) {
    FontStyleOption.DEFAULT -> "Default"
    else -> toFontName()
}