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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    viewModel: VideoViewModel = hiltViewModel(),
    onFavoriteClick: () -> Unit,
    onJoinClick: () -> Unit,
    isUserEnrolled: Boolean,
    courseId: Int,
) {

Log.d("Course Id", courseId.toString())
    val videos by viewModel.videos.observeAsState(emptyList())
    val course by  viewModel.course.observeAsState()
    LaunchedEffect(courseId) {
        viewModel.loadVideos()
        viewModel.getVideosByCourse(courseId)
    }
    course?.let { Log.d("Course Data", it.name) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    course?.let {
                        Text(
                            text = it.name,
                            color = colorResource(id = R.color.title_color)
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Next",
                        tint = colorResource(id = R.color.title_color),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 15.dp)
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.unfavorite_icon),
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .clickable { onFavoriteClick() }
                            .size(50.dp)
                            .padding(top = 15.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black
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
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(videos.filter { it.course_id == courseId }) { video -> // Filtreleme işlemi burada yapılır
                    course?.let {
                        VideoCard(
                            imageUrl = course!!.course_image,
                            videoName = it.name,
                        )
                    }
                }
            }

            if (!isUserEnrolled) {
                Button(
                    onClick = { onJoinClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.brand_color))
                ) {
                    Text(text = "Join The Course", color = colorResource(id = R.color.title_color))
                }
            }
        }
    }
}

@Composable
fun VideoCard(imageUrl: String, videoName: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
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
                text = videoName,
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        }
    }
}
