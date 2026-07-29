# Walkthrough - Implementasi Teks Ibrani & Yunani

Saya telah berhasil menambahkan activity khusus untuk mendemonstrasikan teks Ibrani dan Yunani di dalam modul `:app`.

## Perubahan Utama

### 1. Tombol Pengaturan di Semua Layar
Saya telah menambahkan tombol `Settings` di `TopAppBar` pada seluruh layar di modul `:app`:
- **Article Detail** & **Profile** (`DummyScreens.kt`)
- **Settings Showcase** (`SettingsShowcaseScreen.kt`)
- **Browser/WebView** (`WebViewScreen.kt`)
- **Hebrew & Greek Demo** (`HebrewGreekActivity.kt`)

Setiap tombol akan membuka `SettingBottomSheet`, memungkinkan pengguna untuk mengubah tema (Terang/Gelap), jenis font, dan ukuran teks secara instan dari layar mana pun.

### 2. Activity Khusus: [HebrewGreekActivity.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/HebrewGreekActivity.kt)
Activity ini sekarang juga mendukung perubahan tema dinamis. Perhatikan bagaimana teks Ibrani (RTL) dan Yunani (LTR) tetap terbaca dengan baik saat Anda mengubah ukuran teks atau beralih ke tema gelap.

## Cara Verifikasi
1. Jalankan aplikasi LibCat.
2. Navigasikan ke layar mana pun (Detail, Profil, Browser, atau Ibrani/Yunani).
3. Klik ikon roda gigi (pengaturan) di pojok kanan atas.
4. Ubah tema atau ukuran teks, dan lihat perubahannya diterapkan secara real-time di layar tersebut.

> [!TIP]
> Implementasi ini memanfaatkan `LibCatTheme` yang sudah ada, sehingga warna dan font tetap konsisten dengan pengaturan aplikasi global.
