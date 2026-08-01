package mr.cat.setting.component.model

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import mr.cat.setting.R

actual fun FontStyleOption.toFontFamily(): FontFamily? {
    val resId = when (this) {
        FontStyleOption.DEFAULT -> null
        FontStyleOption.OPEN_SANS -> R.font.opensans
        FontStyleOption.NOTO_SANS -> R.font.notosans
        FontStyleOption.DM_SANS -> R.font.dmsans
        FontStyleOption.ARIMO -> R.font.arimo
        FontStyleOption.ROBOTO_SLAB -> R.font.robotoslab
        FontStyleOption.TITILLIUM_WEB -> R.font.titilliumweb
        FontStyleOption.MERRIWEATHER -> R.font.merriweather
        FontStyleOption.LUSTRIA -> R.font.lustria
        FontStyleOption.TINOS -> R.font.tinos
        FontStyleOption.PLAYFAIR_DISPLAY -> R.font.playfairdisplay
        FontStyleOption.COMIC_NEUE -> R.font.comicneue
        FontStyleOption.CAVEAT -> R.font.caveat
        FontStyleOption.YESEVA_ONE -> R.font.yesevaone
        FontStyleOption.FJALLA_ONE -> R.font.fjallaone
        FontStyleOption.GLORIA_HALLELUJAH -> R.font.gloriahallelujah
        FontStyleOption.OLEO_SCRIPT -> R.font.oleoscript
    }

    return resId?.let {
        FontFamily(Font(it, FontWeight.Normal))
    }
}
