package com.example.learnconnect.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) :ViewModel(){
    private  val _registerState=MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> get() = _registerState

    fun register(username: String,email:String,password:String, confirmPassword: String){
       if(password !=confirmPassword){
           _registerState.value=RegisterState.Error("Passwords do not match.")
           return
       }
        viewModelScope.launch {
            _registerState.value=RegisterState.Loading
            val success=userRepository.registerUser(username,email,password)
            if(success){
                _registerState.value=RegisterState.Success
            }else{
                _registerState.value=RegisterState.Error("Registration failed.")
            }
        }
    }
}

sealed class RegisterState {
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}