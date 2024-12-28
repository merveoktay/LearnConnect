package com.example.learnconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.learnconnect.navigation.AppNavHost
import com.example.learnconnect.theme.LearnConnectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val systemTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemTheme) }
            LearnConnectTheme(isDarkTheme) {
                AppNavHost(isDarkTheme, changeAppTheme = { isDarkTheme = !isDarkTheme })

            }

        }
    }

}
