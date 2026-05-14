package mr.cat.setting.utility

import android.util.Log
import android.webkit.WebView
import mr.cat.setting.component.model.FontStyleOption

class FontInjector(private val registry: FontRegistry) {

    /**
     * Inject @font-face ke WebView untuk font tertentu.
     * Idempotent: tidak akan inject ulang jika sudah ada di DOM.
     */
    fun injectFontFace(webView: WebView, option: FontStyleOption) {
        val css = registry.buildFontFaceCSS(option) ?: return

        val styleId = "libcat-font-face-${option.name}"

        val js = """
            (function() {
                var existing = document.getElementById('$styleId');
                if (existing) return;

                var style = document.createElement('style');
                style.id = '$styleId';
                style.textContent = `$css`;
                document.head.appendChild(style);
            })();
        """.trimIndent()

        Log.d("FontInjector", "injectFontFace: ${option.name}")
        webView.evaluateJavascript(js, null)
    }

    /**
     * Mengganti font aktif di halaman.
     * Akan memastikan font sudah diinject terlebih dahulu (lazy).
     */
    fun switchFont(webView: WebView, option: FontStyleOption) {
        val fontFamily = option.toFontName()

        // Lazy inject hanya untuk font ini
        injectFontFace(webView, option)

        val js = """
            (function() {
                document.body.style.fontFamily = '$fontFamily';
            })();
        """.trimIndent()

        Log.d("FontInjector", "switchFont: $fontFamily")
        webView.evaluateJavascript(js, null)
    }

    /**
     * Optional: reset tidak lagi diperlukan karena tidak ada state lokal.
     * Disediakan untuk kompatibilitas dengan API lama.
     */
    fun reset() {
        Log.d("FontInjector", "reset (no-op)")
    }
}