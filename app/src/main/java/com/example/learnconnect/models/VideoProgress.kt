package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_progress")
data class VideoProgress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val videoId: Int,
    val progress: Long
)