package com.example.financialliteracy.ui.presentation.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialliteracy.common.Resource
import com.example.financialliteracy.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewmodel @Inject constructor(private val authRepository: AuthRepository)
    : ViewModel() {
        private fun isUserLoggedIn(){
            if (authRepository.isUserLoggedIn()){

            }
            else{

            }
        }

        private fun signUp(email:String, password:String) = viewModelScope.launch{
            when(val result = authRepository.signUp(email,password)){
                is Resource.Success-> {

                }
                is Resource.Error->{

                }
            }
        }
        private fun signIn(email:String, password:String) = viewModelScope.launch{
            when(val result = authRepository.signUp(email,password)){
                is Resource.Success-> {

                }
                is Resource.Error->{

                }
            }
        }

}