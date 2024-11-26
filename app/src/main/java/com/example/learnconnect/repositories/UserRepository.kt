package com.example.learnconnect.repositories

import com.example.learnconnect.dao.UserDao
import com.example.learnconnect.models.User

class UserRepository(val userDao: UserDao) {
    suspend fun registerUser(username:String,email: String,password:String):Boolean{
       val user=User(username=username,email=email, password = password)
        userDao.insertUser(user)
        return true
    }
    suspend fun loginUser(email: String,password: String):Boolean{
        val user= userDao.getUser(email,password)
        return user!=null
    }


}