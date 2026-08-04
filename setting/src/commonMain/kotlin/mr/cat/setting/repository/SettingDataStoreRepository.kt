package mr.cat.setting.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mr.cat.setting.component.model.DEFAULT_FONT_SIZE
import mr.cat.setting.component.model.DEFAULT_THEME_ID
import mr.cat.setting.component.model.FontStyleOption

/**
 * Implementasi [SettingRepository] menggunakan Jetpack DataStore Multiplatform.
 */
class SettingDataStoreRepository(
    private val dataStore: DataStore<Preferences>,
) : SettingRepository {

    companion object {
        private val KEY_FONT_SIZE = floatPreferencesKey("font_size")
        private val KEY_FONT_STYLE = stringPreferencesKey("font_style")
        private val KEY_THEME_ID = stringPreferencesKey("theme_id")
    }

    override val fontSizeFlow: Flow<Float> = dataStore.data.map { prefs ->
        // Safely retrieve the value to avoid ClassCastException if the type in DataStore is wrong
        val value = prefs.asMap().entries.find { it.key.name == KEY_FONT_SIZE.name }?.value
        when (value) {
            is Float -> value
            is String -> value.toFloatOrNull() ?: DEFAULT_FONT_SIZE
            is Number -> value.toFloat()
            else -> DEFAULT_FONT_SIZE
        }
    }

    override suspend fun saveFontSize(size: Float) {
        dataStore.edit { prefs ->
            prefs[KEY_FONT_SIZE] = size
        }
    }

    override val fontStyleFlow: Flow<FontStyleOption> = dataStore.data.map { prefs ->
        val value = prefs.asMap().entries.find { it.key.name == KEY_FONT_STYLE.name }?.value
        val stringValue = when (value) {
            is String -> value
            else -> FontStyleOption.DEFAULT.name
        }
        try {
            FontStyleOption.valueOf(stringValue)
        } catch (_: IllegalArgumentException) {
            FontStyleOption.DEFAULT
        }
    }

    override suspend fun saveFontStyle(option: FontStyleOption) {
        dataStore.edit { prefs ->
            prefs[KEY_FONT_STYLE] = option.name
        }
    }

    override val themeIdFlow: Flow<String> = dataStore.data.map { prefs ->
        val value = prefs.asMap().entries.find { it.key.name == KEY_THEME_ID.name }?.value
        when (value) {
            is String -> value
            else -> DEFAULT_THEME_ID
        }
    }

    override suspend fun saveThemeId(id: String) {
        dataStore.edit { prefs ->
            prefs[KEY_THEME_ID] = id
        }
    }
}
