package com.example.learnconnect.repositories

import android.util.Log
import com.example.learnconnect.dao.VideoDao
import com.example.learnconnect.models.Video
import com.example.learnconnect.models.VideoProgress
import com.example.learnconnect.initialize.CourseCategoryProvider
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoDao: VideoDao,private val courseCategoryProvider: CourseCategoryProvider){
    suspend fun initializeAllVideo() {

        courseCategoryProvider.initializeVideos()
    }
    suspend fun getVideos(): List<Video> {
        Log.d("video", videoDao.getAllVideos().toString())
        return videoDao.getAllVideos()
    }
    suspend fun getVideosByCourseId(courseId: Int): List<Video> {
        return videoDao.getVideosByCourse(courseId)
    }
    suspend fun getVideoById(videoId: Int): Video {
        return videoDao.getVideoById(videoId)
    }

    // Kullanıcının belirli bir video için ilerleme kaydını getir
    suspend fun getProgressByUserAndVideo(userId: Int, videoId: Int): VideoProgress? {
        return videoDao.getProgressByUserAndVideo(userId, videoId)
    }

    // Videonun izlenme durumunu güncelle
    suspend fun updateIsWatched(userId: Int, videoId: Int) {
        videoDao.updateIsWatchedStatus(userId, videoId)
    }

    // Videonun son pozisyonunu kaydet
    suspend fun saveLastPosition(userId: Int, videoId: Int, lastPosition: Long) {
        videoDao.saveLastPosition(userId, videoId, lastPosition)
    }

    // Kullanıcının belirli bir videodaki son pozisyonunu getir
    suspend fun getLastPosition(userId: Int, videoId: Int): Long? {
        return videoDao.getLastPosition(userId, videoId)
    }

    // Kullanıcının izlediği son videoyu getir
    suspend fun getLastWatchedVideoId(userId: Int): Int? {
        return videoDao.getLastWatchedVideoId(userId)
    }

    // Kullanıcının izlediği son videoyu güncelle
    suspend fun updateIsLastWatched(userId: Int, videoId: Int) {
        videoDao.updateIsLastWatched(userId, videoId)
    }

    // İndirilen videonun durumunu ve dosya yolunu kaydet
    suspend fun markAsDownloaded(userId: Int, videoId: Int) {
        videoDao.updateIsDownloaded(userId, videoId)
    }

    // Kullanıcının tüm video ilerleme kayıtlarını getir
    suspend fun getAllProgressByUser(userId: Int): List<VideoProgress> {
        return videoDao.getAllProgressByUser(userId)
    }
}