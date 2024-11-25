package com.example.learnconnect.di

import androidx.lifecycle.ViewModel
import com.example.learnconnect.configs.AppDatabase
import com.example.learnconnect.dao.UserDao
import com.example.learnconnect.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideUserDao(database: AppDatabase):UserDao=database.userDao()
    @Provides
    fun provideUserRepository(userDao: UserDao):UserRepository= UserRepository(userDao)
}
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel()