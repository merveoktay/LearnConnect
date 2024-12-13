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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.learnconnect.R

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    changeAppTheme: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    onNavigateToCourses: () -> Unit,
) {
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
                Text(
                    text = "USER NAME",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(30.dp))
                TextButton(onClick = { onNavigateToFavorite() }) {
                    Text(
                        text = "❤ My Favorite Course ❤",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                DarkModeToggleScreen( isDarkTheme = isDarkTheme,
                    changeAppTheme = changeAppTheme)
            }
        }
    }


@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun DarkModeToggleScreen(  isDarkTheme: Boolean,
                           changeAppTheme: () -> Unit) {
         Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Dark Mode",
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(20.dp))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = {changeAppTheme()  },
                modifier = Modifier.padding(bottom = 16.dp),thumbContent = {
                    Icon(
                        painter = if (isDarkTheme) painterResource(id = R.drawable.moon_icon) else painterResource(id = R.drawable.sun_icon) ,
                        contentDescription = if (isDarkTheme) "Dark Theme" else "Light Theme",
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.hint_color),
                    uncheckedThumbColor = colorResource(id = R.color.brand_color)
                )
            )
        }

    }



