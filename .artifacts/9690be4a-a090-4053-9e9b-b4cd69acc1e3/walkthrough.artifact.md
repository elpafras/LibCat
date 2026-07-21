# Walkthrough - Restrukturisasi LibCat ke Kotlin Multiplatform (Tahap 1)

Saya telah berhasil menyelesaikan tahap pertama migrasi LibCat ke **Kotlin Multiplatform (KMP)**. Fokus tahap ini adalah memindahkan logika bisnis inti dan model data ke `commonMain` serta menyiapkan infrastruktur untuk mendukung Android dan iOS di masa depan, dengan tetap menjaga fungsionalitas Android 100% identik.

## Perubahan Utama

### 1. Konfigurasi Gradle (KMP & Compose Multiplatform)
- Module `:setting` kini menggunakan plugin `kotlin("multiplatform")` dan `org.jetbrains.compose`.
- Menyiapkan target `androidTarget` dan target iOS (`iosX64`, `iosArm64`, `iosSimulatorArm64`).
- Mengatur source sets: `commonMain` untuk logika bersama, `androidMain` untuk kode spesifik Android (termasuk WebView), dan `iosMain` untuk masa depan.

### 2. Migrasi Model & Logic ke `commonMain`
- **Model Data**: `FontSizeOption`, `FontStyleOption`, dan `ThemeOption` kini berada di `commonMain`.
- **ViewModel**: `SettingViewModel` dipindahkan ke `commonMain` menggunakan library `androidx.lifecycle.ViewModel` versi Multiplatform.
- **Repository Pattern**: Memperkenalkan `SettingRepository` (interface) dan `SettingDataStoreRepository` untuk memisahkan logika persistensi dari ViewModel.

### 3. Persistensi Data (Jetpack DataStore KMP)
- Menggunakan `androidx.datastore:datastore-preferences-core` yang mendukung Multiplatform.
- Implementasi `expect/actual` untuk `createDataStore()`:
    - **Android**: Menggunakan `Context` dan menyimpan di direktori aplikasi standar.
    - **iOS**: Menyiapkan path menggunakan `NSDocumentDirectory`.

### 4. Kompatibilitas Android (Zero Breaking Change)
- Memindahkan semua file Android yang ada (UI, WebView Manager, Injector) ke `androidMain`.
- Memperbarui `rememberSettingState` dan `SettingBottomSheet` untuk menyediakan `SettingViewModel` secara otomatis menggunakan factory, sehingga kode di aplikasi utama (`:app`) tidak perlu diubah sama sekali.

## Struktur Proyek Baru
```text
setting/
├── build.gradle.kts
├── src/
│   ├── commonMain/kotlin/mr/cat/setting/
│   │   ├── component/model/ (Logic & Options)
│   │   ├── repository/ (Data interface & implementation)
│   │   ├── viewmodel/ (Shared ViewModel)
│   │   └── datastore/ (Expect factory)
│   ├── androidMain/kotlin/mr/cat/setting/
│   │   ├── base/ (WebView Manager)
│   │   ├── component/ (Android UI)
│   │   ├── datastore/ (Actual factory for Android)
│   │   └── utility/ (Android Injectors)
│   └── iosMain/kotlin/mr/cat/setting/
│       └── datastore/ (Actual factory for iOS)
```

## Verifikasi
- Perintah `./gradlew :app:assembleDebug` berhasil diselesaikan.
- Semua fungsionalitas di aplikasi demo Android tetap berjalan normal.

> [!NOTE]
> Properti `android.builtInKotlin=false` dan `android.newDsl=false` ditambahkan sementara di `gradle.properties` untuk menjaga kompatibilitas AGP 9.3 dengan plugin KMP tradisional selama masa transisi.
