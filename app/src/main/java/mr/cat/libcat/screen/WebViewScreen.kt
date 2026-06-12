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
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.rememberSettingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    onBack: () -> Unit
) {
    val setting = rememberSettingState()
    val themeColors = setting.theme.colors
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.toTextUnit()

    var webView: WebView? by remember { mutableStateOf(null) }
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
                        onClick = { webView?.goBack() },
                        enabled = canGoBack
                    ) {
                        Icon(
                            Icons.Default.ArrowBackIosNew, 
                            contentDescription = "Back",
                            tint = if (canGoBack) themeColors.topBarText else themeColors.topBarText.copy(alpha = 0.3f)
                        )
                    }
                    IconButton(
                        onClick = { webView?.goForward() },
                        enabled = canGoForward
                    ) {
                        Icon(
                            Icons.Default.ArrowForwardIos, 
                            contentDescription = "Forward",
                            tint = if (canGoForward) themeColors.topBarText else themeColors.topBarText.copy(alpha = 0.3f)
                        )
                    }
                    IconButton(onClick = { webView?.reload() }) {
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
                        }
                    }
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                    webView = this
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
