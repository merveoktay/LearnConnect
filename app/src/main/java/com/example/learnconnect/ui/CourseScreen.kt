package com.example.learnconnect.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    onNavigateToVideoPlayer: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    navController: NavController,
    courseId: Int,
) {
    Log.d("Course Id", courseId.toString())
    val context = LocalContext.current
    val videos by viewModel.videos.collectAsState()
    val course by viewModel.course.collectAsState()
    val userId = PreferencesManager.getUserId(context)
    val isUserEnrolled by viewModel.isEnrolled.collectAsState()
    LaunchedEffect(courseId) {
        viewModel.loadVideos()
        viewModel.getCourse(courseId)
        viewModel.isUserEnrolled(userId, courseId)
    }

    course.let { Log.d("Course Data", it.name) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = course.name,
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
                    if (!isUserEnrolled) {
                        IconButton(onClick = {
                            Log.d("CourseScreen save course", courseId.toString())
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
                    Icon(
                        painter = painterResource(id = R.drawable.unfavorite_icon),
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .clickable { onFavoriteClick() }
                            .size(50.dp)
                            .padding(top = 15.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
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
    var showDialog by remember { mutableStateOf(false) } // Popup kontrolü için bir state

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
                    onNavigateToVideoPlayer(video.id)
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
@Composable
fun PlayIconWithCircle() {
    val context = LocalContext.current

    val brandColor = remember {
        Color(ContextCompat.getColor(context, R.color.brand_color))
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(64.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = brandColor.copy(alpha = 0.7f),
                style = Stroke(width = 4.dp.toPx())
            )
        }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play Icon",
            tint = brandColor.copy(alpha = 0.7f),
            modifier = Modifier.size(48.dp)
        )
    }
}