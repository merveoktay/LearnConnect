package com.example.learnconnect.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.learnconnect.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)
    @Query("SELECT* FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String,password:String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findUserByEmailOrUsername(email: String): User?
    @Query("SELECT* FROM users WHERE  id = :userId LIMIT 1")
    suspend fun getUserbyId(userId:Int): User?
}