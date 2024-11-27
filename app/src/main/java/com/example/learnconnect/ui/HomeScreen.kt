package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.VideoViewModel

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToVideo: () -> Unit, videoViewModel: VideoViewModel,
) {
    LaunchedEffect(Unit) {
        videoViewModel.loadCategories()
        videoViewModel.loadCourses()
    }
    val searchQuery by videoViewModel.searchQuery.observeAsState("") // Arama sorgusunu ViewModel'den al

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            HomeBottomBar(onNavigateToProfile)
        },
        content = { innerPadding ->
            HomeContent(
                modifier = Modifier.padding(innerPadding), videoViewModel,
                onNavigateToVideo
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
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
                Text(text = "LearnConnect", color = colorResource(id = R.color.title_color))
            }
        },
        actions = {

        }
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    videoViewModel: VideoViewModel,
    onNavigateToVideo: () -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<Int?>(null) } // Seçili kategori ID'si
    val categories by videoViewModel.categories.observeAsState(emptyList())
    val courses by videoViewModel.courses.observeAsState(emptyList())


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

            items(categories) { category ->
                Chip(
                    text = category.name,
                    onClick = { selectedCategory = category.id }
                )
            }
        }
        val filteredCourses = selectedCategory?.let { categoryId ->
            videoViewModel.getCoursesByCategory(categoryId)
        } ?: courses
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredCourses) { course ->
                VideoCard(
                    imageUrl = course.course_image,
                    courseName = course.name, onNavigateToVideo
                )
            }
        }
    }
}

@Composable
fun HomeBottomBar(onNavigateToProfile: () -> Unit) {
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
                onClick = { onNavigateToProfile() }
            )
        }
    }
}

@Composable
fun Chip(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .wrapContentSize(), // Adjusts size based on the content (Text)
        color = Color.LightGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center, // Center the content
            modifier = Modifier.wrapContentSize() // Ensure the Box sizes based on the content
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp),
                color = colorResource(id = R.color.title_color)
            )
        }
    }
}

@Composable
fun VideoCard(imageUrl: String, courseName: String, onNavigateToVideo: () -> Unit) {
    Card(
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxSize(1f)
            .clickable {
                onNavigateToVideo() // Tıklama gerçekleştiğinde bu fonksiyon çağrılacak
            }
    ) {
        Column {
            AsyncImage(
                model = imageUrl,  // Görsel URL'si
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()  // Görselin genişliğini kartla uyumlu yap
                    .height(150.dp)
                    .clip(RoundedCornerShape(32.dp)), // Yüksekliği belirleyin
            )
            Text(
                text = courseName,
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp, end = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = colorResource(id = R.color.title_color)
            )
        }
    }
}


