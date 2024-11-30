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
class CourseViewModel @Inject constructor(private val courseRepository: CourseRepository) : ViewModel() {

    private val _categories = MutableLiveData<List<CourseType>>()
    val categories: LiveData<List<CourseType>> get() = _categories


    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses

    private val _course = MutableLiveData<Course>()
    val course: LiveData<Course> = _course

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> get() = _videos

    private val _video = MutableLiveData<Video>()
    val video: LiveData<Video> get() = _video

    private val _filteredVideos = MediatorLiveData<List<Video>>()
    val filteredVideos: LiveData<List<Video>> get() = _filteredVideos

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _userCourseSaved = MutableLiveData<Boolean>()
    val userCourseSaved: LiveData<Boolean> get() = _userCourseSaved

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
        return _courses.value?.filter { it.course_type_id == categoryId } ?: emptyList()
    }
    fun getVideosForCourse(courseId: Int) {
        viewModelScope.launch {
            val videoList = courseRepository.getVideosByCourseId(courseId)
            _videos.postValue(videoList)
        }
    }
    fun getVideosByCourse(courseId: Int) {
        viewModelScope.launch {
            val fetchedCourse = courseRepository.getCourse(courseId)
            _course.postValue(fetchedCourse)
        }
    }
    fun getCourse(courseId: Int): Course? {
        return _course.value?.takeIf { it.id == courseId }
    }

    private fun filterVideos() {
        val query = searchQuery.value.orEmpty()
        val videos = _videos.value.orEmpty()
        _filteredVideos.value = videos.filter { video ->
            video.title.contains(query, ignoreCase = true)
        }
    }
    fun saveUserCourse(courseId: Int, userId: Int) {
        viewModelScope.launch {
            try {

                courseRepository.saveUserCourse( userId,courseId)
                _userCourseSaved.postValue(true)
            } catch (e: Exception) {
                _userCourseSaved.postValue(false)
            }
        }
    }
    suspend fun isUserEnrolled(userId: Int, courseId: Int):Boolean {
        return courseRepository.isUserEnrolled(userId, courseId)
    }
    fun getVideoDetails(videoId: Int): LiveData<Video> {
        viewModelScope.launch {
            val fetchedVideo = courseRepository.getVideoById(videoId)
            _video.postValue(fetchedVideo)
        }
        return video
    }

}