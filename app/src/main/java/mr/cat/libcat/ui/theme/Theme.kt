package mr.cat.libcat.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.rememberSettingState

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun LibCatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val settings = rememberSettingState()
    val themeColors = settings.theme.colors
    val fontFamily = settings.fontStyle.toFontFamily()
    val fontSize = settings.fontSize.toTextUnit()

    val colorScheme = when {
        // We prioritize the theme from settings if it's not the default "hvs" or similar
        // Or we can just map settings colors to MaterialTheme colors
        else -> lightColorScheme(
            primary = themeColors.topBar,
            onPrimary = themeColors.topBarText,
            primaryContainer = themeColors.topBar,
            onPrimaryContainer = themeColors.topBarText,
            background = themeColors.background,
            onBackground = themeColors.text,
            surface = themeColors.background,
            onSurface = themeColors.text,
            secondary = themeColors.topBar.copy(alpha = 0.8f),
            onSecondary = themeColors.topBarText,
            tertiary = themeColors.topBar.copy(alpha = 0.6f)
        )
    }

    val typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = fontSize,
            lineHeight = (fontSize.value + 8).sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = (fontSize.value + 6).sp,
            lineHeight = (fontSize.value + 14).sp,
            letterSpacing = 0.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontSize = (fontSize.value - 2).sp,
            lineHeight = (fontSize.value + 4).sp,
            letterSpacing = 0.5.sp
        )
    )

    CompositionLocalProvider(LocalLibCatSettings provides settings) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
