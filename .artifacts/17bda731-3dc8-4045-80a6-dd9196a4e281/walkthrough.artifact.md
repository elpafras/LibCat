# Walkthrough - Bold Montserrat as Default Font

I have implemented the request to make **Montserrat** the default font style and ensure it renders in **Bold** weight across both Compose and WebView environments.

## Changes Made

### 1. Default Setting Update
- Updated [SettingViewModel.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/commonMain/kotlin/mr/cat/setting/viewmodel/SettingViewModel.kt) and [SettingDataStoreRepository.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/commonMain/kotlin/mr/cat/setting/repository/SettingDataStoreRepository.kt) to use `FontStyleOption.MONTSERRAT` as the initial/fallback value.

### 2. Compose UI (Android)
- Modified [Theme.kt](file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/ui/theme/Theme.kt) to automatically apply `FontWeight.Bold` to all typography styles when Montserrat is selected.
- Updated [FontStyleSelector.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/androidMain/kotlin/mr/cat/setting/component/FontStyleSelector.kt) to visually reflect the bold weight in the settings dropdown and selection box.

### 3. WebView Injection (Android & iOS)
- Enhanced [SettingManager.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/androidMain/kotlin/mr/cat/setting/base/SettingManager.kt) and [SettingManagerIOS.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/iosMain/kotlin/mr/cat/setting/webview/SettingManagerIOS.kt) to inject the `--font-weight` CSS variable into the WebView.
- Updated [FontInjector.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/androidMain/kotlin/mr/cat/setting/utility/FontInjector.kt) and [FontInjectorIOS.kt](file:///home/elan/Documents/android_project/LibCat/setting/src/iosMain/kotlin/mr/cat/setting/webview/FontInjectorIOS.kt) to handle the font weight property during font switching.

## Verification Results

> [!NOTE]
> The changes were applied successfully across common, android, and ios source sets. Montserrat is now the starting font for new users and appears bolded as requested.

### Code Verification
- Verified that `FontWeight.Bold` is used in Compose when `FontStyleOption.MONTSERRAT` is active.
- Verified that CSS variable `--font-weight` is set to `bold` for Montserrat and `normal` for others.
- Confirmed that default values in DataStore repository now point to Montserrat.

render_diffs(file:///home/elan/Documents/android_project/LibCat/setting/src/commonMain/kotlin/mr/cat/setting/viewmodel/SettingViewModel.kt)
render_diffs(file:///home/elan/Documents/android_project/LibCat/setting/src/commonMain/kotlin/mr/cat/setting/repository/SettingDataStoreRepository.kt)
render_diffs(file:///home/elan/Documents/android_project/LibCat/app/src/main/java/mr/cat/libcat/ui/theme/Theme.kt)
render_diffs(file:///home/elan/Documents/android_project/LibCat/setting/src/androidMain/kotlin/mr/cat/setting/base/SettingManager.kt)
render_diffs(file:///home/elan/Documents/android_project/LibCat/setting/src/androidMain/kotlin/mr/cat/setting/utility/FontInjector.kt)
