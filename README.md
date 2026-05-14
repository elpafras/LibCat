# LibCat 🐱

LibCat is a comprehensive Android library designed to simplify common app configurations such as font settings, theme management, audio controls, and more. It provides ready-to-use UI components and utility classes to enhance user experience with minimal effort.

---

## 🚀 Features

### Current (v1.0.0)
- **Font Settings**: Change font family and font style dynamically.
- **Theme Management**: Switch between different app themes.
- **WebView Injection**: Seamlessly inject custom fonts and styles into `WebView` content (perfect for reader apps).
- **Persistence**: Built-in persistence using Jetpack DataStore.
- **Compose Ready**: Modern UI components built with Jetpack Compose.

### 🗺️ Roadmap (Coming Soon)
- 🔊 **Audio Management**: Unified API for handling app sounds and music.
- 🪟 **Pop-up Components**: Customizable dialogs, tooltips, and snackbars.
- 🌐 **Language/Localization**: Easy on-the-fly language switching.
- 🛠️ **DI Support**: First-class support for Hilt and Koin.
- 📱 **Multiplatform (KMP)**: Expanding to iOS and Desktop.
- 🧪 **Testing**: Comprehensive Unit and Screenshot tests.

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
    implementation("com.github.elpafras:LibCat:1.0.0")
}
```

---

## 🛠️ Usage Guide

### 1. Initialize SettingViewModel and SettingManager

In your Composable, set up the `SettingViewModel` and `SettingManager`. The `SettingManager` is responsible for applying settings to components like `WebView`.

```kotlin
val settingViewModel: SettingViewModel = viewModel()
val settingManager = remember {
    SettingManager(
        viewModel = settingViewModel,
        fontInjector = FontInjector(FontRegistry())
    )
}
```

### 2. Integrate with WebView (Optional)

If you are building a reader app, you can bind the `SettingManager` to a `WebView` to automatically apply font and theme changes.

```kotlin
AndroidView(
    factory = { ctx ->
        WebView(ctx).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    settingManager.notifyPageReady(view)
                }
            }
            // Load your content...
        }
    }
)

// Bind settings to the WebView lifecycle
LaunchedEffect(webViewRef) {
    settingManager.bind(webViewRef, lifecycleOwner)
}
```

### 3. Show the Settings Bottom Sheet

Easily provide a UI for users to change their settings using the built-in `SettingBottomSheet`.

```kotlin
var showSheet by remember { mutableStateOf(false) }

Button(onClick = { showSheet = true }) {
    Text("Open Settings")
}

SettingBottomSheet(
    show = showSheet,
    onDismiss = { showSheet = false }
)
```

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
