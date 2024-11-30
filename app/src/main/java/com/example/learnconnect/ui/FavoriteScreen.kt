package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.CourseViewModel

@Composable
fun FavoriteScreen(
    onNavigateToCourses: () -> Unit,
    onNavigateToProfile: () -> Unit,
    courseViewModel: CourseViewModel,
) {
    LaunchedEffect(Unit) {
        courseViewModel.loadCategories()
        courseViewModel.loadCourses()
    }
    Scaffold(
        topBar = {
            FavoriteTopBar()
        },
        content = { innerPadding ->
            FavoriteContent(
                modifier = Modifier.padding(innerPadding), courseViewModel,
                onNavigateToCourses
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar() {
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
fun FavoriteContent(
    modifier: Modifier = Modifier,
    courseViewModel: CourseViewModel,
    onNavigateToVideo: () -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<Int?>(null) } // Seçili kategori ID'si
    val categories by courseViewModel.categories.observeAsState(emptyList())
    val courses by courseViewModel.courses.observeAsState(emptyList())


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
      /*  LazyRow(
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
        }*/
    }
}
/*
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
*/
