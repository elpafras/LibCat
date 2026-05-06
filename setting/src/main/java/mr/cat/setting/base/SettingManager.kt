package mr.cat.setting.base

import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import mr.cat.setting.utility.FontInjector
import mr.cat.setting.viewmodel.SettingViewModel

class SettingManager(
    private val viewModel: SettingViewModel,
    private val fontInjector: FontInjector
) {
    fun bindFont(
        webView: WebView,
        lifecycleOwner: LifecycleOwner
    ) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fontStyle.collect { option ->
                    fontInjector.switchFont(webView, option)
                }
            }
        }
    }

    fun applyCurrentFont(webView: WebView) {
        val current = viewModel.fontStyle.value
        fontInjector.switchFont(webView, current)
    }
}