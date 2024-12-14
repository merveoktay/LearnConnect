package com.example.learnconnect.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.learnconnect.ui.components.CourseCards
import com.example.learnconnect.utils.PreferencesManager
import com.example.learnconnect.viewModels.CourseViewModel

@Composable
fun FavoriteScreen(
    onNavigateToCourse: (Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    courseViewModel: CourseViewModel
) {

    Scaffold(
        topBar = {
            FavoriteCourseTopBar(onNavigateToProfile)
        },
        bottomBar = {
        },
        content = { innerPadding ->
            FavoriteCourseContent(
                modifier = Modifier.padding(innerPadding),
                courseViewModel = courseViewModel,
                onNavigateToCourse = onNavigateToCourse
            )
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteCourseTopBar(onNavigateToProfile: () -> Unit) {
    TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
        title = {
            Text(
                text = "My Favorite Courses",
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigateToProfile() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        actions = {}
    )
}
@Composable
fun FavoriteCourseContent(
    modifier: Modifier = Modifier,
    courseViewModel: CourseViewModel,
    onNavigateToCourse: (Int) -> Unit
) {
    val userId= PreferencesManager.getUserId(context = LocalContext.current)
    val courseId by remember { mutableStateOf<Int?>(null) }
    val userFavoriteCourses by courseViewModel.userFavoriteCourses.collectAsState()

    LaunchedEffect(userId,courseId) {
        courseViewModel.getUserFavoriteCourses(userId)
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
            items(userFavoriteCourses) { userCourse ->
                val currentCourseId = userCourse.id
                Log.d("UserCourse", currentCourseId.toString())


                Log.d("UserCourseName", userCourse.name )

                CourseCards(
                    imageUrl = userCourse.course_image,
                    courseName = userCourse.name,
                    courseId = userCourse.id,
                    onNavigateToCourse
                )
            }
        }
    }
}
