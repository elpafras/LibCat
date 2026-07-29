package mr.cat.setting.utility

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Implementasi Android untuk [SystemBarsController].
 */
class AndroidSystemBarsController(
    private val view: View,
    private val window: Window
) : SystemBarsController {
    
    override fun applyTheme(
        statusBarColor: Color,
        navigationBarColor: Color,
        useDarkIcons: Boolean
    ) {
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()
        
        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = useDarkIcons
        controller.isAppearanceLightNavigationBars = useDarkIcons
    }
}

@Composable
actual fun rememberSystemBarsController(): SystemBarsController? {
    val view = LocalView.current
    return remember(view) {
        val window = (view.context as? Activity)?.window
        if (window != null) {
            AndroidSystemBarsController(view, window)
        } else {
            null
        }
    }
}
