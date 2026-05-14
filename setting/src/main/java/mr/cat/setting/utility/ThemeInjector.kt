package mr.cat.setting.utility

import android.webkit.WebView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import mr.cat.setting.component.model.ThemeOption

class ThemeInjector {
    fun applyTheme(
        webView: WebView,
        theme: ThemeOption
    ) {

        val background = theme.colors.background.toCssColor()
        val text = theme.colors.text.toCssColor()
        val topBar = theme.colors.topBar.toCssColor()
        val topBarText = theme.colors.topBarText.toCssColor()

        val js = """
            (function() {

                document.body.style.backgroundColor = '$background';
                document.body.style.color = '$text';

                document.documentElement.style.setProperty(
                    '--topbar-background',
                    '$topBar'
                );

                document.documentElement.style.setProperty(
                    '--topbar-text',
                    '$topBarText'
                );

            })();
        """.trimIndent()

        webView.evaluateJavascript(js, null)
    }

}

fun Color.toCssColor(): String {

    val argb = toArgb()

    val r = (argb shr 16) and 0xFF
    val g = (argb shr 8) and 0xFF
    val b = argb and 0xFF

    return "rgb($r, $g, $b)"
}