package com.example.learnconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learnconnect.viewModels.VideoViewModel
import com.example.learnconnect.ui.HomeScreen
import com.example.learnconnect.ui.LoginScreen
import com.example.learnconnect.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                onNavigateToRegister = { /* Profil sayfasına yönlendirilecek işlem */ },
                onNavigateToHome = { /* Video sayfasına yönlendirilecek işlem */ },
                viewModel = loginViewModel
            )
        }
    }
}
