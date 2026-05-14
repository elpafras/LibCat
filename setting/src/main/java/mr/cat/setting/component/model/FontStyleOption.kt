package mr.cat.setting.component.model

import androidx.annotation.FontRes
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
    val fontFileName: String?,   // untuk asset / base64
    val displayName: String,     // untuk CSS & UI
    @param:FontRes val fontResId: Int? // untuk Compose
) {

    DEFAULT(
        category = FontCategory.MODERN,
        fontFileName = null,
        displayName = "sans-serif",
        fontResId = null
    ),

    OPEN_SANS(FontCategory.MODERN, "opensans", "Open Sans", R.font.opensans),
    MONTSERRAT(FontCategory.MODERN, "montserrat", "Montserrat", R.font.montserrat),
    NOTO_SANS(FontCategory.MODERN, "notosans", "Noto Sans", R.font.notosans),
    DM_SANS(FontCategory.MODERN, "dmsans", "DM Sans", R.font.dmsans),
    ARIMO(FontCategory.MODERN, "arimo", "Arimo", R.font.arimo),

    ROBOTO_SLAB(FontCategory.KLASIK, "robotoslab", "Roboto Slab", R.font.robotoslab),
    TITILLIUM_WEB(FontCategory.KLASIK, "titilliumweb", "Titillium Web", R.font.titilliumweb),
    MERRIWEATHER(FontCategory.KLASIK, "merriweather", "Merriweather", R.font.merriweather),
    LUSTRIA(FontCategory.KLASIK, "lustria", "Lustria", R.font.lustria),
    TINOS(FontCategory.KLASIK, "tinos", "Tinos", R.font.tinos),
    PLAYFAIR_DISPLAY(FontCategory.KLASIK, "playfairdisplay", "Playfair Display", R.font.playfairdisplay),

    COMIC_NEUE(FontCategory.DEKORATIF, "comicneue", "Comic Neue", R.font.comicneue),
    CAVEAT(FontCategory.DEKORATIF, "caveat", "Caveat", R.font.caveat),
    YESEVA_ONE(FontCategory.DEKORATIF, "yesevaone", "Yeseva One", R.font.yesevaone),
    FJALLA_ONE(FontCategory.DEKORATIF, "fjallaone", "Fjalla One", R.font.fjallaone),
    GLORIA_HALLELUJAH(FontCategory.DEKORATIF, "gloriahallelujah", "Gloria Hallelujah", R.font.gloriahallelujah),
    OLEO_SCRIPT(FontCategory.DEKORATIF, "oleoscript", "Oleo Script", R.font.oleoscript);

    // =========================
    // Derived helpers
    // =========================

    fun toFontFamily(): FontFamily? {
        return fontResId?.let {
            FontFamily(Font(it, FontWeight.Normal))
        }
    }

    fun toFontName(): String = displayName

    fun toLabel(): String {
        return if (this == DEFAULT) "Default" else displayName
    }
}