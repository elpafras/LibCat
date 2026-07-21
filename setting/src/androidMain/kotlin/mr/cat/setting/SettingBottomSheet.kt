package mr.cat.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import mr.cat.setting.component.model.defaultThemes
import mr.cat.setting.datastore.createDataStore
import mr.cat.setting.repository.SettingDataStoreRepository
import mr.cat.setting.viewmodel.SettingViewModel

@Composable
fun SettingBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    viewModel: SettingViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            private val context = LocalContext.current.applicationContext
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = SettingDataStoreRepository(createDataStore(context))
                return SettingViewModel(repository) as T
            }
        }
    )
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
