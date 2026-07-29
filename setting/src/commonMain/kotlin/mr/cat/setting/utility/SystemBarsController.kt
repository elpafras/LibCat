package mr.cat.setting.utility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Interface untuk mengontrol warna status bar dan navigation bar sistem (OS-level chrome).
 */
fun interface SystemBarsController {
    /**
     * Menerapkan warna tema ke status bar dan navigation bar.
     * 
     * @param statusBarColor Warna latar belakang status bar.
     * @param navigationBarColor Warna latar belakang navigation bar.
     * @param useDarkIcons Jika true, ikon (jam, sinyal, dll) menggunakan warna gelap (untuk bg terang).
     */
    fun applyTheme(
        statusBarColor: Color,
        navigationBarColor: Color,
        useDarkIcons: Boolean
    )
}

/**
 * Utilitas untuk mengelola [SystemBarsController].
 */
object SystemBarsUtils {
    /**
     * CompositionLocal untuk mengakses [SystemBarsController].
     */
    val LocalSystemBarsController = staticCompositionLocalOf<SystemBarsController?> { null }

    /**
     * Menyediakan implementasi [SystemBarsController] sesuai platform.
     */
    @Composable
    fun rememberController(): SystemBarsController? = rememberSystemBarsController()
}

@Composable
expect fun rememberSystemBarsController(): SystemBarsController?
