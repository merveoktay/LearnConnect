package com.example.learnconnect.di

import android.app.Application
import androidx.room.Room
import com.example.learnconnect.configs.AppDatabase
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.dao.UserDao
import com.example.learnconnect.initialize.CourseCategoryProvider import com.example.learnconnect.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "your-database-name")
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.courseDao()
    }


    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }


    @Provides
    @Singleton
    fun provideCourseCategoryProvider(courseDao: CourseDao): CourseCategoryProvider {
        return CourseCategoryProvider(courseDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }
}
