# Sinkronisasi System Bars dengan Tema LibCat

Rencana ini bertujuan untuk menyinkronkan warna Status Bar dan Navigation Bar secara otomatis dengan tema aktif LibCat pada platform Android dan iOS.

## Proposed Changes

### [Component: Setting]

#### [NEW] [SystemBarsController.kt (commonMain)](file:///home/elan/Documents/android_project/LibCat/setting/src/commonMain/kotlin/mr/cat/setting/utility/SystemBarsController.kt)
- Mendefinisikan `expect interface SystemBarsController` dengan method `applyTheme`.
- Mendefinisikan `expect fun rememberSystemBarsController(): SystemBarsController?`.
- Mendefinisikan `LocalSystemBarsController` via `staticCompositionLocalOf`.

#### [NEW] [SystemBarsController.kt (androidMain)](file:///home/elan/Documents/android_project/LibCat/setting/src/androidMain/kotlin/mr/cat/setting/utility/SystemBarsController.kt)
- Implementasi `actual class SystemBarsController` menggunakan `WindowInsetsControllerCompat`.
- `applyTheme` akan mengatur `statusBarColor`, `navigationBarColor`, serta `isAppearanceLightStatusBars`/`isAppearanceLightNavigationBars` berdasarkan parameter `useDarkIcons`.
- `rememberSystemBarsController` akan mengambil `Window` dari context `LocalView.current`.

#### [NEW] [SystemBarsController.kt (iosMain)](file:///home/elan/Documents/android_project/LibCat/setting/src/iosMain/kotlin/mr/cat/setting/utility/SystemBarsController.kt)
- Implementasi `actual class SystemBarsController` yang minimal.
- `applyTheme` akan menyediakan jembatan untuk mengatur style status bar (Light/Dark content).

#### [MODIFY] [LibCatTheme.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/commonMain/kotlin/mr/cat/setting/compose/LibCatTheme.kt)
- Mengintegrasikan `SystemBarsController` ke dalam `LibCatTheme` menggunakan `SideEffect`.
- Menghitung `useDarkIcons` secara otomatis menggunakan `ThemeRegistry.calculateContrastRatio` terhadap warna status bar.
- Memperbarui KDoc untuk menjelaskan fitur sinkronisasi otomatis ini.

### [Component: App]

#### [MODIFY] [Theme.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/ui/theme/Theme.kt)
- Mengintegrasikan logika yang sama ke dalam `LibCatTheme` versi modul `:app` untuk memastikan konsistensi jika project menggunakan versi ini.

## Verification Plan

### Manual Verification
- Menjalankan aplikasi di Android.
- Mengubah tema antara tema terang (misal: "HVS") dan tema gelap (misal: "Papan Tulis").
- Memastikan warna Status Bar dan Navigation Bar berubah menyatu dengan latar belakang aplikasi.
- Memastikan ikon status bar (jam, baterai) berubah menjadi hitam pada tema terang dan putih pada tema gelap.
