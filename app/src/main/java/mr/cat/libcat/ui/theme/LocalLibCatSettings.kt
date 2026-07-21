package mr.cat.libcat.ui.theme

import androidx.compose.runtime.compositionLocalOf
import mr.cat.setting.SettingState

val LocalLibCatSettings = compositionLocalOf<SettingState> {
    error("No SettingState provided")
}
