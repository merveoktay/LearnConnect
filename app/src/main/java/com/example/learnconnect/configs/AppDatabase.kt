package com.example.learnconnect.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.dao.UserDao
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.User
import com.example.learnconnect.models.UserCourse
import com.example.learnconnect.models.UserFavoriteCourse
import com.example.learnconnect.models.Video
import javax.inject.Inject


@Database(
    entities = [User::class, CourseType::class, Course::class, Video::class, UserCourse::class, UserFavoriteCourse::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase :RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao


}