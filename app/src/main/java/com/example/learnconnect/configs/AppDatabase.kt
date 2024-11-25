package com.example.learnconnect.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learnconnect.dao.UserDao
import com.example.learnconnect.models.User
import javax.inject.Inject


@Database(entities = [User::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun userDao(): UserDao


}