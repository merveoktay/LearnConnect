package com.example.learnconnect.repositories

import com.example.learnconnect.initialize.CourseCategoryProvider
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.Video
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseCategoryProvider: CourseCategoryProvider
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
        return courseCategoryProvider.getCategories()
    }
    suspend fun getCourses(): List<Course> {
        return courseCategoryProvider.getCourses()
    }
    suspend fun getCourse(courseTypeId: Int): Course {
        return courseCategoryProvider.getCourse(courseTypeId)
    }
    suspend fun getVideos(): List<Video> {
        return courseCategoryProvider.getVideos()
    }
    suspend fun getCoursesByType(courseTypeId: Int): List<Course> {
        return courseCategoryProvider.getCoursesByType(courseTypeId)
    }
    suspend fun getVideosByCourseId(courseId: Int): List<Video> {
        return courseCategoryProvider.getVideosByCourse(courseId)
    }
}