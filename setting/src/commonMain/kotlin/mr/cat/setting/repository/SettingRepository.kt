package mr.cat.setting.repository

import kotlinx.coroutines.flow.Flow
import mr.cat.setting.component.model.FontStyleOption

/**
 * Kontrak untuk operasi data pengaturan (Theme, Font, Size).
 */
interface SettingRepository {
    val fontSizeFlow: Flow<Float>
    val fontStyleFlow: Flow<FontStyleOption>
    val themeIdFlow: Flow<String>
    
    suspend fun saveFontSize(size: Float)
    suspend fun saveFontStyle(option: FontStyleOption)
    suspend fun saveThemeId(id: String)
}
