package com.example.learnconnect.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.User
import com.example.learnconnect.models.UserCourse
import com.example.learnconnect.models.UserFavoriteCourse
import com.example.learnconnect.models.Video
import com.example.learnconnect.models.VideoProgress

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseType(courseType: CourseType)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(videos: Video)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserCourse(userCourse: UserCourse)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserFavoriteCourse(userFavoriteCourse: UserFavoriteCourse)

    @Query("SELECT * FROM courses WHERE course_type_id = :courseTypeId")
    suspend fun getAllCoursesByType(courseTypeId: Int): List<Course>
    @Query("SELECT * FROM courses ")
    suspend fun getAllCourse(): List<Course>

    @Query("SELECT * FROM courses  WHERE id = :courseId")
    suspend fun getCourse(courseId: Int): Course

    @Query("SELECT * FROM videos")
    suspend fun getAllVideos(): List<Video>

    @Query("SELECT * FROM videos WHERE course_id = :courseId")
    suspend fun getVideosByCourse(courseId: Int): List<Video>

    @Query("SELECT * FROM course_types")
    suspend fun getAllCourseTypes(): List<CourseType>

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(email: String, password: String): User?

    @Query(
        """
        SELECT c.* 
        FROM user_courses uc
        INNER JOIN courses c ON uc.course_id = c.id
        WHERE uc.user_id = :userId
        """
    )
    suspend fun getUserCourses(userId: Int): List<Course>

    @Query(
        """
        SELECT c.* 
        FROM user_favorite_courses uc
        INNER JOIN courses c ON uc.course_id = c.id
        WHERE uc.user_id = :userId
        """
    )
    suspend fun getUserFavoriteCourses(userId: Int): List<Course>

    @Query("SELECT COUNT(*) FROM user_favorite_courses WHERE user_id = :userId AND course_id = :courseId")
    suspend fun isUserFavorite(userId: Int, courseId: Int): Boolean

    @Query("SELECT COUNT(*) FROM user_courses WHERE user_id = :userId AND course_id = :courseId")
    suspend fun isUserEnrolled(userId: Int, courseId: Int): Boolean

    @Query("SELECT * FROM videos WHERE id = :videoId LIMIT 1")
    suspend fun getVideoById(videoId: Int): Video

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(videoProgress: VideoProgress)

    @Query("SELECT progress FROM video_progress WHERE videoId = :videoId LIMIT 1")
    suspend fun getProgress(videoId: Int): Long
}