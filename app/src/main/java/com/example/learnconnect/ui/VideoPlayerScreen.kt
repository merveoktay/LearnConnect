package com.example.learnconnect.ui

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.learnconnect.utils.CacheManager
import com.example.learnconnect.utils.PreferencesManager
import com.example.learnconnect.utils.PreferencesManager.getVideoProgress
import com.example.learnconnect.utils.PreferencesManager.saveVideoProgress
import com.example.learnconnect.viewModels.CourseViewModel

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
            .apply {
                addListener(object : Player.Listener {
                    @Deprecated("Deprecated in Java")
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        if (playbackState == Player.STATE_READY && playWhenReady) {
                            val currentPosition = currentPosition
                            saveVideoProgress(context, videoId, currentPosition)
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onPositionDiscontinuity(reason: Int) {
                        val currentPosition = currentPosition
                        saveVideoProgress(context, videoId, currentPosition)
                    }

                })
            }
    }

    val url = PreferencesManager.getVideoLink(context).toUri()
    val mediaItem = MediaItem.fromUri(url)
    val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
        .createMediaSource(mediaItem)

    val video by courseViewModel.video.collectAsState()
    val progress = getVideoProgress(context, videoId)

    Log.d("Video Progress", "Video ID: $videoId, Progress: $progress")

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
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
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

                    if (progress > 0) {
                        exoPlayer.seekTo(progress)
                    }

                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }
            )
        }

        DisposableEffect(context) {
            onDispose {
                val currentPosition = exoPlayer.currentPosition
                saveVideoProgress(context, videoId, currentPosition)

                exoPlayer.release()
                CacheManager.getSimpleCache(context).release()

            }
        }
    }
}

