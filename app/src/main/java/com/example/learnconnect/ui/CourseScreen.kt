package com.example.learnconnect.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.learnconnect.utils.PreferencesManager
import com.example.learnconnect.R
import com.example.learnconnect.models.Video
import com.example.learnconnect.viewModels.CourseViewModel
import com.example.learnconnect.ui.components.PlayIconWithCircle
import com.example.learnconnect.viewModels.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    onNavigateToVideoPlayer: (Int) -> Unit,
    navController: NavController,
    courseId: Int,
) {
    val videoViewModel: VideoViewModel = hiltViewModel()
    Log.d("Course Id", courseId.toString())
    val context = LocalContext.current
    val videos by videoViewModel.videos.collectAsState()
    val course by viewModel.course.collectAsState()
    val userId = PreferencesManager.getUserId(context)
    val isUserEnrolled by viewModel.isEnrolled.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    LaunchedEffect(courseId) {
        videoViewModel.loadVideos()
        viewModel.getCourse(courseId)
        viewModel.isUserEnrolled(userId, courseId)
        viewModel.isUserFavorite(userId, courseId)
    }

    course.let { Log.d("Course Data", it.name) }

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
                title = {
                    Text(
                        text = "My Favorite Courses",
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                },
                actions = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isUserEnrolled) {
                            IconButton(onClick = {
                                viewModel.saveUserCourse(userId = userId, courseId = courseId)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus_icon),
                                    contentDescription = "Add to Courses",
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.size(60.dp)
                                )
                            }

                        }


                        IconButton(
                            onClick = {
                                if (!isFavorite) {
                                    viewModel.saveUserFavoriteCourse(
                                        userId = userId,
                                        courseId = courseId
                                    )
                                } else {
                                    viewModel.removeCourseFromFavorites(
                                        userId = userId,
                                        courseId = courseId
                                    )
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isFavorite) R.drawable.favorite_icon else R.drawable.unfavorite_icon
                                ),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .size(30.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(videos.filter { it.course_id == courseId }) { video ->
                    VideoCard(
                        isUserEnrolled = isUserEnrolled,
                        imageUrl = course.course_image,
                        video = video,
                        onNavigateToVideoPlayer = onNavigateToVideoPlayer
                    )
                }
            }
        }
    }
}

@Composable
fun VideoCard(
    isUserEnrolled: Boolean,
    imageUrl: String,
    video: Video,
    onNavigateToVideoPlayer: (Int) -> Unit,
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (isUserEnrolled) {
                    PreferencesManager.clearVideoLink(context)
                    PreferencesManager.saveVideoLink(context = context, video.url)
                    Log.d("Video Card içinde video url ", video.id.toString())
                    video.id?.let { onNavigateToVideoPlayer(it) }
                } else {
                    showDialog = true
                }
            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                if (isUserEnrolled) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        PlayIconWithCircle()
                    }
                }
            }
            Text(
                text = video.title,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Enrollment Required") },
            text = { Text("To watch the course video, you need to enroll first.") }
        )
    }
}
