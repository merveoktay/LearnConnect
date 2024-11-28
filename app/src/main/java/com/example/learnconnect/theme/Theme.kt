package com.example.learnconnect.theme

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


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
private val darkColorPalette = darkColorScheme(
    primary = Color(0xFFFF6F61), // brand_color
    secondary = Color(0xFFAAAAAA), // hint_color
    background = Color(0xFF333333), // dark_background_color
    surface = Color(0xFF333333), // dark_background_color
    onPrimary = Color(0xFFFFFFFF), // Text on primary (white)
    onSecondary = Color(0xFF333333), // title_color
    onBackground = Color(0xFFFFFFFF), // white text on dark background
    onSurface = Color(0xFFFFFFFF), // white text on dark surface
)
private val lightColorPalette = lightColorScheme(
    primary = Color(0xFFFF6F61), // brand_color
    secondary = Color(0xFFAAAAAA), // hint_color
    background = Color(0xFFFFFFFF), // light_background_color
    surface = Color(0xFFF5F5F5), // surface_color
    onPrimary = Color(0xFFFFFFFF), // Text on primary (white)
    onSecondary = Color(0xFF333333), // title_color
    onBackground = Color(0xFF333333), // title_color
    onSurface = Color(0xFF333333), // body_text_color
)
@Composable
fun LearnConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorPalette
        else -> lightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
@Composable
fun AppTheme(isDarkMode: Boolean, content: @Composable () -> Unit) {
    val colors = if (isDarkMode) {
        darkColorPalette
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // Dark Mode

    } else {
        lightColorPalette
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Light Mode

    }


}