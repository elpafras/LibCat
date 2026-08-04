package mr.cat.setting.component.model

import androidx.compose.ui.graphics.Color

data class ThemeColors(
    val background: Color,
    val text: Color,
    val topBar: Color,
    val topBarText: Color,
    val isDark: Boolean
)

data class ThemeOption(
    val id: String,
    val label: String,
    val colors: ThemeColors
)

private fun theme(
    id: String,
    label: String,
    background: Long,
    text: Long,
    topBar: Long,
    topBarText: Long,
    isDark: Boolean = false
) = ThemeOption(
    id = id,
    label = label,
    colors = ThemeColors(
        background = Color(background),
        text = Color(text),
        topBar = Color(topBar),
        topBarText = Color(topBarText),
        isDark = isDark
    )
)

const val DEFAULT_THEME_ID = "default"

val defaultThemes = listOf(

    theme(
        id = "default",
        label = "Default",
        background = 0xFFF0F2F5,
        text = 0xFF1C1B1F,
        topBar = 0xFF3F51B5,
        topBarText = 0xFFFFFFFF,
        isDark = false
    ),

    theme(
        id = "hvs",
        label = "Kertas HVS",
        background = 0xFFFFFFFF,
        text = 0xFF000000,
        topBar = 0xFF000000,
        topBarText = 0xFFFFFFFF,
        isDark = false
    ),

    theme(
        id = "padang_pasir",
        label = "Padang Pasir",
        background = 0xFFF1ECD9,
        text = 0xFF4D250F,
        topBar = 0xFF6F5C45,
        topBarText = 0xFFFCF9DA,
        isDark = false
    ),

    theme(
        id = "langit_cerah",
        label = "Langit Cerah",
        background = 0xFFE7F8FF,
        text = 0xFF2CA6ED,
        topBar = 0xFF007DC6,
        topBarText = 0xFFE7F8FF,
        isDark = false
    ),

    theme(
        id = "pink_pastel",
        label = "Pink Pastel",
        background = 0xFFFFD6E5,
        text = 0xFF773B8F,
        topBar = 0xFF510072,
        topBarText = 0xFFFFD6E5,
        isDark = false
    ),

    theme(
        id = "kuning_pastel",
        label = "Kuning Pastel",
        background = 0xFFFED68B,
        text = 0xFF000000,
        topBar = 0xFFF9A825,
        topBarText = 0xFF000000,
        isDark = false
    ),

    theme(
        id = "hijau_lumut",
        label = "Hijau Lumut",
        background = 0xFF267A6E,
        text = 0xFFFFFFFF,
        topBar = 0xFFFFFFFF,
        topBarText = 0xFF267A6E,
        isDark = true
    ),

    theme(
        id = "batang_kayu",
        label = "Batang Kayu",
        background = 0xFF3C2620,
        text = 0xFFFFF6EA,
        topBar = 0xFFFFF6EA,
        topBarText = 0xFF3C2620,
        isDark = true
    ),

    theme(
        id = "material_gelap",
        label = "Material Gelap",
        background = 0xFF2F2F2F,
        text = 0xFFEAEAEA,
        topBar = 0xFFEAEAEA,
        topBarText = 0xFF2F2F2F,
        isDark = true
    ),

    theme(
        id = "malam_cerah",
        label = "Malam Cerah",
        background = 0xFF10243E,
        text = 0xFFF2F2F2,
        topBar = 0xFFF2F2F2,
        topBarText = 0xFF10243E,
        isDark = true
    ),

    theme(
        id = "malam_berbintang",
        label = "Malam Berbintang",
        background = 0xFF081526,
        text = 0xFFFCE98A,
        topBar = 0xFFFCE98A,
        topBarText = 0xFF081526,
        isDark = true
    ),

    theme(
        id = "papan_tulis",
        label = "Papan Tulis",
        background = 0xFF1A1919,
        text = 0xFFFFFFFF,
        topBar = 0xFFFFFFFF,
        topBarText = 0xFF1A1919,
        isDark = true
    )
)