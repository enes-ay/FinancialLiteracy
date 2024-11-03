package com.example.financialliteracy.data.repository

import android.util.Log
import com.example.financialliteracy.data.datasource.WalletDatasource
import com.example.financialliteracy.model.Trade.Transaction
import com.example.financialliteracy.ui.presentation.Portfolio.Asset
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WalletRepository @Inject constructor(val firestore: FirebaseFirestore) {

    fun initializeUserData(userId: String, initialBalance: Double) {
        val userRef = firestore.collection("Users").document(userId)
        val userData = mapOf(
            "Balance" to initialBalance,
            "Assets" to emptyList<Map<String, Any>>() // Başlangıçta boş varlık listesi
        )

        userRef.set(userData)
            .addOnSuccessListener { Log.d("UserRepository", "User data initialized") }
            .addOnFailureListener { e ->
                Log.e(
                    "UserRepository",
                    "Error initializing user data",
                    e
                )
            }
    }

    suspend fun getUserBalance(userId: String): Double {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()
        return snapshot.getDouble("Balance") ?: 0.0
    }

    suspend fun updateUserBalance(userId: String, newBalance: Double) {
        firestore.collection("Users").document(userId).update("Balance", newBalance).await()
    }
    suspend fun getUserAssets(userId: String): Map<String, Double> {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()
        return snapshot.get("Assets") as? Map<String, Double> ?: emptyMap()
    }

    suspend fun addUserAsset(userId: String, symbol: String, amount: Double) {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        // Mevcut varlıkları çek
        val currentAssets = snapshot.get("Assets") as? MutableMap<String, Double> ?: mutableMapOf()

        // Yeni varlık miktarını hesapla (varsa üstüne ekle)
        val updatedAmount = (currentAssets[symbol] ?: 0.0) + amount
        currentAssets[symbol] = updatedAmount

        // Firestore'da güncelle
        userRef.update("Assets", currentAssets).await()
    }

    suspend fun updateUserAsset(userId: String, symbol: String, newQuantity: Double) {
        val userRef = firestore.collection("Users").document(userId)
        val userAssets = getUserAssets(userId).toMutableMap()

        userAssets[symbol] = (userAssets[symbol] ?: 0.0) + newQuantity
        userRef.update("Assets", userAssets).await()    }
}