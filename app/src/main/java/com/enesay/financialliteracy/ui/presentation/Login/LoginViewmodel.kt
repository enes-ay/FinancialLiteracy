package com.enesay.financialliteracy.ui.presentation.Login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.common.Resource
import com.enesay.financialliteracy.data.repository.AuthRepository
import com.enesay.financialliteracy.model.User.User
import com.enesay.financialliteracy.ui.presentation.Register.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(private val authRepository: AuthRepository) :ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    val userLoggedIn = mutableStateOf(authRepository.isUserLoggedIn())
    val currentUser = mutableStateOf(authRepository.getCurrentUserId())

    private val _userInfo = mutableStateOf<User?>(null)
    val userInfo: State<User?> = _userInfo

    init {
        userLoggedIn.value= authRepository.isUserLoggedIn()
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        // İşlem başlamadan önce Loading durumunu ayarla
        _authState.value = AuthState.Loading

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password cannot be null")
        } else {
            val result = authRepository.signIn(email, password)
            _authState.value = when (result) {
                is Resource.Success -> AuthState.Authenticated
                is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        val result = authRepository.signOut()
        _authState.value = when (result) {
            is Resource.Success -> {
                userLoggedIn.value = false
                AuthState.Idle
            }
            is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
        }
    }

    fun getUserInfo() = viewModelScope.launch {
        val userId = authRepository.getCurrentUserId()
        if (userId != null) {
            when (val result = authRepository.getUserProfile(userId)) {
                is Resource.Success -> _userInfo.value = result.data
                is Resource.Error -> {
                    // Hata durumunda gerekli işlemleri yap
                }
            }
        }
    }

}