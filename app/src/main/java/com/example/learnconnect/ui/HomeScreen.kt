package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learnconnect.R

@Composable
fun HomeScreen() {
    var isUserLoggedOut by remember { mutableStateOf(false) }
    if (isUserLoggedOut) {
        println("User logged out")
    }
    Scaffold(
        topBar = {
            HomeTopBar( onLogoutClick = {
                // Çıkış işlemi burada yapılacak
                isUserLoggedOut = true // Çıkış yapıldığında bu değeri true yapıyoruz
            })
        },
        bottomBar = {
            HomeBottomBar()
        },
        content = { innerPadding ->
            HomeContent(modifier = Modifier.padding(innerPadding))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onLogoutClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.hint_color)),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.xsmall_brand_logo), // Logo ikonu
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "LearnConnect",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.title_color)
                )
            }
        },
        actions = {
            IconButton(onClick ={onLogoutClick}) {
                Icon(
                    painter = painterResource(id = R.drawable.logout_icon),
                    contentDescription = "Menu",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    )
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = listOf("English", "Math", "Science", "History")
            items(categories) { category ->
                Chip(
                    text = category,
                    onClick = {  }
                )
            }
        }

        // Video Kartları
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(3) {
                VideoCard(
                    imageRes = R.drawable.sample_image,
                    courseName = "Video Name"
                )
            }
        }
    }
}

@Composable
fun HomeBottomBar() {
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
                onClick = { }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.selected_home_icon),
                        contentDescription = "Home",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = true,
                onClick = { }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unselected_profile_icon),
                        contentDescription = "Profile",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = false,
                onClick = { }
            )
        }
    }
}

@Composable
fun Chip(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .size(90.dp, 45.dp),
        color = Color.LightGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            color = colorResource(id = R.color.title_color)
        )
    }
}

@Composable
fun VideoCard(imageRes: Int, courseName: String) {
    Card(
        shape = RoundedCornerShape(25.dp),
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 4.dp
            ),
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.padding(top = 10.dp).size(400.dp,150.dp)
            )
            Text(
                text = "Kurs Adı : $courseName",
                modifier = Modifier.padding(start=20.dp,top=8.dp,bottom=8.dp,end=10.dp),
                color = colorResource(id = R.color.body_text_color)
            )
        }
    }
}
@Composable
fun MenuScreen(onMenuItemClick: (String) -> Unit) {
    val menuItems = listOf(
        "Favorilerim",
        "İndirilenler",
        "Çıkış Yap"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        menuItems.forEach { item ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onMenuItemClick(item) },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}