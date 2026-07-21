package mr.cat.setting.component.model

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class FontSizeOption {
    SMALL, STANDARD, LARGE
}

data class FontSizeConfig(
    val option: FontSizeOption,
    val label: String,
    val fontSize: TextUnit
)

fun FontSizeOption.toTextUnit(): TextUnit = when (this) {
    FontSizeOption.SMALL -> 12.sp
    FontSizeOption.STANDARD -> 14.sp
    FontSizeOption.LARGE -> 18.sp
}