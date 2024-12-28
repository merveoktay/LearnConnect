package com.example.learnconnect.utils

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

object CacheManager {
    @UnstableApi
    private var simpleCache: SimpleCache? = null

    @Synchronized
    @OptIn(UnstableApi::class)
    fun getSimpleCache(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheDir = File(context.cacheDir, "media")
            val cacheEvictor = LeastRecentlyUsedCacheEvictor(1024 * 1024 * 100) // 100 MB cache
            val databaseProvider = StandaloneDatabaseProvider(context)
            simpleCache = SimpleCache(cacheDir, cacheEvictor, databaseProvider)
        }
        return simpleCache!!
    }
}

