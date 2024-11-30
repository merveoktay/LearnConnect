package com.example.learnconnect.initialize

import android.util.Log
import com.example.learnconnect.dao.CourseDao
import com.example.learnconnect.models.Video
import com.example.learnconnect.models.Course
import com.example.learnconnect.models.CourseType
import com.example.learnconnect.models.UserCourse
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
                        1,
                        "Activities & the Activity Lifecycle",
                        "https://videocdn.cdnpk.net/videos/27fdc0ee-40fa-5e6a-aa99-f003acd2af4c/horizontal/previews/clear/large.mp4?token=exp=1732928747~hmac=e7a3a5f8e94a58ca15cc37e4b9ad587710e3afffda4c3ee137ee49b114fe8f51",
                        10
                    ),
                    Video(
                        2,
                        "Tasks, Back Stack & Launch Modes",
                        "https://cdn.pixabay.com/video/2015/08/08/90-135735293_large.mp4",
                        10
                    ),
                    Video(
                        3,
                        "ViewModels",
                        "https://cdn.pixabay.com/video/2022/10/24/136259-764387660_large.mp4",
                        10
                    ),
                    Video(
                        4,
                        "What is the Context?",
                        "https://cdn.pixabay.com/video/2022/10/31/137265-766326232_large.mp4",
                        10
                    ),

                    Video(
                        5,
                        "Flutter Mimari v2b1: Nedir",
                        "https://cdn.pixabay.com/video/2022/10/24/136264-764387675_large.mp4",
                        1
                    ),
                    Video(
                        6,
                        "Proje ve modül oluşturma",
                        "https://cdn.pixabay.com/video/2022/10/24/136283-764387738_large.mp4",
                        1
                    ),
                    Video(
                        7,
                        "VSCode eklentileri",
                        "https://cdn.pixabay.com/video/2020/05/23/39892-423345743_large.mp4",
                        1
                    ),
                    Video(
                        8,
                        "Çoklu dil desteği alt yapısı hazırlama",
                        "https://cdn.pixabay.com/video/2022/10/24/136269-764387693_large.mp4",
                        1
                    ),

                    Video(
                        9,
                        "Introdaction",
                        "https://cdn.pixabay.com/video/2020/04/25/37106-413256452_large.mp4",
                        2
                    ),
                    Video(
                        10,
                        "How Does an App Work?",
                        "https://videocdn.cdnpk.net/videos/c7728dc3-7043-4d82-a2f8-267da8e07a77/horizontal/previews/videvo_watermarked/large.mp4",
                        2
                    ),
                    Video(
                        11,
                        "How to Make an App",
                        "https://videocdn.cdnpk.net/videos/c97ab11b-122c-4ab4-ae3a-1c10f37297bc/horizontal/previews/videvo_watermarked/large.mp4",
                        2
                    ),


                    Video(
                        12,
                        "Introdaction",
                        "https://cdn.pixabay.com/video/2015/10/16/1046-142621379_large.mp4",
                        3
                    ),
                    Video(
                        13,
                        "HTML",
                        "https://media.istockphoto.com/id/1332504522/tr/video/businesswoman-working-on-computer-at-night.mp4?s=mp4-640x640-is&k=20&c=lGmZd9RQtBOqxzyGovrumi_QlQZ0BLZwhPFQwXI_JTE=",
                        3
                    ),
                    Video(
                        14,
                        "HTML CSS",
                        "https://media.istockphoto.com/id/1006249812/tr/video/hologram-veri-izlerken.mp4?s=mp4-640x640-is&k=20&c=nuqgV6sKtHPr73y_Bo90_FLGWn_5AjFB1EJhAocDT4w=",
                        3
                    ),
                    Video(
                        15,
                        "How To Make A Website From HTML & CSS",
                        "https://cdn.pixabay.com/video/2019/05/06/23355-334950213_large.mp4",
                        3
                    ),
                    Video(
                        16,
                        "Hand-code an HTML + CSS layout",
                        "https://cdn.pixabay.com/video/2015/12/11/1625-148614367_medium.mp4",
                        3
                    ),

                    Video(
                        17,
                        "Introduction",
                        "https://videocdn.cdnpk.net/videos/cde6a5c3-5c11-4b75-981e-1cfc28a8685a/horizontal/previews/videvo_watermarked/large.mp4",
                        4
                    ),
                    Video(
                        18,
                        "Classes and Object",
                        "https://cdn.pixabay.com/video/2020/02/24/32767-394004551_large.mp4",
                        4
                    ),
                    Video(
                        19,
                        "Creating a Class in Java",
                        "https://videocdn.cdnpk.net/videos/a3a78c75-90a9-451e-b3de-89822a9a3c50/horizontal/previews/videvo_watermarked/large.mp4",
                        4
                    ),

                    Video(
                        20,
                        "Beginner's Game Development Tutorial",
                        "https://cdn.pixabay.com/video/2019/05/06/23354-334950206_large.mp4",
                        5
                    ),

                    Video(
                        21,
                        "Programming Tutorial",
                        "https://cdn.pixabay.com/video/2015/12/11/1625-148614367_medium.mp4",
                        6
                    ),
                    Video(
                        22,
                        "Introduction to Python",
                        "https://cdn.pixabay.com/video/2020/06/06/41263-429379223_large.mp4",
                        6
                    ),

                    Video(
                        23,
                        "Data Science for Beginners",
                        "https://cdn.pixabay.com/video/2020/02/24/32767-394004551_large.mp4",
                        7
                    ),
                    Video(
                        24,
                        "Statistics For Data Science",
                        "https://cdn.pixabay.com/video/2023/08/01/174086-850404739_large.mp4",
                        7
                    ),

                    Video(
                        25,
                        "Frame Tool",
                        "https://cdn.pixabay.com/video/2024/06/06/215500_large.mp4",
                        8                 ),
                    Video(
                        26,
                        "Shape Tool",
                        "https://cdn.pixabay.com/video/2023/10/01/183107-870151708_large.mp4",
                        8
                    ),
                    Video(
                        27,
                        "Pen Tool",
                        "https://media.istockphoto.com/id/1556389414/tr/video/man-using-a-laptop-double-exposure-with-business-data-analytics-dashboard.mp4?s=mp4-640x640-is&k=20&c=im3IwkIeg5H2KhxLb3_gRf5VKsSDuZVD4OvHyxfGp74=",
                        8
                    ),

                    Video(
                        28,
                        "Figma UI Design Tutorial",
                        "https://videocdn.cdnpk.net/videos/f4349d40-0272-5f2b-b755-410168ab606b/horizontal/previews/videvo_watermarked/large.mp4",
                        9
                    )

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
    suspend fun getCourse(courseId:Int): Course {
        Log.d("getCourse", courseDao.getAllCourse().toString())
        return courseDao.getCourse(courseId)
    }

    suspend fun getCoursesByType(courseTypeId: Int): List<Course> {
        return courseDao.getAllCoursesByType(courseTypeId)
    }
    suspend fun insertCourseForUser(userId: Int,courseId: Int){
        val userCourse=UserCourse(user_id = userId, course_id = courseId)
        return courseDao.insertUserCourse(userCourse =userCourse)
    }

    suspend fun isUserEnrolled(userId: Int, courseId: Int): Boolean {
        return courseDao.isUserEnrolled(userId, courseId) != null
    }
    fun getVideoById(videoId: Int): Video {
        return courseDao.getVideoById(videoId)
    }







}