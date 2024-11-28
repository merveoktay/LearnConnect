package com.example.learnconnect.repositories

import com.example.learnconnect.initialize.CourseCategoryProvider
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.Video
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseCategoryProvider: CourseCategoryProvider  // Yeni Provider'ı Enjekte Et
) {
    suspend fun initializeAllCategories() {
        courseCategoryProvider.initializeCategories()
    }
    suspend fun initializeAllCourses() {
        courseCategoryProvider.initializeCourses()
    }
    suspend fun initializeAllVideo() {
        courseCategoryProvider.initializeVideos()
    }
    suspend fun getCategories(): List<CourseType> {
        return courseCategoryProvider.getCategories()  // Kategorileri döndür
    }
    suspend fun getCourses(): List<Course> {
        return courseCategoryProvider.getCourses()  // Kategorileri döndür
    }
    suspend fun getVideos(): List<Video> {
        return courseCategoryProvider.getVideos()  // Kategorileri döndür
    }
    suspend fun getCoursesByType(courseTypeId: Int): List<Course> {
        return courseCategoryProvider.getCoursesByType(courseTypeId)
    }
}