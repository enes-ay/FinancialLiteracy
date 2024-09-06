package com.example.financialliteracy.ui.presentation.Login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialliteracy.common.Resource
import com.example.financialliteracy.data.repository.AuthRepository
import com.example.financialliteracy.ui.presentation.Register.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(private val authRepository: AuthRepository) :ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState
    val userLoggedIn = mutableStateOf(false)

    init {
        userLoggedIn.value= authRepository.isUserLoggedIn()

    }

    fun signIn(email:String, password:String) = viewModelScope.launch{
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password cannot be null")
        }
        else{
            val result = authRepository.signIn(email, password)
            _authState.value = when (result) {
                is Resource.Success -> AuthState.Authenticated
                is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
            }
        }
    }

}