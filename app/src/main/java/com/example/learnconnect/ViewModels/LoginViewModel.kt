package com.example.learnconnect.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnect.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _loginState =MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get()=_loginState
    fun login(email:String,password:String){
        viewModelScope.launch {
            _loginState.value=LoginState.Success
            val success = userRepository.loginUser(email,password)
            if (success){
                _loginState.value=LoginState.Success
            }
            else{
                _loginState.value=LoginState.Error("Email or password is incorrect.")
            }
        }
    }


}

sealed class LoginState {
    object Loading : LoginState()
    object Success : LoginState()
    data class  Error(val message:String):LoginState()
}
