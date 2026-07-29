# Penambahan Tombol Pengaturan di Semua Layar

Rencana ini bertujuan untuk menambahkan tombol pengaturan (Settings) ke setiap layar (screen) dalam modul `:app`, sehingga pengguna dapat mengubah tema, font, dan ukuran teks dari mana saja.

## Proposed Changes

### [Component: :app]

Setiap layar berikut akan diperbarui untuk menyertakan:
1. State `showSheet` untuk mengontrol visibilitas `SettingBottomSheet`.
2. `IconButton` dengan ikon `Settings` pada `TopAppBar`.
3. Komponen `SettingBottomSheet` di dalam `Scaffold`.

#### [MODIFY] [DummyScreens.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/screen/DummyScreens.kt)
- Menambahkan tombol pengaturan ke `ArticleDetailScreen`.
- Menambahkan tombol pengaturan ke `ProfileScreen`.

#### [MODIFY] [SettingsShowcaseScreen.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/screen/SettingsShowcaseScreen.kt)
- Menambahkan tombol pengaturan ke `SettingsShowcaseScreen`.

#### [MODIFY] [WebViewScreen.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/screen/WebViewScreen.kt)
- Menambahkan tombol pengaturan ke `WebViewScreen` (bersama dengan navigasi browser).

#### [MODIFY] [HebrewGreekActivity.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/HebrewGreekActivity.kt)
- Menambahkan tombol pengaturan ke `HebrewGreekScreen`.

## Verification Plan

### Manual Verification
- Menjalankan aplikasi.
- Membuka setiap layar: Detail Artikel, Profil, Settings Showcase, Browser, dan Teks Ibrani/Yunani.
- Memastikan ikon pengaturan muncul di pojok kanan atas (TopAppBar).
- Memastikan mengklik ikon tersebut membuka Bottom Sheet pengaturan.
- Memastikan perubahan pengaturan (misal: ganti tema) langsung diterapkan di layar tersebut.
