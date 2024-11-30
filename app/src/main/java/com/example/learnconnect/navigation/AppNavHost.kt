package com.example.learnconnect.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learnconnect.ui.CourseScreen
import com.example.learnconnect.ui.CoursesScreen
import com.example.learnconnect.ui.FavoriteScreen
import com.example.learnconnect.ui.HomeScreen
import com.example.learnconnect.viewModels.LoginViewModel
import com.example.learnconnect.viewModels.RegisterViewModel
import com.example.learnconnect.ui.LoginScreen
import com.example.learnconnect.ui.ProfileScreen
import com.example.learnconnect.ui.RegisterScreen
import com.example.learnconnect.ui.SplashScreen
import com.example.learnconnect.ui.VideoPlayerScreen
import com.example.learnconnect.viewModels.CourseViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable("home") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            HomeScreen(
                onNavigateToCourse = { courseId ->
                    navController.navigate("course/$courseId")
                },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToCourses = { navController.navigate("courses") },
                courseViewModel = courseViewModel
            )
        }
        composable("favorite") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            FavoriteScreen(
                onNavigateToCourses = { navController.navigate("courses") },
                onNavigateToProfile = { navController.navigate("profile") },
                courseViewModel = courseViewModel
            )
        }

        composable("courses") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            CoursesScreen(
                onNavigateToCourse = { courseId ->
                    navController.navigate("course/$courseId")
                },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToHome = { navController.navigate("home") },
                courseViewModel = courseViewModel
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToFavorite = { navController.navigate("favorite") },
                onNavigateToCourses = { navController.navigate("courses") }
            )
        }
        composable(
            route = "video/{videoId}"
        ) { backStackEntry ->
            val courseViewModel: CourseViewModel = hiltViewModel()
            val videoId = backStackEntry.arguments?.getString("videoId")?.toIntOrNull() ?: 0
            VideoPlayerScreen(videoId = videoId, navController = navController,courseViewModel)
        }
        composable("course/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull() ?: 0
            val courseViewModel: CourseViewModel = hiltViewModel()
            val loginViewModel: LoginViewModel = hiltViewModel()
            CourseScreen(
                viewModel = courseViewModel,
                onNavigateToVideoPlayer = { videoId ->
                    navController.navigate("video/$videoId")
                },
                loginViewModel = loginViewModel,
                courseId = courseId,
                onFavoriteClick = { /* Favorite işlemini burada tanımlayın */ },
                navController = navController,
            )
        }
    }
}