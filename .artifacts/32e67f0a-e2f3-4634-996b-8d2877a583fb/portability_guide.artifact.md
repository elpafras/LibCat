# Panduan Portabilitas & Kalkulasi Implementasi

Dokumen ini memberikan breakdown teknis dan estimasi upaya (kalkulasi) untuk mengimplementasikan fitur Teks Antik (Hebrew/Greek) dan Sistem Pengaturan Dinamis ke dalam proyek Android lain.

## 1. Breakdown Komponen Teknis

| Komponen | Deskripsi | Tingkat Kesulitan |
| :--- | :--- | :--- |
| **Sistem Tema Dinamis** | Penggunaan `CompositionLocalProvider` untuk menyebarkan pengaturan font dan warna secara global. | Menengah |
| **Penyimpanan DataStore** | Implementasi `DataStore` untuk menyimpan preferensi pengguna (tema, font, ukuran) secara persisten. | Menengah |
| **Dukungan RTL (Hebrew)** | Implementasi `LocalLayoutDirection` khusus untuk blok teks Ibrani tanpa mengubah arah seluruh aplikasi. | Mudah |
| **UI Pengaturan (Bottom Sheet)** | Komponen UI yang dapat dipanggil dari `TopAppBar` di setiap layar. | Mudah |

## 2. Kalkulasi Langkah Implementasi

### Fase A: Infrastruktur (Estimasi: 4-6 Jam)
1.  **Modul Settings**: Salin atau buat modul baru yang menangani `DataStore`.
2.  **State Management**: Buat `ViewModel` atau `StateFlow` untuk memantau perubahan pengaturan.
3.  **Theme Wrapper**: Bungkus aplikasi dalam `CustomTheme { ... }` yang membaca dari `CompositionLocal`.

### Fase B: Teks Antik & RTL (Estimasi: 1-2 Jam)
1.  **Helper RTL**: Implementasikan fungsi pembungkus untuk teks Ibrani:
    ```kotlin
    @Composable
    fun HebrewText(text: String) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(text = text, modifier = Modifier.fillMaxWidth())
        }
    }
    ```
2.  **Koleksi Font**: Pastikan font yang mendukung karakter Yunani/Ibrani tersedia di `res/font`.

### Fase C: Integrasi UI (Estimasi: 2-3 Jam)
1.  **Scaffold Pattern**: Standarisasi `TopAppBar` untuk menyertakan `IconButton` pengaturan.
2.  **BottomSheet Integration**: Tambahkan `SettingBottomSheet` ke dalam `Scaffold` utama atau melalui navigasi.

## 3. Prasyarat (Dependencies)

Untuk mengimplementasikan ini di proyek lain, Anda memerlukan:
- **Jetpack Compose Material 3** (Wajib)
- **androidx.datastore:datastore-preferences** (Untuk persistensi)
- **Kotlin Multiplatform** (Opsional, jika ingin berbagi logika dengan iOS seperti di proyek ini)

## 4. Keuntungan Arsitektur
- **Konsistensi**: Satu perubahan di pengaturan langsung mengubah seluruh aplikasi.
- **Aksesibilitas**: Dukungan RTL memastikan teks Ibrani dapat dibaca dengan benar secara gramatikal.
- **Skalabilitas**: Mudah untuk menambahkan bahasa antik lain (seperti Arab atau Suryani) dengan pola yang sama.

> [!IMPORTANT]
> Jika proyek tujuan bukan Kotlin Multiplatform, Anda dapat menyederhanakan modul `:setting` menjadi paket internal di dalam modul `:app`.
