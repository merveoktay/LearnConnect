package com.example.learnconnect.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learnconnect.ui.HomeScreen
import com.example.learnconnect.viewModels.LoginViewModel
import com.example.learnconnect.viewModels.RegisterViewModel
import com.example.learnconnect.ui.LoginScreen
import com.example.learnconnect.ui.RegisterScreen
import com.example.learnconnect.viewModels.VideoViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel() // Hilt ile ViewModel'ı al
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { navController.navigate("home") }
            )
        }
        composable("register") {
            val registerViewModel: RegisterViewModel = hiltViewModel() // Hilt ile ViewModel'ı al
            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable("home") {
            val videoViewModel: VideoViewModel = hiltViewModel() // Hilt ile ViewModel'ı al
            HomeScreen(onNavigateToProfile={navController.navigate("profile")},
                onNavigateToVideo={navController.navigate("video")},
                videoViewModel = videoViewModel
            )
        }
    }
}