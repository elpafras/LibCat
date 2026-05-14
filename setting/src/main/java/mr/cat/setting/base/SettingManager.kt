package mr.cat.setting.base

import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.utility.FontInjector
import mr.cat.setting.viewmodel.SettingViewModel

class SettingManager(
    private val viewModel: SettingViewModel,
    private val fontInjector: FontInjector
) {

    private var isPageReady = false

    fun bind(
        webView: WebView,
        lifecycleOwner: LifecycleOwner
    ) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // FONT STYLE
                launch {
                    viewModel.fontStyle.collect { option ->
                        if (!isPageReady) return@collect
                        fontInjector.switchFont(webView, option)
                    }
                }

                // FONT SIZE
                launch {
                    viewModel.fontSize.collect { option ->
                        if (!isPageReady) return@collect

                        val size = option.toTextUnit().value
                        webView.evaluateJavascript(
                            "document.body.style.fontSize='${size}px';",
                            null
                        )
                    }
                }

                // THEME
                launch {
                    viewModel.themeId.collect { theme ->
                        if (!isPageReady) return@collect

                        val safeTheme = theme.replace("'", "\\'")
                        webView.evaluateJavascript(
                            "document.body.setAttribute('data-theme', '$safeTheme');",
                            null
                        )
                    }
                }
            }
        }
    }

    fun notifyPageReady(webView: WebView) {
        isPageReady = true
        applyAll(webView)
    }

    fun notifyPageReload() {
        isPageReady = false
        // tidak perlu reset lagi
    }

    private fun applyAll(webView: WebView) {
        val size = viewModel.fontSize.value.toTextUnit().value
        val theme = viewModel.themeId.value.replace("'", "\\'")
        val font = viewModel.fontStyle.value

        // 1x JS call (efisien)
        webView.evaluateJavascript(
            """
            (function() {
                document.body.style.fontSize='${size}px';
                document.body.setAttribute('data-theme', '$theme');
            })();
            """.trimIndent(),
            null
        )

        // font (lazy inject handled inside)
        fontInjector.switchFont(webView, font)
    }
}