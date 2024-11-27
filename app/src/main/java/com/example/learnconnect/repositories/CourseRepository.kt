package com.example.learnconnect.repositories

import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.models.UserCourse
import com.example.learnconnect.models.UserFavoriteCourse
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val courseDao: CourseDao  // courseDao burada inject ediliyor
) {
    suspend fun enrollInCourse(userId: Int, courseId: Int) {
        val userCourse = UserCourse(user_id = userId, course_id = courseId)
        courseDao.insertUserCourse(userCourse)  // courseDao burada kullanılıyor
    }

    suspend fun markCourseAsFavorite(userId: Int, courseId: Int) {
        val userFavoriteCourse = UserFavoriteCourse(user_id = userId, course_id = courseId)
        courseDao.insertUserFavoriteCourse(userFavoriteCourse)  // courseDao burada kullanılıyor
    }
}