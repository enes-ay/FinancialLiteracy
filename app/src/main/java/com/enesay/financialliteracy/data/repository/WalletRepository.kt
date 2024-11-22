package com.enesay.financialliteracy.data.repository

import android.util.Log
import com.enesay.financialliteracy.model.Trade.Asset
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
        Log.d("gelen", snapshot.toString())

        return snapshot.getDouble("Balance") ?: 0.0
    }

    suspend fun updateUserBalance(userId: String, newBalance: Double) {
        firestore.collection("Users").document(userId).update("Balance", newBalance).await()
    }
    suspend fun getUserAssets(userId: String): List<Asset> {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        // Veriyi "Assets" alanından al
        val assetsMap = snapshot.get("Assets") as? Map<String, Double> ?: emptyMap()

        // Assets Map'ini Asset nesnelerine dönüştür
        return assetsMap.map { (symbol, quantity) ->
            Asset(
                id = symbol,  // Sembolü id olarak kullanabiliriz
                name = "",  // Adı burada ekleyebilirsiniz, örneğin bir API'den veya yerel veritabanından alabilirsiniz
                symbol = symbol,
                price = 0.0,  // Fiyatı burada sıfır olarak tutuyoruz, güncel fiyatı ekleyebilirsiniz
                quantity = quantity
            )
        }
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

    suspend fun sellUserAsset(userId: String, symbol: String, amount: Double) {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        // Mevcut varlıkları çek
        val currentAssets = snapshot.get("Assets") as? MutableMap<String, Double> ?: mutableMapOf()

        // Yeni varlık miktarını hesapla (varsa üstüne ekle)
        val updatedAmount = (currentAssets[symbol] ?: 0.0) - amount
        currentAssets[symbol] = updatedAmount

        // Firestore'da güncelle
        userRef.update("Assets", currentAssets).await()
    }

//    suspend fun sellUserAsset(userId: String, symbol: String, amount: Double) {
//        val userRef = firestore.collection("Users").document(userId)
//        val snapshot = userRef.get().await()
//
//        // Mevcut varlıkları çek
//        val currentAssets = snapshot.get("Assets") as? MutableMap<String, Double> ?: mutableMapOf()
//
//        // Kullanıcının bu varlıktan sahip olduğu miktarı kontrol et
//        val currentAmount = currentAssets[symbol] ?: 0.0
//        if (currentAmount < amount) {
//            throw IllegalArgumentException("Insufficient $symbol to sell. Available: $currentAmount, Requested: $amount")
//        }
//
//        // Yeni miktarı hesapla
//        val updatedAmount = currentAmount - amount
//
//        if (updatedAmount == 0.0) {
//            // Varlığı tamamen kaldır
//            currentAssets.remove(symbol)
//        } else {
//            // Yeni miktarı güncelle
//            Log.d("burada2", "burada")
//            currentAssets[symbol] = updatedAmount
//        }
//
//        // Firestore'da güncelle
//        userRef.update("Assets", currentAssets).await()
//    }




//    suspend fun updateUserAsset(userId: String, symbol: String, newQuantity: Double) {
//        val userRef = firestore.collection("Users").document(userId)
//        val userAssets = getUserAssets(userId)
//
//        userAssets[symbol] = (userAssets[symbol] ?: 0.0) + newQuantity
//        userRef.update("Assets", userAssets).await()    }
}