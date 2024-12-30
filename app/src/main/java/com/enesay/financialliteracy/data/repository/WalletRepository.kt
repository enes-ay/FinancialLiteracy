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

        val assetsList = snapshot.get("Assets") as? List<Map<String, Any>> ?: emptyList()
        return assetsList.map { asset ->
            Asset(
                id = asset["id"]?.toString() ?: "Unknown",
                name = asset["name"] as? String ?: "Unknown",
                symbol = asset["symbol"]?.toString() ?: "Unknown",
                price = (asset["purchasePrice"] as? Double) ?: 0.0,
                quantity = (asset["quantity"] as? Double) ?: 0.0,
                max_supply = 0.0,
                cmc_rank = 0,
                self_reported_market_cap = 0.0,
                volume_24h = 0.0,
                asset_type = 1
            )
        }
    }

    suspend fun addUserAsset(userId: String, asset: Asset, purchasePrice: Double, purchaseAmount: Double, purchaseDate: String) {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        val currentAssets = snapshot.get("Assets") as? MutableList<Map<String, Any>> ?: mutableListOf()

        val existingAssetIndex = currentAssets.indexOfFirst { it["symbol"] == asset.symbol }

        if (existingAssetIndex != -1) {
            val existingAsset = currentAssets[existingAssetIndex]
            val updatedQuantity = (existingAsset["quantity"] as? Double ?: 0.0) + asset.quantity

            currentAssets[existingAssetIndex] = existingAsset.toMutableMap().apply {
                this["id"] = asset.id
                this["quantity"] = updatedQuantity
                this["purchasePrice"] = purchasePrice
                this["purchaseAmount"] = purchaseAmount
                this["purchaseDate"] = purchaseDate
            }
        } else {
            currentAssets.add(
                mapOf(
                    "id" to asset.id,
                    "name" to asset.name,
                    "symbol" to asset.symbol,
                    "quantity" to asset.quantity,
                    "purchasePrice" to purchasePrice,
                    "purchaseAmount" to purchaseAmount,
                    "purchaseDate" to purchaseDate
                )
            )
        }

        userRef.update("Assets", currentAssets).await()
    }

    suspend fun sellUserAsset(userId: String, symbol: String, amount: Double, salePrice: Double) {
        if (amount <= 0) throw Exception("Quantity must be greater than zero")

        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        val currentAssets = snapshot.get("Assets") as? MutableList<Map<String, Any>> ?: mutableListOf()
        val assetIndex = currentAssets.indexOfFirst { it["symbol"] == symbol }

        if (assetIndex == -1) throw Exception("Asset $symbol not found for user $userId")

        val assetDetails = currentAssets[assetIndex]
        val currentQuantity = (assetDetails["quantity"] as? Double) ?: throw Exception("Invalid quantity for asset $symbol")

        if (currentQuantity < amount) throw Exception("Insufficient quantity for asset $symbol")
        if (amount < 0.001) throw Exception("Quantity amount is under the minimum level")

        val updatedQuantity = currentQuantity - amount
        if (updatedQuantity > 0) {
            currentAssets[assetIndex] = assetDetails.toMutableMap().apply {
                this["quantity"] = updatedQuantity
            }
        } else {
            currentAssets.removeAt(assetIndex)
        }

        val currentBalance = snapshot.getDouble("Balance") ?: 0.0
        val updatedBalance = currentBalance + (amount * salePrice)

        firestore.runBatch { batch ->
            batch.update(userRef, "Assets", currentAssets)
            batch.update(userRef, "Balance", updatedBalance)
        }.await()
    }
}
