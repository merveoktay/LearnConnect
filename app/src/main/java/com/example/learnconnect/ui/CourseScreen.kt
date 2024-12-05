package com.example.learnconnect.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.learnconnect.PreferencesManager
import com.example.learnconnect.R
import com.example.learnconnect.models.Video

import com.example.learnconnect.viewModels.CourseViewModel
import com.example.learnconnect.viewModels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel,
    onNavigateToVideoPlayer: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    navController: NavController,
    courseId: Int,
) {

    Log.d("Course Id", courseId.toString())
    val videos by viewModel.videos.observeAsState(emptyList())
    val course by viewModel.course.observeAsState()
    val userId = PreferencesManager.getUserId(context = LocalContext.current)
    val isUserEnrolled by viewModel.result.observeAsState()

    LaunchedEffect(courseId) {
        viewModel.loadVideos()
        viewModel.getCourse(courseId)
        viewModel.isUserEnrolled(userId  ,courseId)
    }
    course?.let { Log.d("Course Data", it.name) }

     Log.d("IS ENROLL", isUserEnrolled.toString())

    Log.d("USER ID", userId.toString())
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    course?.let {
                        Text(
                            text = it.name,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
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
                    if(isUserEnrolled == false) {
                        IconButton(onClick = {
                            viewModel.saveUserCourse(userId = userId, courseId = courseId)
                        }) {
                            Icon(
                                painter = painterResource(id =  R.drawable.plus_icon),
                                contentDescription = "Back",
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
                        tint =  MaterialTheme.colorScheme.onSecondary
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
                    course?.let {
                        VideoCard(
                            imageUrl = it.course_image,
                            video = video,
                            onNavigateToVideoPlayer = onNavigateToVideoPlayer
                        )



                    }
                }
            }
            Log.d("isssenrolled", isUserEnrolled.toString())
        }
    }
}

@Composable
fun VideoCard(
    imageUrl: String,
    video: Video,
    onNavigateToVideoPlayer: (Int) -> Unit,
) {
    val context =LocalContext.current
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                PreferencesManager.clearVideoLink(context)
                PreferencesManager.saveVideoLink(context = context, video.url)
                Log.d("Video Card içinde video url ",video.id.toString())
                onNavigateToVideoPlayer(video.id)

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
                text = video.title,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
