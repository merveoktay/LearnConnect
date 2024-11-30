package com.example.learnconnect

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.learnconnect.models.Course
import com.example.learnconnect.repositories.CourseRepository
import com.example.learnconnect.viewModels.CourseViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class CourseViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // LiveData testleri için

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: CourseViewModel
    private val courseRepository: CourseRepository = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Coroutine testler için ana thread'i ayarlama
        viewModel = CourseViewModel(courseRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Test sonrası temizleme
    }

    @Test
    fun `loadCourses should initialize and load courses`() = runTest {

        val mockCourses = listOf(
            Course(
                1,
                "Kotlin Basics",
                "https://thumbs.dreamstime.com/z/may-brazil-photo-illustration-flutter-entertainment-logo-displayed-smartphone-screen-320096491.jpg?ct=jpeg",
                10
            ),
            Course(
                2,
                "Jetpack Compose",
                "https://thumbs.dreamstime.com/z/android-symbol-figure-white-background-android-o-android-symbol-figure-white-background-android-103876615.jpg?ct=jpeg",
                10
            )
        )
        whenever(courseRepository.getCourses()).thenReturn(mockCourses)

        viewModel.loadCourses()

        verify(courseRepository).initializeAllCourses()

        assertEquals(mockCourses, viewModel.courses.value)
    }

    @Test
    fun `loadCourses should handle exception and log error`() = runTest {

        val exception = RuntimeException("Test exception")

        whenever(courseRepository.getCourses()).thenThrow(exception)

        val logSpy = mockStatic(Log::class.java)

        logSpy.use {

            viewModel.loadCourses()

            logSpy.verify { Log.e("VideoViewModel", "Error loading courses: Test exception") }
        }
    }
}
