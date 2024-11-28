package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_types")
data class CourseType(
    @PrimaryKey val id: Int,
    val name: String
)
