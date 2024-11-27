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

    // Veritabanı sağlayıcı metodu
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "your-database-name")
            .addMigrations(AppDatabase.MIGRATION_1_2)  // Migration'ı ekliyoruz
            .fallbackToDestructiveMigration()
            .build()
    }

    // CourseDao sağlayıcı metodu
    @Provides
    @Singleton
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.courseDao()
    }

    // UserDao sağlayıcı metodu
    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    // CourseCategoryProvider sağlayıcı metodu
    @Provides
    @Singleton
    fun provideCourseCategoryProvider(courseDao: CourseDao): CourseCategoryProvider {
        return CourseCategoryProvider(courseDao)
    }

    // UserRepository sağlayıcı metodu
    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }
}
