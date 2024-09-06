package com.example.financialliteracy.ui.presentation.Register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialliteracy.common.Resource
import com.example.financialliteracy.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(private val authRepository: AuthRepository)
    : ViewModel() {
        private val liveDataMessage = MutableLiveData("")

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

        fun signUp(email:String, password:String) = viewModelScope.launch{
            if(email.isEmpty() || password.isEmpty()){
                liveDataMessage.value = "email and password cannot be null"
            }
            else{
                val result = authRepository.signUp(email, password)
                _authState.value = when (result) {
                    is Resource.Success -> AuthState.Authenticated
                    is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
                }
            }
        }

}
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
}