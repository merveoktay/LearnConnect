package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "user_favorite_courses",
    primaryKeys = ["user_id", "course_id"],
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"]),
        ForeignKey(entity = Course::class, parentColumns = ["id"], childColumns = ["course_id"])
    ]
)
data class UserFavoriteCourse(
    val user_id: Int,
    val course_id: Int
)