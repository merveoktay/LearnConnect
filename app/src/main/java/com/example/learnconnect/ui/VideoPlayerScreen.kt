package com.example.learnconnect.ui


import android.util.Log
import androidx.media3.common.MediaItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.learnconnect.PreferencesManager
import com.example.learnconnect.viewModels.CourseViewModel
import okhttp3.HttpUrl.Companion.toHttpUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoId: Int,
    navController: NavController,
    courseViewModel: CourseViewModel
) {
    val context = LocalContext.current
    val exoPlayer = remember(context) { ExoPlayer.Builder(context).build() }

    val video by courseViewModel.video.observeAsState()
    Log.d("urlll için id", PreferencesManager.getVideoLink(context))
    val  url=PreferencesManager.getVideoLink(context).toUri()
    Log.d("urlll urlll", video?.url.toString())
    LaunchedEffect(Unit) {
        courseViewModel.getVideoDetails(videoId = 123)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = video?.title ?: "", color = MaterialTheme.colorScheme.onSurface) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Use surface color
                    titleContentColor = MaterialTheme.colorScheme.onSurface, // Text color on surface
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface // Action icon color
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        this.player = exoPlayer
                    }
                },
                update = { playerView ->
                    val mediaItem = MediaItem.fromUri(url)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }
            )
        }

        DisposableEffect(context) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}
