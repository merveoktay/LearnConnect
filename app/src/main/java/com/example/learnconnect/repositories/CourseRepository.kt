package com.example.learnconnect.repositories

import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.models.UserCourse
import com.example.learnconnect.models.UserFavoriteCourse

class CourseRepository(val courseDao: CourseDao) {
    suspend fun enrollInCourse(courseDao: CourseDao, userId: Int, courseId: Int) {
        val userCourse = UserCourse(user_id = userId, course_id = courseId)
        courseDao.insertUserCourse(userCourse)
    }
    suspend fun markCourseAsFavorite(courseDao: CourseDao, userId: Int, courseId: Int) {
        val userFavoriteCourse = UserFavoriteCourse(user_id = userId, course_id = courseId)
        courseDao.insertUserFavoriteCourse(userFavoriteCourse)
    }
}