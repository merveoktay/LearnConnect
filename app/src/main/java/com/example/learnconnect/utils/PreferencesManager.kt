package com.example.learnconnect.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"

    private const val PREFS_VIDEO = "video_prefs"
    private const val KEY_VİDEO_LINK = "video_link"


    private fun getSharedPreferences(pref_name:String,context: Context): SharedPreferences {
        return context.getSharedPreferences(pref_name, Context.MODE_PRIVATE)
    }

    fun saveUserId(context: Context, userId: Int) {
        val prefs = getSharedPreferences(PREFS_NAME,context)
        prefs.edit().putInt(KEY_USER_ID, userId).apply()
    }


    fun getUserId(context: Context): Int {
        val prefs = getSharedPreferences(PREFS_NAME,context)
        return prefs.getInt(KEY_USER_ID, 0)
    }
    fun saveVideoLink(context: Context, video_link: String) {
        val prefs = getSharedPreferences(PREFS_VIDEO,context)
        prefs.edit().putString(KEY_VİDEO_LINK, video_link).apply()
    }

    fun getVideoLink(context: Context): String {
        val prefs = getSharedPreferences(PREFS_VIDEO,context)
        return prefs.getString(KEY_VİDEO_LINK,"").toString()
    }



    // userId'yi silme
    fun clearUserId(context: Context) {
        val prefs = getSharedPreferences(PREFS_NAME,context)
        prefs.edit().remove(KEY_USER_ID).apply()
    }
    fun clearVideoLink(context: Context) {
        val prefs = getSharedPreferences(PREFS_VIDEO,context)
        prefs.edit().remove(KEY_VİDEO_LINK).apply()
    }

    fun saveVideoProgress(context: Context, videoId: Int, progress: Long) {
        val preferences = context.getSharedPreferences("video_progress", Context.MODE_PRIVATE)
        preferences.edit().putLong("video_$videoId", progress).apply()
    }
    fun getVideoProgress(context: Context, videoId: Int): Long {
        val preferences = context.getSharedPreferences("video_progress", Context.MODE_PRIVATE)
        return preferences.getLong("video_$videoId", 0L)
    }
}
