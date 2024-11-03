package com.example.financialliteracy.data.repository

import android.util.Log
import com.example.financialliteracy.common.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                         private val walletRepository: WalletRepository,
    private val firestore: FirebaseFirestore){

    suspend fun signUp(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid.orEmpty()
            val userDoc = firestore.collection("Users").document(userId)
            userDoc.set(
                mapOf(
                    "Balance" to 10000.0,
                    "Assets" to emptyList<Map<String, Any>>(),
                    "Transactions" to emptyList<Map<String, Any>>()
                )
            ).await()
            if (userId.isNotEmpty()) {
                // Yeni kullanıcıya başlangıç bakiyesi atanır
                Log.e("initial","balance verildi")
                walletRepository.initializeUserData(userId, 10000.0)
            }

            Resource.Success(userId)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signIn(email:String, password:String) : Resource<String>{
        return  try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user?.uid.orEmpty())
        }catch (e:Exception){
            Resource.Error(e)
        }
    }
    suspend fun signOut() : Resource<String> {
        return try {
            firebaseAuth.signOut()
            Resource.Success("User signed out successfully")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser!=null
    fun getCurrentUserId() : String? = firebaseAuth.currentUser?.uid
}