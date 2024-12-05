package com.example.learnconnect

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

object CacheManager {
    @UnstableApi
    private var simpleCache: SimpleCache? = null

    @OptIn(UnstableApi::class)
    fun getSimpleCache(context: Context): SimpleCache {
        if (simpleCache == null) {
            synchronized(this) {
                if (simpleCache == null) {
                    val cacheDir = File(context.cacheDir, "media")
                    val cacheEvictor = LeastRecentlyUsedCacheEvictor(1024 * 1024 * 100) // 100 MB cache
                    simpleCache = SimpleCache(cacheDir, cacheEvictor)
                }
            }
        }
        return simpleCache!!
    }
}