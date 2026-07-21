package mr.cat.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mr.cat.setting.component.model.FontSizeOption
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.repository.SettingRepository

/**
 * ViewModel Multiplatform untuk mengelola state pengaturan.
 * Bekerja dengan [SettingRepository] untuk persistensi data.
 */
class SettingViewModel(
    private val repository: SettingRepository
) : ViewModel() {

    val fontSize = repository.fontSizeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FontSizeOption.STANDARD
    )

    val fontStyle = repository.fontStyleFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FontStyleOption.DEFAULT
    )

    val themeId = repository.themeIdFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "hvs"
    )

    fun setFontSize(option: FontSizeOption) = viewModelScope.launch {
        repository.saveFontSize(option)
    }

    fun setFontStyle(option: FontStyleOption) = viewModelScope.launch {
        repository.saveFontStyle(option)
    }

    fun setTheme(id: String) = viewModelScope.launch {
        repository.saveThemeId(id)
    }
}
