package mr.cat.setting.utility

import android.app.Activity
import android.os.Build
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
 * 
 * Strategi Pengelolaan System Bars:
 * 1. API < 35: Menggunakan warna solid legacy melalui [Window.statusBarColor] dan 
 *    [Window.navigationBarColor] untuk kompatibilitas ke belakang.
 * 2. API >= 35 (Android 15+): Menggunakan pola edge-to-edge modern. Warna bar tidak 
 *    diatur secara manual karena sistem secara otomatis membuatnya transparan/adaptif. 
 *    Fokus beralih ke pengaturan kontras ikon ([WindowCompat.getInsetsController]).
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
        if (Build.VERSION.SDK_INT >= 35) {
            // Android 15+ mewajibkan edge-to-edge. Kita pastikan decor fits system windows false
            // dan membiarkan sistem mengelola transparansi bar.
            WindowCompat.setDecorFitsSystemWindows(window, false)
        } else {
            // Kompatibilitas untuk versi lama yang masih mengandalkan warna solid pada window.
            @Suppress("DEPRECATION")
            window.statusBarColor = statusBarColor.toArgb()
            @Suppress("DEPRECATION")
            window.navigationBarColor = navigationBarColor.toArgb()
        }
        
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
