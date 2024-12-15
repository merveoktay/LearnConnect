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




    @Query("SELECT * FROM courses WHERE course_type_id = :courseTypeId")
    suspend fun getAllCoursesByType(courseTypeId: Int): List<Course>
    @Query("SELECT * FROM courses ")
    suspend fun getAllCourse(): List<Course>

    @Query("SELECT * FROM courses  WHERE id = :courseId")
    suspend fun getCourse(courseId: Int): Course

    @Query("SELECT * FROM course_types")
    suspend fun getAllCourseTypes(): List<CourseType>

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(email: String, password: String): User?




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserCourse(userCourse: UserCourse)
    @Query("SELECT COUNT(*) FROM user_courses WHERE user_id = :userId AND course_id = :courseId")
    suspend fun isUserEnrolled(userId: Int, courseId: Int): Boolean
    @Query(
        """
        SELECT c.* 
        FROM user_courses uc
        INNER JOIN courses c ON uc.course_id = c.id
        WHERE uc.user_id = :userId
        """
    )
    suspend fun getUserCourses(userId: Int): List<Course>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserFavoriteCourse(userFavoriteCourse: UserFavoriteCourse)
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

    @Query(
        """
        DELETE FROM user_favorite_courses
        WHERE user_id = :userId AND course_id = :courseId
        """
    )
    suspend fun removeCourseFromFavorites(userId: Int, courseId: Int)
}