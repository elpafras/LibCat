package mr.cat.setting.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mr.cat.setting.component.model.FontSizeOption
import mr.cat.setting.component.model.FontStyleOption

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mr_cat_setting")

class SettingDataStore(private val context: Context) {

    companion object {
        private val KEY_FONT_SIZE = stringPreferencesKey("font_size")
        private val KEY_FONT_STYLE = stringPreferencesKey("font_style")
        private val KEY_THEME_ID = stringPreferencesKey("theme_id")
    }

    // Font Size
    val fontSizeFlow: Flow<FontSizeOption> = context.dataStore.data.map { prefs ->
        val value = prefs[KEY_FONT_SIZE] ?: FontSizeOption.STANDARD.name
        FontSizeOption.valueOf(value)
    }

    suspend fun saveFontSize(option: FontSizeOption) {
        context.dataStore.edit { prefs ->
            prefs[KEY_FONT_SIZE] = option.name
        }
    }

    // Font Style
    val fontStyleFlow: Flow<FontStyleOption> = context.dataStore.data.map { prefs ->
        val value = prefs[KEY_FONT_STYLE] ?: FontStyleOption.DEFAULT.name
        FontStyleOption.valueOf(value)
    }

    suspend fun saveFontStyle(option: FontStyleOption) {
        context.dataStore.edit { prefs ->
            prefs[KEY_FONT_STYLE] = option.name
        }
    }

    // Theme
    val themeIdFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_THEME_ID] ?: "hvs" // default Kertas HVS
    }

    suspend fun saveThemeId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME_ID] = id
        }
    }
}