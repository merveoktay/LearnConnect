package com.example.learnconnect.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.Video
import com.example.learnconnect.repositories.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(private val courseRepository: CourseRepository) :
    ViewModel() {

    private val _categories = MutableStateFlow<List<CourseType>>(emptyList())
    val categories: StateFlow<List<CourseType>> get() = _categories

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> get() = _courses

    private val _userCourses = MutableStateFlow<List<Course>>(emptyList())
    val userCourses: StateFlow<List<Course>> get() = _userCourses

    private val _userFavoriteCourses = MutableStateFlow<List<Course>>(emptyList())
    val userFavoriteCourses: StateFlow<List<Course>> get() = _userFavoriteCourses

    private val _course = MutableStateFlow<Course>(Course(0,"","",0))
    val course: StateFlow<Course> = _course

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> get() = _videos

    private val _video = MutableStateFlow<Video>(Video(0,"","",0))
    val video: StateFlow<Video> get() = _video

    private val _isEnrolled = MutableStateFlow<Boolean>(false)
    val isEnrolled: StateFlow<Boolean> get() = _isEnrolled

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private val _filteredVideos = MutableStateFlow<List<Video>>(emptyList())
    val filteredVideos: StateFlow<List<Video>> get() = _filteredVideos

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery


    private val _filteredCourses = MutableStateFlow<List<Course>>(emptyList())
    val filteredCourses: StateFlow<List<Course>> get() = _filteredCourses


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
                filterCourses()
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
                filterVideos()
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
        _filteredCourses.value = courses.filter { course ->
            course.name.contains(query, ignoreCase = true)
        }
    }

    fun getCoursesByCategory(categoryId: Int): List<Course> {
        return _courses.value.filter { it.course_type_id == categoryId } ?: emptyList()
    }

    fun getVideosForCourse(courseId: Int) {
        viewModelScope.launch {
            val videoList = courseRepository.getVideosByCourseId(courseId)
            _videos.value=videoList
        }
    }

    fun getCourse(courseId: Int) {
        viewModelScope.launch {
            val fetchedCourse = courseRepository.getCourse(courseId)
            _course.value=fetchedCourse
        }
    }
    fun getUserCourses(userId: Int){
        viewModelScope.launch {
            try {
                val fetchedCourses = courseRepository.getUserCourses(userId)
                _userCourses.value=fetchedCourses
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Error fetching user courses: ${e.message}")
            }
        }


    }
    fun getUserFavoriteCourses(userId: Int){
        viewModelScope.launch {
            try {
                val fetchedCourses = courseRepository.getUserFavoriteCourses(userId)
                _userFavoriteCourses.value=fetchedCourses
            } catch (e: Exception) {
                Log.e("CourseViewModel", "Error fetching user favorite courses: ${e.message}")
            }
        }


    }

    private fun filterVideos() {
        val query = searchQuery.value.orEmpty()
        val videos = _videos.value.orEmpty()
        _filteredVideos.value = videos.filter { video ->
            video.title.contains(query, ignoreCase = true)
        }
    }

    fun saveUserCourse( userId: Int,courseId: Int) {
        viewModelScope.launch {
            try {
                courseRepository.saveUserCourse(userId, courseId)
                _isEnrolled.value=true

            } catch (e: Exception) {
                _isEnrolled.value=false
            }
        }
        Log.d("User saved",courseId.toString())

    }
    fun saveUserFavoriteCourse( userId: Int,courseId: Int) {
        viewModelScope.launch {
            try {
                courseRepository.saveUserFavoriteCourse(userId, courseId)
                _isFavorite.value=true

            } catch (e: Exception) {
                _isFavorite.value=false
            }
        }
        Log.d("User Favorite saved",courseId.toString())

    }

    fun isUserFavorite(userId: Int, courseId: Int){
        viewModelScope.launch {
            _isFavorite.value = courseRepository.isUserFavorite(userId, courseId)

        }
    }

    fun isUserEnrolled(userId: Int, courseId: Int){
        viewModelScope.launch {
            _isEnrolled.value = courseRepository.isUserEnrolled(userId, courseId)

        }
    }

    fun getVideoDetails(videoId: Int){
        viewModelScope.launch {
            val fetchedVideo = courseRepository.getVideoById(videoId)
            _video.value=fetchedVideo
        }
    }

}