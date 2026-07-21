package mr.cat.setting.base

import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.utility.FontInjector
import mr.cat.setting.viewmodel.SettingViewModel
import kotlin.time.Duration.Companion.milliseconds

class SettingManager(
    private val viewModel: SettingViewModel,
    private val fontInjector: FontInjector
) {

    private var isPageReady = false
    private var bindJob: Job? = null

    /**
     * Menghubungkan state pengaturan dengan WebView.
     * Menggunakan batching (combine) dan debouncing untuk efisiensi eksekusi JS.
     */
    @OptIn(FlowPreview::class)
    fun bind(
        webView: WebView,
        lifecycleOwner: LifecycleOwner
    ) {
        bindJob?.cancel()
        bindJob = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                
                // Menggabungkan 3 flow menjadi 1 untuk meminimalkan jumlah JS call.
                // Debounce 150ms memastikan perubahan cepat (seperti slider) tidak lag.
                combine(
                    viewModel.fontStyle,
                    viewModel.fontSize,
                    viewModel.themeId
                ) { font, sizeOption, theme ->
                    Triple(font, sizeOption, theme)
                }.debounce(150.milliseconds)
                .collect { (font, sizeOption, theme) ->
                    if (!isPageReady) return@collect
                    
                    val size = sizeOption.toTextUnit().value
                    val safeTheme = theme.replace("'", "\\'")
                    
                    // Optimasi: Gunakan CSS Custom Properties pada root element untuk mengurangi reflow.
                    // Eksekusi semua perubahan gaya dalam satu batch JS call.
                    val batchJs = """
                        (function() {
                            var root = document.documentElement;
                            root.style.setProperty('--font-size', '${size}px');
                            root.setAttribute('data-theme', '$safeTheme');
                        })();
                    """.trimIndent()

                    webView.evaluateJavascript(batchJs, null)
                    
                    // Font injection dikelola secara lazy oleh FontInjector
                    fontInjector.switchFont(webView, font)
                }
            }
        }
    }

    /**
     * Dipanggil saat halaman WebView selesai dimuat.
     */
    fun notifyPageReady(webView: WebView) {
        isPageReady = true
        applyAll(webView)
    }

    /**
     * Dipanggil saat halaman WebView mulai dimuat ulang.
     * Membersihkan cache font injector karena DOM akan di-reset.
     */
    fun notifyPageReload() {
        isPageReady = false
        fontInjector.clearCache()
    }

    /**
     * Menerapkan semua pengaturan sekaligus (Initial Apply).
     */
    private fun applyAll(webView: WebView) {
        val size = viewModel.fontSize.value.toTextUnit().value
        val themeId = viewModel.themeId.value.replace("'", "\\'")
        val font = viewModel.fontStyle.value

        val batchJs = """
            (function() {
                var root = document.documentElement;
                root.style.setProperty('--font-size', '${size}px');
                root.setAttribute('data-theme', '$themeId');
            })();
        """.trimIndent()

        webView.evaluateJavascript(batchJs, null)
        fontInjector.switchFont(webView, font)
    }
}