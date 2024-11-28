package com.example.learnconnect.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.Video
import com.example.learnconnect.repositories.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val courseRepository: CourseRepository) : ViewModel() {

    // Kategoriler
    private val _categories = MutableLiveData<List<CourseType>>()
    val categories: LiveData<List<CourseType>> get() = _categories


    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos

    private val _filteredVideos = MediatorLiveData<List<Video>>()
    val filteredVideos: LiveData<List<Video>> get() = _filteredVideos

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> get() = _searchQuery


    private val _filteredCourses = MutableLiveData<List<Course>>()
    val filteredCourses: LiveData<List<Course>> get() = _filteredCourses

    init {
        loadCategories()
        loadCourses()
        loadVideos()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                courseRepository.initializeAllCategories()
                _categories.value = courseRepository.getCategories()
            } catch (e: Exception) {
                Log.e("VideoViewModel", "Error loading categories: ${e.message}")
            }
        }
    }

    fun loadCourses() {
        viewModelScope.launch {
            try {
                courseRepository.initializeAllCourses()
                val allCourses = courseRepository.getCourses()
                _courses.value = allCourses
                filterCourses() // Kurslar yüklendiğinde filtreleme yap
            } catch (e: Exception) {
                Log.e("VideoViewModel", "Error loading courses: ${e.message}")
            }
        }
    }
    fun loadVideos() {
        viewModelScope.launch {
            try {
                courseRepository.initializeAllVideo()
                val allVideos = courseRepository.getVideos()
                _videos.value = allVideos
                filterVideos() // Videolar yüklendiğinde filtreleme yap
            } catch (e: Exception) {
                Log.e("VideoViewModel", "Error loading videos: ${e.message}")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun filterCourses() {
        val query = searchQuery.value.orEmpty()
        val courses = _courses.value.orEmpty()
        (filteredCourses as MediatorLiveData).value = courses.filter { course ->
            course.name.contains(query, ignoreCase = true)
        }
    }
    fun getCoursesByCategory(categoryId: Int): List<Course> {
        return _courses.value?.filter { it.course_type_id == categoryId } ?: emptyList()
    }

    private fun filterVideos() {
        val query = searchQuery.value.orEmpty()
        val videos = _videos.value.orEmpty()
        _filteredVideos.value = videos.filter { video ->
            video.title.contains(query, ignoreCase = true)
        }
    }
}