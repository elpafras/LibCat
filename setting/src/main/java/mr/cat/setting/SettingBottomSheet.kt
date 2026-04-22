package mr.cat.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import mr.cat.setting.component.model.defaultThemes
import mr.cat.setting.viewmodel.SettingViewModel

@Composable
fun SettingBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    viewModel: SettingViewModel = viewModel()
) {
    CollectionBottomSheet(
        show = show,
        selected = viewModel.fontSize.collectAsState().value,
        onSelected = { viewModel.setFontSize(it) },
        selectedStyle = viewModel.fontStyle.collectAsState().value,
        onStyleSelected = { viewModel.setFontStyle(it) },
        selectedThemeId = viewModel.themeId.collectAsState().value,
        onThemeSelected = { viewModel.setTheme(it.id) },
        themes = defaultThemes,
        onDismiss = onDismiss
    )
}