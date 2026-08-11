# LibCat 🐱

![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-blue?logo=kotlin)
![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20iOS-lightgrey)
![Android API](https://img.shields.io/badge/Android-API%2026%2B-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

**LibCat** adalah library Kotlin Multiplatform (KMP) untuk manajemen tema (font, ukuran teks, dan skema warna) yang tersinkronisasi secara reaktif ke DUA jenis rendering: UI native Compose Multiplatform (via `LibCatTheme`) dan konten WebView (via `SettingManager` + CSS injection), berjalan di Android dan iOS.

---

## 🚀 Instalasi

LibCat didistribusikan melalui self-hosted Maven repository di GitHub Pages.

### 1. Tambahkan Repository
Buka `settings.gradle.kts` dan tambahkan URL repository LibCat:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        // Repository LibCat
        maven { url = uri("https://elpafras.github.io/LibCat/repo") }
    }
}
```

### 2. Tambahkan Dependency
Buka `build.gradle.kts` di modul shared/kmp Anda (biasanya `commonMain`):

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            // Catatan: Artifact ID Maven adalah "setting" (mengikuti nama Gradle module internal), meskipun nama project tetap "LibCat".
            implementation("io.github.elpafras:setting:2.0.0")
        }
    }
}
```
> **Info:** TIDAK PERLU token/autentikasi/signing apapun untuk memakai library ini.

---

## ⚡ Quick Start (3 Langkah)

### 1. Inisialisasi SettingViewModel
Buat instance `SettingViewModel` menggunakan `SettingRepository` (yang memerlukan DataStore).

### 2. Pasang LibCatTheme di Root
Bungkus aplikasi Anda dengan `LibCatTheme` agar seluruh komponen Material3 otomatis mengikuti tema.

### 3. Tampilkan Panel Pengaturan
Gunakan `SettingBottomSheet` yang sudah disediakan untuk membiarkan pengguna mengganti tema.

---

## 📖 Penggunaan Lengkap

### A. Setup Dasar (SettingViewModel + Repository)
LibCat menggunakan DataStore untuk persistensi. Anda perlu menyediakan path storage yang sesuai untuk masing-masing platform.

**Android:**
```kotlin
val repository = SettingDataStoreRepository(
    dataStore = DataStoreFactory(context).create()
)
val viewModel = SettingViewModel(repository)
```

**iOS:**
```kotlin
// Di iOS, path biasanya didapat dari NSFileManager
val repository = SettingDataStoreRepository(
    dataStore = DataStoreFactory(customPath).create()
)
val viewModel = SettingViewModel(repository)
```

### B. Tema untuk UI Native (LibCatTheme)
`LibCatTheme` adalah komponen tingkat atas yang mengelola `MaterialTheme`.

```kotlin
setContent {
    LibCatTheme(viewModel = settingViewModel) {
        Scaffold {
            // Semua komponen Material3 di dalamnya (Button, Scaffold, AlertDialog, 
            // ModalNavigationDrawer, TextField, dst) otomatis mengikuti tema aktif 
            // tanpa wiring manual.
            MyAppContent()
        }
    }
}
```
*   **Independen:** `LibCatTheme` sepenuhnya independen dari `isSystemInDarkTheme()` (mengikuti pilihan user).
*   **Auto-Sync System Bars:** Status bar dan navigation bar akan berubah warna secara otomatis mengikuti tema aktif via `SystemBarsController` yang terintegrasi.

### C. Akses langsung ke gaya teks (rememberSettingTextStyle)
Jika Anda membutuhkan akses manual ke gaya teks (fontSize/fontFamily/warna tema) untuk kasus kustom:

```kotlin
val textStyle = rememberSettingTextStyle(viewModel = settingViewModel)

Text(
    text = "Halo LibCat",
    fontSize = textStyle.fontSize,
    fontFamily = textStyle.fontFamily,
    color = textStyle.theme.onBackground // Akses skema warna aktif
)
```

### D. Sinkronisasi ke WebView (SettingManager)
LibCat dapat menyuntikkan CSS secara reaktif ke dalam WebView. API ini tersedia di Android dan iOS dengan pola yang mirip.

**Android:**
```kotlin
val settingManager = SettingManager(viewModel, coroutineScope)
AndroidView(
    factory = { ctx ->
        WebView(ctx).apply {
            settingManager.bind(this) // Binding reaktif dimulai
            loadUrl("https://artikel-anda.com")
        }
    },
    update = { webView ->
        settingManager.notifyPageReady() // Panggil saat halaman siap
    }
)
```

**iOS:**
```kotlin
// Menggunakan SettingManagerIOS berbasis WKWebView
val settingManager = SettingManagerIOS(viewModel, coroutineScope)
settingManager.bind(wkWebView)
settingManager.notifyPageReady()
```

### E. Panel Pengaturan Siap Pakai (SettingBottomSheet)
LibCat menyediakan UI Bottom Sheet standar yang modern.

```kotlin
var showSheet by remember { mutableStateOf(false) }

SettingBottomSheet(
    show = showSheet,
    onDismiss = { showSheet = false },
    viewModel = settingViewModel
)
```

### F. Mengganti setting secara programatik
Anda bisa mengubah pengaturan langsung dari kode melalui ViewModel:
```kotlin
viewModel.setTheme("dark")           // ID Tema: hvs, sepia, dark
viewModel.setFontSize(20.sp)         // Ganti ukuran font
viewModel.setFontStyle("serif")       // Ganti jenis font
```

---

## 🎨 Kustomisasi Tema

### Daftar Tema Default
Saat ini LibCat mendukung ID tema berikut di `ThemeRegistry`:
- `hvs`: Putih bersih (High Contrast).
- `sepia`: Kekuningan (Nyaman untuk membaca).
- `dark`: Gelap/Malam.

### Menambah Font Baru
Untuk menambah font kustom, Anda harus mendaftarkannya di dua lokasi (**Known Limitation**):
1. **WebView:** Taruh file `.ttf` di `assets/font/`.
2. **Native Compose:** Taruh file `.ttf` di Compose Resources library.

---

## 📊 Platform Support

| Fitur | Android | iOS |
|---|:---:|:---:|
| LibCatTheme (native Compose) | ✅ | ✅ |
| SettingManager (WebView) | ✅ | ✅ |
| DataStore persistence | ✅ | ✅ |
| Status bar/nav bar sync | ✅ | ⚠️ (lihat known limitations) |

---

## 🏗️ Arsitektur Singkat
LibCat bekerja dengan alur searah:
`SettingViewModel` (commonMain) berperan sebagai **Source of Truth**. Perubahan di ViewModel akan dipancarkan ke `LibCatTheme` untuk UI Native dan melalui `SettingManager` (via JavaScript injection) untuk UI WebView (Android/iOS).

---

## ⚠️ Known Limitations
- **Font Assets**: Font perlu didaftarkan di dua lokasi (WebView assets & Compose Resources).
- **Artifact ID**: Maven artifact bernama `setting`, bukan `libcat` (alasan historis, mengikuti nama Gradle module).
- **iOS System Bars**: `SystemBarsController` untuk iOS memerlukan penyesuaian tambahan tergantung struktur root ViewController project consumer.

---

## 🤝 Contributing
Jika Anda menemukan bug atau memiliki ide fitur, silakan buka Issue atau kirimkan Pull Request.

---

## 📄 Lisensi
Library ini dilisensikan di bawah **MIT License**.

Copyright (c) 2026 Dri Handoko.
