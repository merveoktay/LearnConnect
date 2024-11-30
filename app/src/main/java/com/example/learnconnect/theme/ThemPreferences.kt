package com.example.learnconnect.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable

class ThemePreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    @Composable
    fun LearnConnectTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
        val colors = if (isDarkTheme) darkColorPalette else lightColorPalette
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }

    fun saveDarkModeState(isDarkTheme: Boolean) {
        sharedPreferences.edit().putBoolean("DARK_MODE", isDarkTheme).apply()
    }

    fun getDarkModeState(): Boolean {
        return sharedPreferences.getBoolean("DARK_MODE", false)
    }
}
