package mr.cat.setting.utility

import android.webkit.WebView
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontName

class FontInjector(private val registry: FontRegistry) {

    private var injected = false

    fun injectFontFaces(webView: WebView) {
        if (injected) return
        val css = registry.buildFontFaceCSS()
        if (css.isBlank()) return

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
        injected = true
    }

    fun switchFont(webView: WebView, option: FontStyleOption) {
        val fontFamily = option.toFontName()
        webView.evaluateJavascript(
            "document.body.style.fontFamily = '$fontFamily';",
            null
        )
    }

    fun reset() {
        injected = false
    }
}