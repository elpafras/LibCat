package mr.cat.libcat.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.utility.ThemeInjector
import mr.cat.setting.utility.ThemeRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    onBack: () -> Unit,
) {
    val setting = LocalLibCatSettings.current
    val themeColors = remember(setting.theme.id) { ThemeRegistry.resolveThemeColors(setting.theme.id) }
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.sp
    
    var showSheet by remember { mutableStateOf(value = false) }
    val themeInjector = remember { ThemeInjector() }

    var webViewInstance: WebView? by remember { mutableStateOf(null) }
    var canGoBack by remember { mutableStateOf(value = false) }
    var canGoForward by remember { mutableStateOf(value = false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Browser", 
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = themeColors.onPrimary)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { webViewInstance?.goBack() },
                        enabled = canGoBack,
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = if (canGoBack) themeColors.onPrimary else themeColors.onPrimary.copy(alpha = 0.3f)
                        )
                    }
                    IconButton(
                        onClick = { webViewInstance?.goForward() },
                        enabled = canGoForward
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForwardIos, 
                            contentDescription = "Forward",
                            tint = if (canGoForward) themeColors.onPrimary else themeColors.onPrimary.copy(alpha = 0.3f)
                        )
                    }
                    IconButton(onClick = { webViewInstance?.reload() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reload", tint = themeColors.onPrimary)
                    }
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = themeColors.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.primary,
                    titleContentColor = themeColors.onPrimary
                )
            )
        },
        containerColor = themeColors.background
    ) { innerPadding ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            canGoBack = view?.canGoBack() ?: false
                            canGoForward = view?.canGoForward() ?: false
                            
                            // Apply theme when page is ready using ThemeInjector from :setting
                            view?.let { 
                                themeInjector.applyTheme(it, setting.theme)
                            }
                        }
                    }
                    @Suppress("SetJavaScriptEnabled") // Required for theme injection functionality
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                    webViewInstance = this
                }
            },
            update = { view ->
                // Sync theme when setting changes
                themeInjector.applyTheme(view, setting.theme)
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }
}
