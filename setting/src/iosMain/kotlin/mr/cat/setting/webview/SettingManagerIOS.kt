package mr.cat.setting.webview

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.viewmodel.SettingViewModel
import platform.WebKit.WKWebView
import platform.darwin.dispatch_get_main_queue
import kotlin.time.Duration.Companion.milliseconds

/**
 * [SettingManagerIOS] adalah counterpart iOS dari SettingManager Android.
 * API surface sengaja dibuat semirip mungkin untuk memudahkan penggunaan di kode shared.
 */
class SettingManagerIOS(
    private val viewModel: SettingViewModel,
    private val fontInjector: FontInjectorIOS,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
) {

    private var isPageReady = false
    private var bindJob: Job? = null

    @OptIn(FlowPreview::class)
    fun bind(webView: WKWebView) {
        bindJob?.cancel()
        bindJob = scope.launch {
            combine(
                viewModel.fontStyle,
                viewModel.fontSize,
                viewModel.themeId
            ) { font, sizeOption, theme ->
                Triple(font, sizeOption, theme)
            }.debounce(150.milliseconds)
            .collect { (font, sizeValue, theme) ->
                if (!isPageReady) return@collect
                
                val size = sizeValue
                val safeTheme = theme.replace("'", "\\'")
                val weight = "normal"
                
                val batchJs = """
                    (function() {
                        var root = document.documentElement;
                        root.style.setProperty('--font-size', '${size}px');
                        root.style.setProperty('--font-weight', '$weight');
                        root.setAttribute('data-theme', '$safeTheme');
                    })();
                """.trimIndent()

                // Pastikan eksekusi di main thread untuk WKWebView
                withContext(Dispatchers.Main) {
                    webView.evaluateJavaScript(batchJs, null)
                    fontInjector.switchFont(webView, font)
                }
            }
        }
    }

    fun notifyPageReady(webView: WKWebView) {
        isPageReady = true
        applyAll(webView)
    }

    fun notifyPageReload() {
        isPageReady = false
        fontInjector.clearCache()
    }

    private fun applyAll(webView: WKWebView) {
        val size = viewModel.fontSize.value
        val themeId = viewModel.themeId.value.replace("'", "\\'")
        val font = viewModel.fontStyle.value
        val weight = "normal"

        val batchJs = """
            (function() {
                var root = document.documentElement;
                root.style.setProperty('--font-size', '${size}px');
                root.style.setProperty('--font-weight', '$weight');
                root.setAttribute('data-theme', '$themeId');
            })();
        """.trimIndent()

        webView.evaluateJavaScript(batchJs, null)
        fontInjector.switchFont(webView, font)
    }

    fun dispose() {
        bindJob?.cancel()
    }
}
