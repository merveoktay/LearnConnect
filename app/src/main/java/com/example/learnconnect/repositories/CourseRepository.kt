package com.example.learnconnect.repositories

import android.util.Log
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.initialize.CourseCategoryProvider
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.UserCourse
import com.example.learnconnect.models.UserFavoriteCourse
import com.example.learnconnect.models.Video
import javax.inject.Inject

class CourseRepository @Inject constructor(private val courseDao: CourseDao,
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
        Log.d("categories", courseDao.getAllCourseTypes().toString())
        return courseDao.getAllCourseTypes()
    }
    suspend fun getCourses(): List<Course> {
        Log.d("course", courseDao.getAllCourse().toString())
        return courseDao.getAllCourse()
    }
    suspend fun getCourse(courseId:Int): Course {
        Log.d("getCourse", courseDao.getAllCourse().toString())
        return courseDao.getCourse(courseId)
    }
    suspend fun getVideos(): List<Video> {
        Log.d("video", courseDao.getAllVideos().toString())
        return courseDao.getAllVideos()
    }
    suspend fun getUserCourses(userId:Int): List<Course> {
        Log.d("course", courseDao.getUserCourses(userId).toString())
        return courseDao.getUserCourses(userId)
    }
    suspend fun getCoursesByType(courseTypeId: Int): List<Course> {
        return courseDao.getAllCoursesByType(courseTypeId)
    }
    suspend fun getVideosByCourseId(courseId: Int): List<Video> {
        return courseDao.getVideosByCourse(courseId)
    }
    suspend fun saveUserCourse( userId: Int,courseId: Int) {
        val userCourse=UserCourse(user_id = userId, course_id = courseId)
        return courseDao.insertUserCourse(userCourse =userCourse)
    }

    suspend fun isUserEnrolled(userId: Int, courseId: Int): Boolean {
        return courseDao.isUserEnrolled(userId, courseId)
    }
    suspend fun getVideoById(videoId: Int): Video {
        return courseDao.getVideoById(videoId)
    }
    suspend fun saveUserFavoriteCourse( userId: Int,courseId: Int) {
        val userFavoriteCourse= UserFavoriteCourse(user_id = userId, course_id = courseId)
        return courseDao.insertUserFavoriteCourse(userFavoriteCourse =userFavoriteCourse)
    }
    suspend fun isUserFavorite(userId: Int, courseId: Int): Boolean {
        return courseDao.isUserFavorite(userId, courseId)
    }
    suspend fun getUserFavoriteCourses(userId:Int): List<Course> {
        Log.d("course", courseDao.getUserCourses(userId).toString())
        return courseDao.getUserFavoriteCourses(userId)
    }
}