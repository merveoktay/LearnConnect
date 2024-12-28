package com.example.learnconnect.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnconnect.models.Video
import com.example.learnconnect.models.VideoProgress
@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    suspend fun getAllVideos(): List<Video>
    @Query("SELECT * FROM videos WHERE course_id = :courseId")
    suspend fun getVideosByCourse(courseId: Int): List<Video>
    @Query("SELECT * FROM videos WHERE id = :videoId LIMIT 1")
    suspend fun getVideoById(videoId: Int): Video
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(videoProgress: VideoProgress)

    @Query("UPDATE video_progress SET last_position = :lastPosition WHERE user_id = :userId AND video_id = :videoId")
    suspend fun saveCurrentPosition(userId:Int,videoId: Int,lastPosition:Long)

    @Query("UPDATE video_progress SET is_watched = 1 WHERE user_id = :userId AND video_id = :videoId")
    suspend fun updateVideoIsWatched(userId: Int,videoId: Int)

    @Query("SELECT * FROM video_progress WHERE user_id = :userId AND video_id = :videoId")
    suspend fun getProgressByUserAndVideo(userId: Int, videoId: Int): VideoProgress?

    // Kullanıcının belirli bir video için izlenme durumunu güncelle
    @Query("UPDATE video_progress SET is_watched = 1 WHERE user_id = :userId AND video_id = :videoId")
    suspend fun updateIsWatchedStatus(userId: Int, videoId: Int)

    // Kullanıcının bir videodaki son pozisyonunu kaydet
    @Query("UPDATE video_progress SET last_position = :lastPosition WHERE user_id = :userId AND video_id = :videoId")
    suspend fun saveLastPosition(userId: Int, videoId: Int, lastPosition: Long)

    // Kullanıcının bir videodaki son pozisyonunu al
    @Query("SELECT last_position FROM video_progress WHERE user_id = :userId AND video_id = :videoId")
    suspend fun getLastPosition(userId: Int, videoId: Int): Long?

    // Kullanıcının bir kurstaki son izlenen videonun ID'sini getir
    @Query("SELECT video_id FROM video_progress WHERE user_id = :userId AND is_last_watched = 1")
    suspend fun getLastWatchedVideoId(userId: Int): Int?

    // Kullanıcının bir kurstaki son izlenen videoyu güncelle
    @Query(
        """
        UPDATE video_progress
        SET is_last_watched = CASE
            WHEN video_id = :videoId THEN 1
            ELSE 0
        END
        WHERE user_id = :userId
        """
    )
    suspend fun updateIsLastWatched(userId: Int, videoId: Int)

    // Video için indirilen dosyanın yolunu kaydet
    @Query("UPDATE video_progress SET is_downloaded = 1 WHERE user_id = :userId AND video_id = :videoId")
    suspend fun updateIsDownloaded(userId: Int, videoId: Int)

    // Belirli bir kullanıcının tüm video ilerlemelerini getir
    @Query("SELECT * FROM video_progress WHERE user_id = :userId")
    suspend fun getAllProgressByUser(userId: Int): List<VideoProgress>
}