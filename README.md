# LibCat 🐱

[![Android API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**LibCat** is a modern, lightweight Android library built with **Jetpack Compose** designed to simplify common application configuration tasks. It provides a set of plug-and-play components for font management, theme switching, and dynamic UI adjustments, specifically optimized for content-heavy applications like e-book readers or news apps.

---

## ✨ Why LibCat?

Building a robust settings system (fonts, themes, persistent storage) usually involves a lot of boilerplate code. LibCat handles the heavy lifting:
- **Zero-Boilerplate Persistence**: Integrated with Jetpack DataStore.
- **Modern UI**: Pure Jetpack Compose with Material 3 support.
- **WebView Ready**: Powerful injection system to sync app styles with WebView content.
- **Modular**: Only use what you need.

---

## 🚀 Features

### 🎨 Theme & UI
- **Theme Switcher**: Easy toggling between Light, Dark, and Custom themes.
- **Font Customization**: Built-in selectors for Font Family, Font Size, and Font Style.
- **Material 3**: Fully compatible with the latest Material Design guidelines.

### 🌐 WebView Integration
- **Dynamic Injection**: Seamlessly apply user-selected fonts and CSS styles into `WebView` content on-the-fly.
- **Lifecycle Aware**: Automatically manages style updates during page loads and app lifecycle events.

### 💾 Performance & Storage
- **Jetpack DataStore**: Fast, asynchronous, and safe data persistence.
- **ViewModel Powered**: State-driven architecture for a reactive UI.

---

## 📦 Installation

Add the following to your project-level `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then, add the dependency to your module-level `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.elpafras:LibCat:{version}")
}
```

---

## 🛠️ Quick Start

### 1. Provide the Settings
Wrap your application or screen with `SettingsProvider` to initialize the environment:

```kotlin
SettingsProvider {
    MainScreen()
}
```

### 2. Show the Settings UI
Use the pre-built `SettingBottomSheet` to give users control:

```kotlin
var showSheet by remember { mutableStateOf(false) }

SettingBottomSheet(
    show = showSheet,
    onDismiss = { showSheet = false }
)
```

### 3. Sync with WebView
To inject styles into a WebView, use the `SettingManager`:

```kotlin
val settingManager = LocalSettingManager.current

AndroidView(
    factory = { ctx ->
        WebView(ctx).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    settingManager.notifyPageReady(view)
                }
            }
        }
    }
)
```

---

## 🛠️ Tech Stack

- **UI**: Jetpack Compose (Material 3)
- **Language**: Kotlin
- **Storage**: Jetpack DataStore (Preferences)
- **Architecture**: MVVM / State-Driven
- **Target SDK**: 37+ (Android 8.0 API 26+)

---

## 🗺️ Roadmap

- [ ] 🔊 **Audio Management**: Unified API for handling app sounds.
- [ ] 🪟 **Pop-up Components**: Customizable dialogs and tooltips.
- [ ] 🌐 **Localization**: On-the-fly language switching.
- [ ] 📱 **Multiplatform**: Initial support for Kotlin Multiplatform (KMP).

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request or open an Issue.

---
*Made with ❤️ for Android Developers.*
