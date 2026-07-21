package mr.cat.libcat.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.component.model.toTextUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(onBack: () -> Unit) {
    val setting = LocalLibCatSettings.current
    val themeColors = setting.theme.colors
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.toTextUnit()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Article Detail", fontFamily = fontFamily, fontSize = fontSize) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = themeColors.topBarText)
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
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "Ini adalah halaman detail artikel.",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.text
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.text
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val setting = LocalLibCatSettings.current
    val themeColors = setting.theme.colors
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.toTextUnit()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontFamily = fontFamily, fontSize = fontSize) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = themeColors.topBarText)
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
                color = themeColors.text
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "user@example.com",
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = themeColors.text.copy(alpha = 0.6f)
            )
        }
    }
}
