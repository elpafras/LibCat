package mr.cat.libcat.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.base.SettingManager
import mr.cat.setting.viewmodel.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewTestScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val settingViewModel: SettingViewModel = viewModel()
    val settingManager = remember { SettingManager(context, settingViewModel) }

    var showSheet by remember { mutableStateOf(false) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(Unit) {
        settingManager.initialize(lifecycleOwner)
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(title = { Text("WebView Font Test") })

        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String) {
                            super.onPageFinished(view, url)
                            settingManager.applyFontTo(view)
                        }
                    }
                    loadDataWithBaseURL(
                        "http://fake.url/",
                        """
                        <html>
                        <head><style id='custom_style'></style></head>
                        <body>
                            <h1>Heading Test</h1>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                            <p>Ini adalah teks percobaan untuk font family dan font size.</p>
                        </body>
                        </html>
                        """.trimIndent(),
                        "text/html",
                        "utf-8",
                        null
                    )
                    webViewRef = this
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { showSheet = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Open Setting")
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }

    // observe font change
    webViewRef?.let { wv ->
        LaunchedEffect(wv) {
            settingManager.observeFontChange(wv, lifecycleOwner)
        }
    }
}