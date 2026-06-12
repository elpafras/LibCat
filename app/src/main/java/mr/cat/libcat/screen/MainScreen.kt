package mr.cat.libcat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.rememberSettingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToShowcase: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSheet by remember { mutableStateOf(false) }
    val setting = rememberSettingState()
    val themeColors = setting.theme.colors
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.toTextUnit()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LibCat Home", fontFamily = fontFamily, fontSize = fontSize) },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = themeColors.topBarText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.topBar,
                    titleContentColor = themeColors.topBarText
                )
            )
        },
        containerColor = themeColors.background
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Selamat datang di LibCat!",
                fontFamily = fontFamily,
                fontSize = (fontSize.value + 4).sp,
                color = themeColors.text
            )

            Text(
                "Aplikasi ini mendemonstrasikan integrasi tema, font, dan ukuran teks secara dinamis.",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.text
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToDetail,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = themeColors.topBar, contentColor = themeColors.topBarText)
            ) {
                Text("Buka Detail Artikel", fontFamily = fontFamily)
            }

            Button(
                onClick = onNavigateToProfile,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = themeColors.topBar, contentColor = themeColors.topBarText)
            ) {
                Text("Lihat Profil", fontFamily = fontFamily)
            }

            Button(
                onClick = onNavigateToShowcase,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = themeColors.topBar, contentColor = themeColors.topBarText)
            ) {
                Text("Settings Showcase (Semua Komponen)", fontFamily = fontFamily)
            }

            Button(
                onClick = { onNavigateToWebView("https://www.google.com") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = themeColors.topBar, contentColor = themeColors.topBarText)
            ) {
                Text("Test WebView", fontFamily = fontFamily)
            }
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }
}
