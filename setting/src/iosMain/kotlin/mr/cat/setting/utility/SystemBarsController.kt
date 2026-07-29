package mr.cat.setting.utility

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Implementasi iOS untuk [SystemBarsController].
 *
 * NOTE: iOS tidak memiliki konsep "navigation bar color" sistem yang sama dengan Android.
 * Status bar di iOS secara default transparan dan mengikuti warna view di belakangnya.
 * Pengaturan style (ikon terang/gelap) diatur secara global atau per-ViewController.
 */
class IosSystemBarsController : SystemBarsController {
    override fun applyTheme(
        statusBarColor: Color,
        navigationBarColor: Color,
        useDarkIcons: Boolean
    ) {
        // TODO: Integrasi preferredStatusBarStyle bridging.
        // Implementasi ini memerlukan akses ke root UIViewController di sisi Swift/UIKit
        // atau menggunakan HostingController properties jika menggunakan SwiftUI.
        // Untuk saat ini, LibCat menyerahkan pengaturan preferredStatusBarStyle ke level project
        // agar tidak mengintervensi navigasi aplikasi secara agresif di iOS.
    }
}

@Composable
actual fun rememberSystemBarsController(): SystemBarsController? {
    return IosSystemBarsController()
}
