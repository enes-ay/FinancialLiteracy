package com.enesay.financialliteracy.ui.presentation.Register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.common.Resource
import com.enesay.financialliteracy.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(private val authRepository: AuthRepository)
    : ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun signUp(email:String, password:String, name:String, surname:String) = viewModelScope.launch{
        _authState.value = AuthState.Loading

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password cannot be null")
        }
        else{
            val result = authRepository.signUp(email, password, name, surname)
            _authState.value = when (result) {
                is Resource.Success -> AuthState.Authenticated
                is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
            }
        }
    }
}