package com.example.learnconnect.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.dao.UserDao
import com.example.learnconnect.dao.VideoDao
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.User
import com.example.learnconnect.models.UserCourse
import com.example.learnconnect.models.UserFavoriteCourse
import com.example.learnconnect.models.Video
import com.example.learnconnect.models.VideoProgress


@Database(
    entities = [User::class, CourseType::class, Course::class, Video::class, UserCourse::class, UserFavoriteCourse::class, VideoProgress::class],
    version = 9,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun videoDao():VideoDao
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `courses_temp` (`id` INTEGER, `name` TEXT, `course_image` TEXT, `course_type_id` INTEGER, PRIMARY KEY(`id`))")

                database.execSQL("INSERT INTO `courses_temp` (`id`, `name`, `course_image`, `course_type_id`) SELECT `id`, `name`, `course_image`, `course_type_id` FROM `courses`")

                database.execSQL("DROP TABLE `courses`")

                database.execSQL("ALTER TABLE `courses_temp` RENAME TO `courses`")
            }
        }
    }

}