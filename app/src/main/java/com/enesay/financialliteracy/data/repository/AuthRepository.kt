package com.enesay.financialliteracy.data.repository

import com.enesay.financialliteracy.common.Resource
import com.enesay.financialliteracy.model.User.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signUp(email: String, password: String, name: String, surname: String): Resource<String> {
        return try {
            // Firebase Auth ile kullanıcıyı oluştur
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid.orEmpty()

            if (userId.isNotEmpty()) {
                initializeFirestoreData(userId, name, surname, email)
            }

            Resource.Success(userId)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private suspend fun initializeFirestoreData(userId: String, name: String, surname: String, email: String) {
        val userDocument = mapOf(
            "name" to name,
            "surname" to surname,
            "email" to email,
            "Balance" to 10000.0, // Başlangıç bakiyesi
            "Assets" to emptyList<Map<String, Any>>(),
            "Transactions" to emptyList<Map<String, Any>>() // Boş işlem geçmişi
        )

        firestore.collection("Users")
            .document(userId)
            .set(userDocument)
            .await()
    }

    suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signOut(): Resource<String> {
        return try {
            firebaseAuth.signOut()
            Resource.Success("User signed out successfully")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getUserProfile(userId: String): Resource<User> {
        return try {
            val snapshot = firestore.collection("Users").document(userId).get().await()
            if (snapshot.exists()) {
                val userProfile = snapshot.toObject(User::class.java)
                if (userProfile != null) {
                    Resource.Success(userProfile)
                } else {
                    Resource.Error(Exception("User profile data is null"))
                }
            } else {
                Resource.Error(Exception("User profile not found"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
    fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid
}