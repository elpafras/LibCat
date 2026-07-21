# Rencana Implementasi: Native Compose Adapter untuk LibCat

Menambahkan dukungan native Jetpack Compose ke LibCat agar pengaturan (tema, font, ukuran teks) dapat digunakan pada komponen `Text()` Compose tanpa melalui WebView.

## Proposed Changes

### [Component: Compose Adapter]

#### [NEW] [ThemeRegistry.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/main/java/mr/cat/setting/utility/ThemeRegistry.kt)
- Membuat fungsi `resolveThemeColors(themeId: String): SettingThemeColors` yang melakukan pemetaan dari `themeId` ke warna Compose.
- Menggunakan data dari `defaultThemes` yang sudah ada di `ThemeOption.kt` sebagai basis data.

#### [NEW] [SettingComposeAdapter.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/main/java/mr/cat/setting/compose/SettingComposeAdapter.kt)
- Membuat data class `SettingTextStyle` untuk membungkus `fontSize`, `fontFamily`, dan `themeColors`.
- Implementasi Composable `rememberSettingTextStyle()` yang mengamati state dari `SettingViewModel`.
- Implementasi pemuatan font dari assets menggunakan `Font(path, assetManager)` untuk mendukung permintaan spesifik pemuatan dari assets, dengan mekanisme caching untuk `FontFamily`.

## Open Questions

> [!NOTE]
> `FontStyleOption` saat ini sudah memiliki properti `fontResId` (R.font.xxx). Apakah saya harus tetap mengutamakan pemuatan dari assets (`fontFileName`) sesuai instruksi spesifik, atau menggunakan `toFontFamily()` yang sudah ada di enum tersebut?
> **Keputusan sementara**: Saya akan mengimplementasikan pemuatan dari assets sesuai instruksi user untuk memberikan fleksibilitas tambahan (misal jika font ditambahkan dinamis ke assets).

## Verification Plan

### Manual Verification
- Membuat layar baru di aplikasi dummy yang menggunakan `rememberSettingTextStyle()` dan menerapkannya pada `Text()` Compose.
- Memastikan perubahan pada `SettingBottomSheet` langsung merefleksikan perubahan pada teks Compose tersebut (ukuran, font, dan warna).
