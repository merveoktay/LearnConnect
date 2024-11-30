package com.example.learnconnect


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUnitTest {

    @Test
    fun `empty email returns false`() {
        val result = RegistrationUnit.validateRegistrationInput(
            email = "",
            password = "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `empty email returns true`() {
        val result = RegistrationUnit.validateRegistrationInput(
            email = "Carl@mail.com",
            password = "123"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun `empty password returns false`() {
        val result = RegistrationUnit.validateRegistrationInput(
            email = "Carl@mail.com",
            password = ""
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `empty password returns true`() {
        val result = RegistrationUnit.validateRegistrationInput(
            email = "Carl@mail.com",
            password = "123"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun `email already exists returns false`() {
        val result = RegistrationUnit.validateRegistrationInput(
            email = "merve@mail.com",
            password = "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `email already exists returns true`() {
        val result = RegistrationUnit.validateRegistrationInput(
            email = "Carl@mail.com",
            password = "123"
        )
        assertThat(result).isTrue()
    }

}