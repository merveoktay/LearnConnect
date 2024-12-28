package com.example.learnconnect.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learnconnect.R
import com.example.learnconnect.models.User
import com.example.learnconnect.ui.components.DarkModeToggle
import com.example.learnconnect.utils.PreferencesManager
import com.example.learnconnect.viewModels.LoginViewModel
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    changeAppTheme: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    onNavigateToCourses: () -> Unit,
) {
    val context = LocalContext.current
    val userId = PreferencesManager.getUserId(context)
    val user by loginViewModel.user.collectAsState()
    LaunchedEffect(userId){
        loginViewModel.getUser(userId)
    }
        Log.d("isDarkTheme",isDarkTheme.toString())
        Scaffold(
            topBar = {
                ProfileTopBar()
            },
            bottomBar = {
                ProfileBottomBar(onNavigateToHome, onNavigateToCourses)
            },
            content = { innerPadding ->
                ProfileContent(
                    user =user,
                    modifier = Modifier.padding(innerPadding),
                    onNavigateToFavorite,
                    isDarkTheme = isDarkTheme,
                    changeAppTheme = changeAppTheme,
                )
            }
        )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar( ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ), title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.xsmall_brand_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Profile ", color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            actions = {}
        )
}

@Composable
fun ProfileBottomBar(onNavigateToHome: () -> Unit, onNavigateToCourses: () -> Unit) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.unselected_course_icon),
                            contentDescription = "My Courses",
                            modifier = Modifier.size(32.dp),
                        )
                    },
                    selected = false,
                    onClick = { onNavigateToCourses() },
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.unselected_home_icon),
                            contentDescription = "Home",
                            modifier = Modifier.size(32.dp),
                        )
                    },
                    selected = false,
                    onClick = { onNavigateToHome() },
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.selected_profile_icon),
                            contentDescription = "Profile",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    selected = true,
                    onClick = {},
                )
            }
        }
    }



@Composable
fun ProfileContent(
    user: User,
    modifier: Modifier = Modifier,
    onNavigateToFavorite: () -> Unit,
    isDarkTheme: Boolean,
    changeAppTheme: () -> Unit,
) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.username.uppercase(Locale.getDefault()),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                TextButton(onClick = { onNavigateToFavorite() }) {
                    Text(
                        text = "My Favorite Course ❤",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                DarkModeToggle( isDarkTheme = isDarkTheme,
                    changeAppTheme = changeAppTheme)
            }
        }
    }






