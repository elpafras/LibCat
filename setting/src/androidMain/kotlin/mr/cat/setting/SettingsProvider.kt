package mr.cat.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import mr.cat.setting.component.model.DEFAULT_FONT_SIZE
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.ThemeOption
import mr.cat.setting.component.model.defaultThemes
import mr.cat.setting.datastore.createDataStore
import mr.cat.setting.repository.SettingDataStoreRepository
import mr.cat.setting.viewmodel.SettingViewModel

data class SettingState(
    val fontSize: Float,
    val fontStyle: FontStyleOption,
    val theme: ThemeOption
)

/**
 * Menyediakan state pengaturan yang tersinkronisasi.
 * Public API ini tetap kompatibel dengan versi Android sebelumnya.
 */
@Composable
fun rememberSettingState(
    viewModel: SettingViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            private val context = LocalContext.current.applicationContext
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Inisialisasi repository dengan DataStore spesifik Android
                val repository = SettingDataStoreRepository(createDataStore(context))
                return SettingViewModel(repository) as T
            }
        }
    )
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
