package mr.cat.setting.component.model

import androidx.compose.ui.graphics.Color

data class ThemeOption(
    val id: String,
    val label: String,
    val backgroundColor: Color,
    val fontColor: Color,
    val topBarColor: Color
)

val defaultThemes = listOf(
    ThemeOption(
        id = "hvs",
        label = "Kertas HVS",
        backgroundColor = Color(0xFFFFFFFF),
        fontColor = Color(0xFF000000),
        topBarColor = Color(0xFFFFFFFF)
    ),
    ThemeOption(
        id = "padang_pasir",
        label = "Padang Pasir",
        backgroundColor = Color(0xFFF0ECD9),
        fontColor = Color(0xFF512507),
        topBarColor = Color(0xFFF0ECD9)
    ),
    ThemeOption(
        id = "langit_cerah",
        label = "Langit Cerah",
        backgroundColor = Color(0xFFE7F8FF),
        fontColor = Color(0xFF2CA6ED),
        topBarColor = Color(0xFFE7F8FF)
    ),
    ThemeOption(
        id = "pink_pastel",
        label = "Pink Pastel",
        backgroundColor = Color(0xFFFFD6E5),
        fontColor = Color(0xFF773B8F),
        topBarColor = Color(0xFFFFD6E5)
    ),
    ThemeOption(
        id = "kuning_pastel",
        label = "Kuning Pastel",
        backgroundColor = Color(0xFFFED68B),
        fontColor = Color(0xFF000000),
        topBarColor = Color(0xFFFED68B)
    ),
    ThemeOption(
        id = "hijau_lumut",
        label = "Hijau Lumut",
        backgroundColor = Color(0xFF267A6E),
        fontColor = Color(0xFFFFFFFF),
        topBarColor = Color(0xFF267A6E)
    ),
    ThemeOption(
        id = "batang_kayu",
        label = "Batang Kayu",
        backgroundColor = Color(0xFF3C2620),
        fontColor = Color(0xFFFFF6EA),
        topBarColor = Color(0xFF3C2620)
    ),
    ThemeOption(
        id = "material_gelap",
        label = "Material Gelap",
        backgroundColor = Color(0xFF2F2F2F),
        fontColor = Color(0xFFEAEAEA),
        topBarColor = Color(0xFF2F2F2F)
    ),
    ThemeOption(
        id = "malam_cerah",
        label = "Malam Cerah",
        backgroundColor = Color(0xFF10243E),
        fontColor = Color(0xFFF2F2F2),
        topBarColor = Color(0xFF10243E)
    ),
    ThemeOption(
        id = "malam_berbintang",
        label = "Malam Berbintang",
        backgroundColor = Color(0xFF081526),
        fontColor = Color(0xFFFCE98A),
        topBarColor = Color(0xFF081526)
    ),
    ThemeOption(
        id = "papan_tulis",
        label = "Papan Tulis",
        backgroundColor = Color(0xFF1A1919),
        fontColor = Color(0xFFFFFFFF),
        topBarColor = Color(0xFF1A1919)
    )
)