package mr.cat.libcat.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.utility.ThemeRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(onBack: () -> Unit) {
    var showSheet by remember { mutableStateOf(value = false) }
    val setting = LocalLibCatSettings.current
    val themeColors = remember(setting.theme.id) { ThemeRegistry.resolveThemeColors(setting.theme.id) }
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.sp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Detail", fontFamily = fontFamily, fontSize = fontSize) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = themeColors.onPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = themeColors.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.primary,
                    titleContentColor = themeColors.onPrimary,
                ),
            )
        },
        containerColor = themeColors.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "Ini adalah halaman detail artikel.",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.onBackground
            )
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    var showSheet by remember { mutableStateOf(value = false) }
    val setting = LocalLibCatSettings.current
    val themeColors = remember(setting.theme.id) { ThemeRegistry.resolveThemeColors(setting.theme.id) }
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.sp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontFamily = fontFamily, fontSize = fontSize) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = themeColors.onPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = themeColors.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.primary,
                    titleContentColor = themeColors.onPrimary,
                ),
            )
        },
        containerColor = themeColors.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "User Profile",
                fontFamily = fontFamily,
                fontSize = (fontSize.value + 6).sp,
                color = themeColors.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "user@example.com",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.onBackground.copy(alpha = 0.6f)
            )
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }
}
