# Changelog

All notable changes to this project will be documented in this file.

## [2.1.0] - 2026-08-11
### Changed
- Moved library distribution from Maven Central to a self-hosted Maven repository on GitHub Pages.
- Simplified the publishing process by removing GPG signing and Sonatype authentication.
- Repository URL is now `https://elpafras.github.io/LibCat/repo`.

### Removed
- `com.vanniktech.maven.publish` plugin.
- GPG signing configuration.

## [2.0.0] - 2026-08-07
### Added
- Standardized Version Catalog (`libs.versions.toml`) for all dependencies.
- Full support for Kotlin 2.4.10 and Compose Multiplatform 1.11.1.
- Modernized `SystemBarsController` for Android 15 (edge-to-edge support).

### Fixed
- Dependency resolution conflicts for iOS targets.
- Unchecked cast warnings in `SettingsProvider`.
- Deprecated system bar color APIs.

## [1.4.0] - 2026-06-15
### Initial Release
- Core theme management (font, size, color).
- Sync between Native Compose and WebView.
- Android and iOS support.
