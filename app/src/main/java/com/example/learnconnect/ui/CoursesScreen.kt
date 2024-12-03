package com.example.learnconnect.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learnconnect.PreferencesManager
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.CourseViewModel

@Composable
fun CoursesScreen(
    onNavigateToCourse: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToHome: () -> Unit,
    courseViewModel: CourseViewModel,
) {
    LaunchedEffect(Unit) {
        courseViewModel.loadCategories()
        courseViewModel.loadCourses()
    }
    Scaffold(
        topBar = {
            CourseTopBar()
        },
        bottomBar = {
            CoursesBottomBar(onNavigateToProfile, onNavigateToHome)
        },
        content = { innerPadding ->
            CoursesContent(
                modifier = Modifier.padding(innerPadding),
                courseViewModel = courseViewModel,
                onNavigateToCourse = onNavigateToCourse
            )
        }
    )
}

@Composable
fun CoursesContent(
    modifier: Modifier = Modifier,
    courseViewModel: CourseViewModel,
    onNavigateToCourse: (Int) -> Unit,
) {

    val userId=PreferencesManager.getUserId(context = LocalContext.current)
    var courseId by remember { mutableStateOf<Int?>(null) }

    val userCourses by courseViewModel.usercourses.observeAsState(initial = emptyList())
    val course by courseViewModel.course.observeAsState()

    LaunchedEffect(userId,courseId) {
        courseViewModel.getUserCourses(userId)
        courseId?.let { courseViewModel.getCourse(it) }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            userCourses.let {courseList->
                items(courseList) {userCourse->
                   courseId=userCourse.course_id
                   Log.d("UserCoursee",userCourse.course_id.toString())
                    Log.d("UserCoursee", course?.name ?: "")
                    CoursesVideoCard(
                        imageUrl = course?.course_image ?: "",
                        courseName = course?.name ?: "",
                        courseId = course?.id ?:0 , onNavigateToCourse
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseTopBar() {
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
                    text = "My Courses",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        actions = {}
    )
}

@Composable
fun CoursesBottomBar(onNavigateToProfile: () -> Unit, onNavigateToHome: () -> Unit) {
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
                        painter = painterResource(id = R.drawable.selected_course_icon),
                        contentDescription = "My Courses",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = true,
                onClick = {}
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unselected_home_icon),
                        contentDescription = "Home",
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = false,
                onClick = { onNavigateToHome() }
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
fun CoursesVideoCard(
    imageUrl: String,
    courseName: String,
    courseId: Int,
    onNavigateToCourse: (Int) -> Unit,
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
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
