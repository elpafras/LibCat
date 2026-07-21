package mr.cat.setting.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mr.cat.setting.component.model.FontSizeOption
import mr.cat.setting.component.model.FontStyleOption

/**
 * Implementasi [SettingRepository] menggunakan Jetpack DataStore Multiplatform.
 */
class SettingDataStoreRepository(
    private val dataStore: DataStore<Preferences>
) : SettingRepository {

    companion object {
        private val KEY_FONT_SIZE = stringPreferencesKey("font_size")
        private val KEY_FONT_STYLE = stringPreferencesKey("font_style")
        private val KEY_THEME_ID = stringPreferencesKey("theme_id")
    }

    override val fontSizeFlow: Flow<FontSizeOption> = dataStore.data.map { prefs ->
        val value = prefs[KEY_FONT_SIZE] ?: FontSizeOption.STANDARD.name
        FontSizeOption.valueOf(value)
    }

    override suspend fun saveFontSize(option: FontSizeOption) {
        dataStore.edit { prefs ->
            prefs[KEY_FONT_SIZE] = option.name
        }
    }

    override val fontStyleFlow: Flow<FontStyleOption> = dataStore.data.map { prefs ->
        val value = prefs[KEY_FONT_STYLE] ?: FontStyleOption.DEFAULT.name
        FontStyleOption.valueOf(value)
    }

    override suspend fun saveFontStyle(option: FontStyleOption) {
        dataStore.edit { prefs ->
            prefs[KEY_FONT_STYLE] = option.name
        }
    }

    override val themeIdFlow: Flow<String> = dataStore.data.map { prefs ->
        prefs[KEY_THEME_ID] ?: "hvs"
    }

    override suspend fun saveThemeId(id: String) {
        dataStore.edit { prefs ->
            prefs[KEY_THEME_ID] = id
        }
    }
}
