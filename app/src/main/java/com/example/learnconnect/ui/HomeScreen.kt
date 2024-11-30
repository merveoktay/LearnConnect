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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.CourseViewModel

@Composable
fun HomeScreen(
    onNavigateToCourse: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToCourses: () -> Unit,
    courseViewModel: CourseViewModel,
) {
    LaunchedEffect(Unit) {
        courseViewModel.loadCategories()
        courseViewModel.loadCourses()
    }
    Scaffold(
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            HomeBottomBar(onNavigateToProfile, onNavigateToCourses)
        },
        content = { innerPadding ->
            HomeContent(
                modifier = Modifier.padding(innerPadding),
                courseViewModel,
                onNavigateToCourse
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.xsmall_brand_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "LearnConnect",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        actions = {}
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    courseViewModel: CourseViewModel,
    onNavigateToCourse: (Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<Int?>(null) }
    val categories by courseViewModel.categories.observeAsState(emptyList())
    val courses by courseViewModel.courses.observeAsState(emptyList())

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
            item {
                Chip(
                    text = "All Courses",
                    onClick = { selectedCategory = null }
                )
            }
            items(categories) { category ->
                Chip(
                    text = category.name,
                    onClick = { selectedCategory = category.id }
                )
            }
        }
        val filteredCourses = selectedCategory?.let { categoryId ->
            courseViewModel.getCoursesByCategory(categoryId)
        } ?: courses
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredCourses) { course ->
                VideoCard(
                    imageUrl = course.course_image,
                    courseName = course.name,
                    onNavigateToCourse,
                    course.id
                )
            }
        }
    }
}

@Composable
fun HomeBottomBar(onNavigateToProfile: () -> Unit, onNavigateToCourses: () -> Unit) {
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
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = false,
                onClick = { onNavigateToCourses() }
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
            .wrapContentSize(),
        color = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun VideoCard(
    imageUrl: String,
    courseName: String,
    onNavigateToCourse: (Int) -> Unit,
    courseId: Int,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToCourse(courseId)
            }
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = courseName,
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp, end = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onSurface // Use onSurface color
            )
        }
    }
}
