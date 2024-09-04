package com.example.financialliteracy.data.repository

import android.util.Log
import com.example.financialliteracy.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authRepository: FirebaseAuth){

    suspend fun signUp(email:String, password:String) : Resource<String>{
        return  try {
            val result = authRepository.createUserWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user?.uid.orEmpty())
        }catch (e:Exception){
            Resource.Error(e)
        }
    }
}