package com.example.learnconnect.initialize

import android.util.Log
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.models.Video
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
                    Course(
                        1,
                        "Flutter Architecture Template v2",
                        "https://thumbs.dreamstime.com/z/may-brazil-photo-illustration-flutter-entertainment-logo-displayed-smartphone-screen-320096491.jpg?ct=jpeg",
                        0
                    ),
                    Course(
                        2,
                        "Get Started with iOS Development",
                        "https://thumbs.dreamstime.com/z/app-store-digital-distribution-platform-developed-maintained-apple-inc-mobile-apps-its-ios-ipados-operating-204759434.jpg?ct=jpeg",
                        0
                    ),
                    Course(
                        3,
                        "Web Development Tutorials For Beginners",
                        "https://thumbs.dreamstime.com/z/web-development-tools-concept-virtual-screen-programming-language-scripts-php-sql-html-java-others-web-development-132806971.jpg?ct=jpeg",
                        1
                    ),
                    Course(
                        4,
                        "Web Development ",
                        "https://thumbs.dreamstime.com/z/thin-line-smooth-gradient-flat-design-banner-web-development-website-mobile-website-easy-to-use-highly-customizable-108241229.jpg?ct=jpeg",
                        1
                    ),
                    Course(
                        5,
                        "Learn Unity",
                        "https://thumbs.dreamstime.com/z/laptop-computer-displaying-logo-unity-software-inc-poznan-pol-sep-american-video-game-development-company-based-san-209283348.jpg?ct=jpeg",
                        2
                    ),
                    Course(
                        6,
                        "Python for Beginners ",
                        "https://thumbs.dreamstime.com/z/python-programming-language-round-logo-sticker-symbol-label-laying-laptop-keyboard-closeup-top-view-simple-technology-concept-211691587.jpg?ct=jpeg",
                        3
                    ),
                    Course(
                        7,
                        "Data Science",
                        "https://thumbs.dreamstime.com/z/data-science-word-cloud-finger-pressing-touch-screen-69848887.jpg?ct=jpeg",
                        3
                    ),
                    Course(
                        8,
                        "Figma",
                        "https://thumbs.dreamstime.com/z/figma-vector-graphics-editor-prototyping-tool-which-primarily-web-based-additional-offline-features-enabled-205580440.jpg?ct=jpeg",
                        4
                    ),
                    Course(
                        9,
                        "Figma UI Design Tutorial",
                        "https://thumbs.dreamstime.com/z/figma-logo-screen-smartphone-online-service-interface-development-prototyping-moscow-russia-september-176590482.jpg?ct=jpeg",
                        4
                    ),
                    Course(
                        10,
                        "Android Basics 2024",
                        "https://thumbs.dreamstime.com/z/android-symbol-figure-white-background-android-o-android-symbol-figure-white-background-android-103876615.jpg?ct=jpeg",
                        0
                    ),
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

    suspend fun initializeVideos() {
        try {
            val existingVideos = courseDao.getAllVideos()
            Log.d("Database", "Existing videos: $existingVideos")

            if (existingVideos.isEmpty()) {
                val videos = listOf(
                    Video(
                        0,
                        "Activities & the Activity Lifecycle",
                        "https://youtu.be/SJw3Nu_h8kk?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        0
                    ),
                    Video(
                        1,
                        "Tasks, Back Stack & Launch Modes",
                        "https://youtu.be/Z0AzoFOiH9c?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        0
                    ),
                    Video(
                        2,
                        "ViewModels",
                        "https://youtu.be/9sqvBydNJSg?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        0
                    ),
                    Video(
                        3,
                        "What is the Context?",
                        "https://youtu.be/YdnM2ZvrIFM?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        0
                    ),

                    Video(
                        4,
                        "Flutter Mimari v2b1: Nedir",
                        "hhttps://youtu.be/zOG-4bkLeu0?list=PL1k5oWAuBhgUAFNvxdF6wIuC9cfvN5PrZ",
                        1
                    ),
                    Video(
                        5,
                        "Proje ve modül oluşturma",
                        "https://youtu.be/TIrcxptk89Y?list=PL1k5oWAuBhgUAFNvxdF6wIuC9cfvN5PrZ",
                        1
                    ),
                    Video(
                        6,
                        "VSCode eklentileri",
                        "https://youtu.be/omIsEj5fiXM?list=PL1k5oWAuBhgUAFNvxdF6wIuC9cfvN5PrZ",
                        1
                    ),
                    Video(
                        7,
                        "Çoklu dil desteği alt yapısı hazırlama",
                        "https://youtu.be/xLpKFOqoPMI?list=PL1k5oWAuBhgUAFNvxdF6wIuC9cfvN5PrZ",
                        1
                    ),

                    Video(
                        8,
                        "Introdaction",
                        "https://youtu.be/UNH0bE4zPtY?list=PLSzsOkUDsvdu5Mm67aBYs2YPu2OM4mFzt",
                        2
                    ),
                    Video(
                        9,
                        "How Does an App Work?",
                        "https://youtu.be/n7UFxV7zHKw?list=PLSzsOkUDsvdu5Mm67aBYs2YPu2OM4mFzt",
                        2
                    ),
                    Video(
                        10,
                        "How to Make an App",
                        "https://youtu.be/HxssFtenD_M?list=PLSzsOkUDsvdu5Mm67aBYs2YPu2OM4mFzt",
                        2
                    ),


                    Video(
                        11,
                        "Introdaction",
                        "https://youtu.be/gQojMIhELvM?list=PLoYCgNOIyGAB_8_iq1cL8MVeun7cB6eNc",
                        3
                    ),
                    Video(
                        12,
                        "HTML",
                        "https://youtu.be/RjHflb-QgVc?list=PLoYCgNOIyGAB_8_iq1cL8MVeun7cB6eNc",
                        3
                    ),
                    Video(
                        13,
                        "HTML CSS",
                        "https://youtu.be/J35jug1uHzE?list=PLoYCgNOIyGAB_8_iq1cL8MVeun7cB6eNc",
                        3
                    ),
                    Video(
                        14,
                        "How To Make A Website From HTML & CSS",
                        "https://youtu.be/5iUB31h2Hzs?list=PLoYCgNOIyGAB_8_iq1cL8MVeun7cB6eNc",
                        3
                    ),
                    Video(
                        15,
                        "Hand-code an HTML + CSS layout",
                        "https://youtu.be/dMK_3lH1YPo?list=PLoYCgNOIyGAB_8_iq1cL8MVeun7cB6eNc",
                        3
                    ),

                    Video(
                        16,
                        "Introduction",
                        "https://youtu.be/W-D71ZeMixQ?list=PLBlnK6fEyqRiwWLbSXKFtdGV8OVqr9dZr",
                        4
                    ),
                    Video(
                        17,
                        "Classes and Object",
                        "https://youtu.be/Kk6dlF59yGg?list=PLBlnK6fEyqRiwWLbSXKFtdGV8OVqr9dZr",
                        4
                    ),
                    Video(
                        18,
                        "Creating a Class in Java",
                        "https://youtu.be/EplETikbJis?list=PLBlnK6fEyqRiwWLbSXKFtdGV8OVqr9dZr",
                        4
                    ),

                    Video(
                        18,
                        "Beginner's Game Development Tutorial",
                        "https://youtu.be/gB1F9G0JXOo",
                        5
                    ),

                    Video(
                        19,
                        "Programming Tutorial",
                        "https://youtu.be/QXeEoD0pB3E?list=PLsyeobzWxl7poL9JTVyndKe62ieoN-MZ3",
                        6
                    ),
                    Video(
                        20,
                        "Introduction to Python",
                        "https://youtu.be/hEgO047GxaQ?list=PLsyeobzWxl7poL9JTVyndKe62ieoN-MZ3",
                        6
                    ),

                    Video(
                        21,
                        "Data Science for Beginners",
                        "https://youtu.be/jNeUBWrrRsQ",
                        7
                    ),
                    Video(
                        22,
                        "Statistics For Data Science",
                        "https://youtu.be/Lv0xcdeXaGU",
                        7
                    ),

                    Video(
                        23,
                        "Frame Tool",
                        "https://youtu.be/DTiXw486rRE?list=PLdctvM0pDo0tYucIbTxDUbKVnWnH5szz6",
                        8                    ),
                    Video(
                        24,
                        "Shape Tool",
                        "https://youtu.be/5iFF50wzCzY?list=PLdctvM0pDo0tYucIbTxDUbKVnWnH5szz6",
                        8
                    ),
                    Video(
                        25,
                        "Pen Tool",
                        "https://youtu.be/tM6G8ExyKYA?list=PLdctvM0pDo0tYucIbTxDUbKVnWnH5szz6",
                        8
                    ),

                    Video(
                        26,
                        "Figma UI Design Tutorial",
                        "https://youtu.be/FTFaQWZBqQ8",
                        9
                    ),

                    Video(
                        27,
                        "Activities & the Activity Lifecycle",
                        "https://youtu.be/SJw3Nu_h8kk?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        10
                    ),
                    Video(
                        28,
                        "Tasks, Back Stack & Launch Modes",
                        "https://youtu.be/Z0AzoFOiH9c?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        10
                    ),
                    Video(
                        29,
                        "ViewModels",
                        "https://youtu.be/9sqvBydNJSg?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        10
                    ),
                    Video(
                        30,
                        "What is the Context?",
                        "https://youtu.be/YdnM2ZvrIFM?list=PLQkwcJG4YTCSVDhww92llY3CAnc_vUhsm",
                        10
                    ),

                    )
                videos.forEach {
                    courseDao.insertVideo(it)
                    Log.d("Database", "Inserted video: ${it.title}")
                }
            }
        } catch (e: Exception) {
            Log.e("Database", "Error inserting videos: ${e.message}")
        }
    }

    suspend fun getVideos(): List<Video> {
        Log.d("video", courseDao.getAllVideos().toString())
        return courseDao.getAllVideos()
    }

    suspend fun getVideosByCourse(courseId: Int): List<Video> {
        return courseDao.getVideosByCourse(courseId)
    }

    suspend fun getCategories(): List<CourseType> {
        Log.d("categories", courseDao.getAllCourseTypes().toString())
        return courseDao.getAllCourseTypes()
    }

    suspend fun getCourses(): List<Course> {
        Log.d("course", courseDao.getAllCourse().toString())
        return courseDao.getAllCourse()
    }
    suspend fun getCourse(course_id:Int): Course {
        Log.d("course", courseDao.getAllCourse().toString())
        return courseDao.getCourse(course_id)
    }

    suspend fun getCoursesByType(courseTypeId: Int): List<Course> {
        return courseDao.getAllCoursesByType(courseTypeId)
    }
}