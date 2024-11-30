package com.example.learnconnect.viewModels

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.models.User
import com.example.learnconnect.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _id = MutableStateFlow(0)
    val id: StateFlow<Int> = _id
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login(
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        viewModelScope.launch {
            val result = repository.loginUser(email.value, password.value)

            if (result) {

                _id.value = repository.getIdbyUser(email.value, password.value) ?: 0
                Log.d("KULLANICI ID Si", _id.value.toString())

                onSuccess()

            } else {
                onError()
            }
        }
    }

    fun getUserId(): Int {
        Log.d("KULLANICI normal olan ID Si", _id.value.toString())
        return _id.value
    }


}