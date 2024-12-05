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
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.learnconnect.CacheManager
import com.example.learnconnect.PreferencesManager
import com.example.learnconnect.viewModels.CourseViewModel
import java.io.File

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoId: Int,
    navController: NavController,
    courseViewModel: CourseViewModel
) {

    LaunchedEffect(Unit) {
        courseViewModel.getVideoDetails(videoId = videoId)
    }
    val context = LocalContext.current
    val cache = CacheManager.getSimpleCache(context)
    val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(cache)
        .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context))

    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(
            DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
            50000,
            1500,
            2000
        )
        .build()

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .build()
    }
    val  url=PreferencesManager.getVideoLink(context).toUri()
    val mediaItem = MediaItem.fromUri(url)
    val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
        .createMediaSource(mediaItem)
    val video by courseViewModel.video.observeAsState()
    Log.d("urlll için id", PreferencesManager.getVideoLink(context))
    Log.d("urlll urlll", video?.url.toString())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = video?.title ?: "", color = MaterialTheme.colorScheme.onSurface)
                        Log.d("Video Title video player da", video?.title ?: "")},
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
                update = {
                    exoPlayer.setMediaSource(mediaSource)
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }
            )
        }

        DisposableEffect(context) {
            onDispose {
                exoPlayer.release()
                CacheManager.getSimpleCache(context).release()
            }
        }
    }
}
