package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "videos",
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Video(
    @PrimaryKey(autoGenerate = true) val id:Int? =null,
    val title: String,
    val url: String,
    val course_id: Int,
    val duration:Int,
    val file_path:String?
)