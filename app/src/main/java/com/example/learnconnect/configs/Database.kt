package com.example.learnconnect.configs

import android.content.Context
import androidx.room.Room

class Database {
    companion object{
        fun database(context:Context):AppDatabase{
            val db= Room.databaseBuilder(
                context,AppDatabase::class.java,
                "appDataBase"
            ).build()
            return db
        }
    }
}