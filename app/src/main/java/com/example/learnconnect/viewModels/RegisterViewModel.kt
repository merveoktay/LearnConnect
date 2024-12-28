package com.example.learnconnect.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }


    fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val existingUser = repository.findUserByEmailOrUsername(email)
            if (existingUser != null) {
                onError()
                return@launch
            }

            val result = repository.registerUser(username,email, password)
            if (result) {
                onSuccess()
            } else {
                onError()
            }
        }
    }
}