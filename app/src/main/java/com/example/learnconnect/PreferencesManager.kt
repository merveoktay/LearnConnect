package com.example.learnconnect

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"

    private const val KEY_VİDEO_LINK = "video_link"


    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(context: Context, userId: Int) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putInt(KEY_USER_ID, userId).apply()
    }


    fun getUserId(context: Context): Int {
        val prefs = getSharedPreferences(context)
        return prefs.getInt(KEY_USER_ID, 0) // Varsayılan olarak 0 döner
    }
    fun saveVideoLink(context: Context, video_link: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_VİDEO_LINK, video_link).apply()
    }

    fun getVideoLink(context: Context): String {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_VİDEO_LINK,"").toString() // Varsayılan olarak 0 döner
    }

    // userId'yi silme
    fun clearUserId(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_USER_ID).apply()
    }
    fun clearVideoLink(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().remove(KEY_VİDEO_LINK).apply()
    }
}
