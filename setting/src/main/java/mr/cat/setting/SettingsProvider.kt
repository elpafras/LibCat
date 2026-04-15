package mr.cat.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import mr.cat.setting.component.model.FontSizeOption
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.ThemeOption
import mr.cat.setting.component.model.defaultThemes
import mr.cat.setting.viewmodel.SettingViewModel

data class SettingState(
    val fontSize: FontSizeOption,
    val fontStyle: FontStyleOption,
    val theme: ThemeOption
)

@Composable
fun rememberSettingState(
    viewModel: SettingViewModel = viewModel()
): SettingState {
    val fontSize by viewModel.fontSize.collectAsState()
    val fontStyle by viewModel.fontStyle.collectAsState()
    val themeId by viewModel.themeId.collectAsState()
    val theme = defaultThemes.find { it.id == themeId } ?: defaultThemes.first()

    return SettingState(
        fontSize = fontSize,
        fontStyle = fontStyle,
        theme = theme
    )
}