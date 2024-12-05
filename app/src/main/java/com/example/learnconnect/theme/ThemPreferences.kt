package com.example.learnconnect.theme

import android.content.Context


class ThemePreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveDarkModeState(isDarkTheme: Boolean) {
        sharedPreferences.edit().putBoolean("DARK_MODE", isDarkTheme).apply()
    }

    fun getDarkModeState(): Boolean {
        return sharedPreferences.getBoolean("DARK_MODE", false)
    }
}