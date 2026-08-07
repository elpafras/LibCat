# Changelog

Semua perubahan penting pada proyek ini akan dicatat dalam file ini.

## [1.4.0] - 2026-08-07

### Added
- Konfigurasi untuk publikasi ke **Maven Central** menggunakan `com.vanniktech.maven.publish`.
- Dukungan target iOS baru: `iosX64`.
- Workflow GitHub Actions untuk otomatisasi publikasi.

### Changed
- **Breaking Change**: Group ID diubah dari `com.github.elpafras` menjadi `io.github.elpafras`.
- **Breaking Change**: Artifact ID diubah menjadi lowercase `libcat`.
- Update versi ke `1.4.0`.

### Migration Guide
Untuk bermigrasi dari JitPack ke Maven Central:
1. Ubah dependency di `build.gradle.kts`:
   ```kotlin
   implementation("io.github.elpafras:libcat:1.4.0")
   ```
2. Hapus repository JitPack jika tidak lagi dibutuhkan:
   ```kotlin
   repositories {
       mavenCentral()
       // remove maven("https://jitpack.io")
   }
   ```
