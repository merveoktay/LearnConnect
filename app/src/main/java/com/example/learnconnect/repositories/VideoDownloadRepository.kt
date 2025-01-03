package com.example.learnconnect.repositories

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext

class VideoDownloadRepository(@ApplicationContext private val context: Context) {
     fun videoDownload(videoUrl:String,title:String):String{
        val request=DownloadManager.Request(Uri.parse(videoUrl))
        request.setTitle("Downloading")
        request.setDescription("Downloading")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        val destinationUri=getDownloadDestinationUri(title)
        request.setDestinationUri(destinationUri)

        val dowloadManager=context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dowloadManager.enqueue(request)

        return destinationUri.toString()
    }

     private fun getDownloadDestinationUri(title: String):Uri{
        val fileName="${title.replace("","_")}.mp4"
        val destinationDirectory=if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            context.getExternalFilesDir(null)?.absolutePath
        }else{
            "/storge/emulated/0/Download"
        }
        return Uri.parse("file://$destinationDirectory/$fileName")
    }
}