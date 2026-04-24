package mr.cat.setting.utility

import android.util.Log
import android.webkit.WebView
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontName

class FontInjector(private val registry: FontRegistry) {

    private var injected = false

    fun injectFontFaces(webView: WebView) {
        if (injected) {
            Log.d("FontInjector", "already injected, skip")
            return
        }
        val css = registry.buildFontFaceCSS()
        Log.d("FontInjector", "css length: ${css.length}")
        if (css.isBlank()) {
            Log.e("FontInjector", "css is blank, abort inject")
            return
        }

        val js = """
            (function() {
                var style = document.getElementById('libcat-font-face');
                if (!style) {
                    style = document.createElement('style');
                    style.id = 'libcat-font-face';
                    document.head.appendChild(style);
                }
                style.textContent = `$css`;
            })();
        """.trimIndent()

        webView.evaluateJavascript(js, null)

        Log.d("FontInjector", "inject success")
        injected = true
    }

    fun switchFont(webView: WebView, option: FontStyleOption) {
        if (!injected) {
            injectFontFaces(webView)
        }
        val fontFamily = option.toFontName()
        Log.d("FontInjector", "switchFont: $fontFamily")
        webView.evaluateJavascript(
            "document.body.style.fontFamily = '$fontFamily';",
            null
        )
    }
}