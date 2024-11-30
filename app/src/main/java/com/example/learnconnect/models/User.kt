package com.example.learnconnect.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.StateFlow


@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,

    )
