package mr.cat.libcat.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.utility.ThemeInjector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    onBack: () -> Unit
) {
    val setting = LocalLibCatSettings.current
    val themeColors = setting.theme.colors
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.toTextUnit()
    
    val themeInjector = remember { ThemeInjector() }

    var webViewInstance: WebView? by remember { mutableStateOf(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Browser", 
                        fontFamily = fontFamily,
                        fontSize = fontSize
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = themeColors.topBarText)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { webViewInstance?.goBack() },
                        enabled = canGoBack
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew, 
                            contentDescription = "Back",
                            tint = if (canGoBack) themeColors.topBarText else themeColors.topBarText.copy(alpha = 0.3f)
                        )
                    }
                    IconButton(
                        onClick = { webViewInstance?.goForward() },
                        enabled = canGoForward
                    ) {
                        Icon(
                            Icons.Default.ArrowForwardIos, 
                            contentDescription = "Forward",
                            tint = if (canGoForward) themeColors.topBarText else themeColors.topBarText.copy(alpha = 0.3f)
                        )
                    }
                    IconButton(onClick = { webViewInstance?.reload() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reload", tint = themeColors.topBarText)
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
    }
}
