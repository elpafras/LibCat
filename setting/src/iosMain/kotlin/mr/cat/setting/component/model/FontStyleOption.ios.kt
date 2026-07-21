package mr.cat.setting.component.model

import androidx.compose.ui.text.font.FontFamily

actual fun FontStyleOption.toFontFamily(): FontFamily? {
    // iOS specific font loading can be implemented here using Res.font (Compose Multiplatform Resources)
    // or native iOS font loading. Returning null for now to fix build.
    return null
}
