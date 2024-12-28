package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "video_progress",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Video::class,
            parentColumns = ["id"],
            childColumns = ["video_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VideoProgress(
    @PrimaryKey(autoGenerate = true) val id:Int? =null,
    val video_id: Int,
    val user_id: Int,
    val last_position: Long = 0L,
    val is_watched: Boolean = false,
    val is_last_watched: Boolean = false,
    val is_downloaded: Boolean = false
    )