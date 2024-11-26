package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "courses",
    foreignKeys = [
        ForeignKey(
            entity = CourseType::class,
            parentColumns = ["id"],
            childColumns = ["course_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val course_type_id: Int // Foreign key to CourseType
)