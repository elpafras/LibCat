package mr.cat.libcat.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import mr.cat.chat.engine.ChatEngine
import mr.cat.chat.model.ChatConfig
import mr.cat.libcat.demo.DemoChatLocalDataSource
import mr.cat.libcat.demo.DemoChatRemoteDataSource
import mr.cat.libcat.demo.DemoChatScreen
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.datastore.createDataStore
import mr.cat.setting.repository.SettingDataStoreRepository
import mr.cat.setting.viewmodel.SettingViewModel
import mr.cat.setting.utility.ThemeRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToShowcase: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    onNavigateToHebrewGreek: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showSheet by remember { mutableStateOf(value = false) }
    var showChat by remember { mutableStateOf(value = false) }
    
    val setting = LocalLibCatSettings.current
    val themeColors = remember(setting.theme.id) { ThemeRegistry.resolveThemeColors(setting.theme.id) }
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.sp

    // Chat Engine Initialization
    val scope = rememberCoroutineScope()
    val chatEngine = remember {
        ChatEngine(
            remoteDataSource = DemoChatRemoteDataSource(),
            localDataSource = DemoChatLocalDataSource(),
            config = ChatConfig(dailyLimit = 20, maxRetries = 3),
            coroutineScope = scope
        )
    }

    // Setting ViewModel for DemoChatScreen
    val context = LocalContext.current.applicationContext
    val settingViewModel: SettingViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = SettingDataStoreRepository(createDataStore(context))
                @Suppress("UNCHECKED_CAST")
                return SettingViewModel(repository) as T
            }
        }
    )

    if (showChat) {
        DemoChatScreen(
            chatEngine = chatEngine,
            settingViewModel = settingViewModel,
            onBack = { showChat = false }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("LibCat Home", fontFamily = fontFamily, fontSize = fontSize) },
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
                    color = themeColors.onBackground
                )

                Text(
                    "Aplikasi ini mendemonstrasikan integrasi tema, font, dan ukuran teks secara dinamis.",
                    fontFamily = fontFamily,
                    fontSize = fontSize,
                    color = themeColors.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNavigateToDetail,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary, contentColor = themeColors.onPrimary)
                ) {
                    Text("Buka Detail Artikel", fontFamily = fontFamily)
                }

                Button(
                    onClick = onNavigateToProfile,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary, contentColor = themeColors.onPrimary)
                ) {
                    Text("Lihat Profil", fontFamily = fontFamily)
                }

                Button(
                    onClick = onNavigateToShowcase,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary, contentColor = themeColors.onPrimary)
                ) {
                    Text("Settings Showcase (Semua Komponen)", fontFamily = fontFamily)
                }

                Button(
                    onClick = onNavigateToHebrewGreek,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary, contentColor = themeColors.onPrimary)
                ) {
                    Text("Teks Ibrani & Yunani", fontFamily = fontFamily)
                }

                Button(
                    onClick = { onNavigateToWebView("https://www.google.com") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary, contentColor = themeColors.onPrimary)
                ) {
                    Text("Test WebView", fontFamily = fontFamily)
                }

                Button(
                    onClick = { showChat = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = themeColors.primary,
                        contentColor = themeColors.onPrimary
                    )
                ) {
                    Text("Buka Demo Chat Engine", fontFamily = fontFamily)
                }
            }

            SettingBottomSheet(
                show = showSheet,
                onDismiss = { showSheet = false }
            )
        }
    }
}
