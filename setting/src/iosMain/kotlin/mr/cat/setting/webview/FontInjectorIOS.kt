package mr.cat.setting.webview

import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.utility.FontRegistry
import platform.WebKit.WKWebView

/**
 * [FontInjectorIOS] adalah counterpart iOS dari FontInjector Android.
 * Mengelola injeksi @font-face dan penggantian font pada WKWebView.
 */
class FontInjectorIOS(private val registry: FontRegistry) {

    private val injectedFonts = mutableSetOf<String>()

    /**
     * Inject @font-face ke WKWebView.
     */
    fun injectFontFace(webView: WKWebView, option: FontStyleOption) {
        val fontName = option.toFontName()
        if (injectedFonts.contains(fontName)) return

        val css = registry.buildFontFaceCSS(option) ?: return
        val styleId = "libcat-font-face-${option.name}"

        // WKWebView evaluateJavaScript idiomatik: null completionHandler jika tidak perlu result
        val js = """
            (function() {
                if (document.getElementById('$styleId')) return;
                var style = document.createElement('style');
                style.id = '$styleId';
                style.textContent = `${css.replace("`", "\\`")}`;
                document.head.appendChild(style);
            })();
        """.trimIndent()

        webView.evaluateJavaScript(js, null)
        injectedFonts.add(fontName)
    }

    /**
     * Mengganti font aktif di WKWebView.
     */
    fun switchFont(webView: WKWebView, option: FontStyleOption) {
        val fontFamily = option.toFontName()
        val fontWeight = if (option == FontStyleOption.MONTSERRAT) "bold" else "normal"
        injectFontFace(webView, option)

        val js = """
            document.documentElement.style.setProperty('--font-family', '$fontFamily');
            document.documentElement.style.setProperty('--font-weight', '$fontWeight');
        """.trimIndent()
        webView.evaluateJavaScript(js, null)
    }

    fun clearCache() {
        injectedFonts.clear()
    }
}
