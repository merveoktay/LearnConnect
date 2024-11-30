package com.example.learnconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.learnconnect.navigation.AppNavHost
import com.example.learnconnect.theme.LearnConnectTheme
import com.example.learnconnect.theme.ThemePreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val themePreferences = remember { ThemePreferences(context) }
            val isDarkTheme = themePreferences.getDarkModeState()

            LearnConnectTheme(isDarkTheme = isDarkTheme) {
                App()
                AppNavHost()
            }
        }

    }


}
