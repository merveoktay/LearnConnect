package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.learnconnect.R
import com.example.learnconnect.theme.AppTheme

@Composable
fun ProfileScreen(onNavigateToHome: () -> Unit,onNavigateToFavorite:()-> Unit,onNavigateToCourses:()-> Unit){

    Scaffold(
        topBar = {
            ProfileTopBar()
        },
        bottomBar = {
            ProfileBottomBar(onNavigateToHome,onNavigateToCourses)
        },
        content = { innerPadding ->
            ProfileContent( modifier = Modifier.padding(innerPadding),onNavigateToFavorite)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  ProfileTopBar(){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.xsmall_brand_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Profile", color = colorResource(id = R.color.title_color))
            }
        },
        actions = {

        }
    )
}
@Composable
fun  ProfileBottomBar(onNavigateToHome: () -> Unit,onNavigateToCourses:()->Unit){
    BottomAppBar(
        containerColor = colorResource(id = R.color.hint_color)
    ) {
        NavigationBar(
            containerColor = colorResource(id = R.color.hint_color),
            contentColor = colorResource(id = R.color.title_color)
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unselected_course_icon),
                        contentDescription = "My Courses",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = false,
                onClick = {onNavigateToCourses()}
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unselected_home_icon),
                        contentDescription = "Home",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = true,
                onClick = {onNavigateToHome() }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.selected_profile_icon),
                        contentDescription = "Profile",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = false,
                onClick = {}
            )
        }
    }
}
@Composable
fun  ProfileContent( modifier: Modifier = Modifier,onNavigateToFavorite:()-> Unit){
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row() {
            TextButton(onClick = { onNavigateToFavorite()}) {
                Text(
                    text = "My Favorite Course",
                    style = TextStyle(
                        color = colorResource(id = R.color.body_text_color),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            DarkModeToggleScreen()

        }

    }
}
@Composable
fun DarkModeToggleScreen() {
    var isDarkMode by remember { mutableStateOf(false) }

    AppTheme(isDarkMode = isDarkMode) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Dark Mode",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Toggle Button
            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
