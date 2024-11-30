package com.example.learnconnect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.learnconnect.theme.LearnConnectTheme
import com.example.learnconnect.theme.ThemePreferences

@Composable
fun App() {
    val context = LocalContext.current
    val themePreferences = remember { ThemePreferences(context) }

    val isDarkTheme by remember { mutableStateOf(themePreferences.getDarkModeState()) }

    LaunchedEffect(isDarkTheme) {
        themePreferences.saveDarkModeState(isDarkTheme)
    }
    LearnConnectTheme(isDarkTheme = isDarkTheme) {

    }
}

