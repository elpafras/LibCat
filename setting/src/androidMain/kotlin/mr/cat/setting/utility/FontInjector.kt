package mr.cat.setting.utility

import android.util.Log
import android.webkit.WebView
import mr.cat.setting.component.model.FontStyleOption

class FontInjector(private val registry: FontRegistry) {

    // Cache lokal untuk melacak font yang sudah di-inject ke WebView saat ini.
    // Menghindari overhead JS call document.getElementById yang berulang.
    private val injectedFonts = mutableSetOf<String>()

    /**
     * Inject @font-face ke WebView untuk font tertentu.
     * Idempotent di sisi Native (via cache) dan JS (via id).
     */
    fun injectFontFace(webView: WebView, option: FontStyleOption) {
        val fontName = option.toFontName()
        
        // Optimasi: Cek cache native terlebih dahulu
        if (injectedFonts.contains(fontName)) return

        val css = registry.buildFontFaceCSS(option) ?: return
        val styleId = "libcat-font-face-${option.name}"

        val js = """
            (function() {
                if (document.getElementById('$styleId')) return;
                var style = document.createElement('style');
                style.id = '$styleId';
                style.textContent = `$css`;
                document.head.appendChild(style);
            })();
        """.trimIndent()

        Log.d("FontInjector", "injectFontFace: ${option.name}")
        webView.evaluateJavascript(js, null)
        
        // Simpan ke cache setelah berhasil dikirim ke WebView
        injectedFonts.add(fontName)
    }

    /**
     * Mengganti font aktif di halaman menggunakan CSS Custom Property.
     * Mengurangi reflow dibandingkan mengubah inline style body secara langsung.
     */
    fun switchFont(webView: WebView, option: FontStyleOption) {
        val fontFamily = option.toFontName()

        // Lazy inject hanya untuk font ini
        injectFontFace(webView, option)

        val js = "document.documentElement.style.setProperty('--font-family', '$fontFamily');"

        Log.d("FontInjector", "switchFont: $fontFamily")
        webView.evaluateJavascript(js, null)
    }

    /**
     * Membersihkan cache font yang sudah di-inject.
     * Harus dipanggil saat WebView melakukan reload halaman.
     */
    fun clearCache() {
        injectedFonts.clear()
        Log.d("FontInjector", "Cache cleared")
    }

    /**
     * Reset tidak lagi diperlukan karena tidak ada state lokal permanen.
     */
    fun reset() {
        clearCache()
    }
}