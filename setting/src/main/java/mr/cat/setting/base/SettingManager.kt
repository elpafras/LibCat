package mr.cat.setting.base

import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mr.cat.setting.utility.FontInjector
import mr.cat.setting.utility.FontRegistry
import mr.cat.setting.viewmodel.SettingViewModel

class SettingManager(
    private val context: Context,
    private val viewModel: SettingViewModel
) {
    private val fontRegistry = FontRegistry(context)
    val fontInjector = FontInjector(fontRegistry)

    fun initialize(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            fontRegistry.preloadAll()
        }
    }

    fun applyFontTo(webView: WebView) {
        fontInjector.injectFontFaces(webView)
        fontInjector.switchFont(webView, viewModel.fontStyle.value)
    }

    fun observeFontChange(webView: WebView, lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.fontStyle.collect { option ->
                fontInjector.switchFont(webView, option)
            }
        }
    }
}