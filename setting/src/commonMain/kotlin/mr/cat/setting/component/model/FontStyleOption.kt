package mr.cat.setting.component.model

import androidx.compose.ui.text.font.FontFamily

enum class FontCategory(val label: String) {
    MODERN("Modern"),
    KLASIK("Klasik"),
    DEKORATIF("Dekoratif")
}

enum class FontStyleOption(
    val category: FontCategory,
    val fontFileName: String?,   // untuk asset / base64
    val displayName: String      // untuk CSS & UI
) {

    DEFAULT(
        category = FontCategory.MODERN,
        fontFileName = null,
        displayName = "sans-serif"
    ),

    OPEN_SANS(FontCategory.MODERN, "opensans", "Open Sans"),
    MONTSERRAT(FontCategory.MODERN, "montserrat", "Montserrat"),
    NOTO_SANS(FontCategory.MODERN, "notosans", "Noto Sans"),
    DM_SANS(FontCategory.MODERN, "dmsans", "DM Sans"),
    ARIMO(FontCategory.MODERN, "arimo", "Arimo"),

    ROBOTO_SLAB(FontCategory.KLASIK, "robotoslab", "Roboto Slab"),
    TITILLIUM_WEB(FontCategory.KLASIK, "titilliumweb", "Titillium Web"),
    MERRIWEATHER(FontCategory.KLASIK, "merriweather", "Merriweather"),
    LUSTRIA(FontCategory.KLASIK, "lustria", "Lustria"),
    TINOS(FontCategory.KLASIK, "tinos", "Tinos"),
    PLAYFAIR_DISPLAY(FontCategory.KLASIK, "playfairdisplay", "Playfair Display"),

    COMIC_NEUE(FontCategory.DEKORATIF, "comicneue", "Comic Neue"),
    CAVEAT(FontCategory.DEKORATIF, "caveat", "Caveat"),
    YESEVA_ONE(FontCategory.DEKORATIF, "yesevaone", "Yeseva One"),
    FJALLA_ONE(FontCategory.DEKORATIF, "fjallaone", "Fjalla One"),
    GLORIA_HALLELUJAH(FontCategory.DEKORATIF, "gloriahallelujah", "Gloria Hallelujah"),
    OLEO_SCRIPT(FontCategory.DEKORATIF, "oleoscript", "Oleo Script");

    // =========================
    // Derived helpers
    // =========================

    fun toFontName(): String = displayName

    fun toLabel(): String {
        return if (this == DEFAULT) "Default" else displayName
    }
}

/**
 * Platform-specific helper to get FontFamily.
 */
expect fun FontStyleOption.toFontFamily(): FontFamily?
