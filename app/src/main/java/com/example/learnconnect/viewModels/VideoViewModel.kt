package com.example.learnconnect.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.models.Video
import com.example.learnconnect.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val videoRepository: VideoRepository) :
    ViewModel() {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> get() = _videos

    private val _video = MutableStateFlow<Video>(Video(0, "", "", 0,0,""))
    val video: StateFlow<Video> get() = _video
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _filteredVideos = MutableStateFlow<List<Video>>(emptyList())
    val filteredVideos: StateFlow<List<Video>> get() = _filteredVideos

    fun loadVideos() {
        viewModelScope.launch {
            try {
                videoRepository.initializeAllVideo()
                val allVideos = videoRepository.getVideos()
                _videos.value = allVideos
                filterVideos()
            } catch (e: Exception) {
                Log.e("VideoViewModel", "Error loading videos: ${e.message}")
            }
        }
    }
    fun getVideosForCourse(courseId: Int) {
        viewModelScope.launch {
            val videoList = videoRepository.getVideosByCourseId(courseId)
            _videos.value = videoList
        }
    }
    private fun filterVideos() {
        val query = searchQuery.value
        val videos = _videos.value
        _filteredVideos.value = videos.filter { video ->
            video.title.contains(query, ignoreCase = true)
        }
    }
    fun getVideoDetails(videoId: Int) {
        viewModelScope.launch {
            val fetchedVideo = videoRepository.getVideoById(videoId)
            _video.value = fetchedVideo
        }
    }
}