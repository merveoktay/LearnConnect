package com.example.learnconnect.initialize

import android.util.Log
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import javax.inject.Inject

class CourseCategoryProvider @Inject constructor(private val courseDao: CourseDao) {
    suspend fun initializeCategories() {
        try {
            val existingCategories = courseDao.getAllCourseTypes()
            Log.d("Database", "Existing categories: $existingCategories")

            if (existingCategories.isEmpty()) {
                val categories = listOf(
                    CourseType(0, "Mobile Development"),
                    CourseType(1, "Web Development"),
                    CourseType(2, "Game Development"),
                    CourseType(3, "Data Science"),
                    CourseType(4, "UI Design")
                )
                categories.forEach {
                    courseDao.insertCourseType(it)
                    Log.d("Database", "Inserted category: ${it.name}")
                }
            }
        } catch (e: Exception) {
            Log.e("Database", "Error inserting categories: ${e.message}")
        }
    }
    suspend fun initializeCourses() {
        try {
            val existingCourse = courseDao.getAllCourse()
            Log.d("Database", "Existing categories: $existingCourse")

            if (existingCourse.isEmpty()) {
                val course = listOf(
                    Course(0, "Android Basics 2024", "https://thumbs.dreamstime.com/z/android-symbol-figure-white-background-android-o-android-symbol-figure-white-background-android-103876615.jpg?ct=jpeg",0),
                    Course(1, "Flutter Architecture Template v2", "https://thumbs.dreamstime.com/z/may-brazil-photo-illustration-flutter-entertainment-logo-displayed-smartphone-screen-320096491.jpg?ct=jpeg",0),
                    Course(2, "Get Started with iOS Development", "https://thumbs.dreamstime.com/z/app-store-digital-distribution-platform-developed-maintained-apple-inc-mobile-apps-its-ios-ipados-operating-204759434.jpg?ct=jpeg",0),
                    Course(3, "Web Development Tutorials For Beginners","https://thumbs.dreamstime.com/z/web-development-tools-concept-virtual-screen-programming-language-scripts-php-sql-html-java-others-web-development-132806971.jpg?ct=jpeg", 1),
                    Course(4, "Web Development ", "https://thumbs.dreamstime.com/z/thin-line-smooth-gradient-flat-design-banner-web-development-website-mobile-website-easy-to-use-highly-customizable-108241229.jpg?ct=jpeg",1),
                    Course(5, "Learn Unity", "https://thumbs.dreamstime.com/z/laptop-computer-displaying-logo-unity-software-inc-poznan-pol-sep-american-video-game-development-company-based-san-209283348.jpg?ct=jpeg",2),
                    Course(6, "Python for Beginners ","https://thumbs.dreamstime.com/z/python-programming-language-round-logo-sticker-symbol-label-laying-laptop-keyboard-closeup-top-view-simple-technology-concept-211691587.jpg?ct=jpeg", 3),
                    Course(7, "Data Science", "https://thumbs.dreamstime.com/z/data-science-word-cloud-finger-pressing-touch-screen-69848887.jpg?ct=jpeg",3),
                    Course(8, "Figma", "https://thumbs.dreamstime.com/z/figma-vector-graphics-editor-prototyping-tool-which-primarily-web-based-additional-offline-features-enabled-205580440.jpg?ct=jpeg",4),
                    Course(9, "Figma UI Design Tutorial", "https://thumbs.dreamstime.com/z/figma-logo-screen-smartphone-online-service-interface-development-prototyping-moscow-russia-september-176590482.jpg?ct=jpeg",4),
                    Course(10, "Android Basics 2024", "https://thumbs.dreamstime.com/z/android-symbol-figure-white-background-android-o-android-symbol-figure-white-background-android-103876615.jpg?ct=jpeg",0),
                )
                course.forEach {
                    courseDao.insertCourse(it)
                    Log.d("Database", "Inserted category: ${it.name}")
                }
            }
        } catch (e: Exception) {
            Log.e("Database", "Error inserting course: ${e.message}")
        }
    }
    suspend fun getCategories(): List<CourseType> {
        Log.d("categories", courseDao.getAllCourseTypes().toString())
        return courseDao.getAllCourseTypes()
    }
    suspend fun getCourses(): List<Course> {
        Log.d("course",courseDao.getAllCourse().toString())
        return courseDao.getAllCourse()
    }
    suspend fun getCoursesByType(courseTypeId: Int): List<Course> {
        return courseDao.getCoursesByType(courseTypeId)
    }
}