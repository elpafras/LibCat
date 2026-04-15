package mr.cat.setting.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mr.cat.setting.component.model.FontSizeOption
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.datastore.SettingDataStore

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = SettingDataStore(application)

    val fontSize = dataStore.fontSizeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FontSizeOption.STANDARD
    )

    val fontStyle = dataStore.fontStyleFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FontStyleOption.DEFAULT
    )

    val themeId = dataStore.themeIdFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "hvs"
    )

    fun setFontSize(option: FontSizeOption) = viewModelScope.launch {
        dataStore.saveFontSize(option)
    }

    fun setFontStyle(option: FontStyleOption) = viewModelScope.launch {
        dataStore.saveFontStyle(option)
    }

    fun setTheme(id: String) = viewModelScope.launch {
        dataStore.saveThemeId(id)
    }
}