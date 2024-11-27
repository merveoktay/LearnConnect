package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_types")
data class CourseType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Varsayılan değeri kullanabilirsiniz
    val name: String
)
