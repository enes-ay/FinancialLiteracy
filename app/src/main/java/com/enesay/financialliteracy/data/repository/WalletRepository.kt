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

        // Fetch the "Assets" field as a map of symbol -> details
        val assetsMap = snapshot.get("Assets") as? Map<String, Map<String, Any>> ?: emptyMap()
        Log.d("map", assetsMap.toString())

        // Convert the assets map to a list of Asset objects
        return assetsMap.map { (symbol, details) ->
            Asset(
                id = details["id"].toString().toInt(), // Symbol as the unique identifier
                name = details["name"] as? String ?: "Unknown", // Get the name or default to "Unknown"
                symbol = symbol,
                price = (details["purchasePrice"] as? Double) ?: 0.0, // Get the purchase price or default to 0.0
                quantity = (details["quantity"] as? Double) ?: 0.0, // Get the quantity or default to 0.0
                max_supply = 0.0,
                cmc_rank = 0,
                self_reported_market_cap = 0.0,
                volume_24h = 0.0,
            )
        }
    }

    suspend fun addUserAsset(userId: String, asset: Asset, purchasePrice: Double, purchaseAmount: Double, purchaseDate: String) {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        // Retrieve existing assets
        val currentAssets = snapshot.get("Assets") as? MutableMap<String, Map<String, Any>> ?: mutableMapOf()

        // Check if asset already exists
        val existingAsset = currentAssets[asset.symbol]

        // Update or add asset data
        val updatedAsset = if (existingAsset != null) {
            val currentQuantity = (existingAsset["quantity"] as? Double) ?: 0.0
            val newQuantity = currentQuantity + asset.quantity

            existingAsset.toMutableMap().apply {
                this["id"] = asset.id
                this["quantity"] = newQuantity
                this["purchasePrice"] = purchasePrice // Update to reflect the latest price
                this["purchaseAmount"] = purchaseAmount
                this["purchaseDate"] = purchaseDate
            }
        } else {
            mapOf(
                "id" to asset.id,
                "name" to asset.name,
                "symbol" to asset.symbol,
                "quantity" to asset.quantity,
                "purchasePrice" to purchasePrice,
                "purchaseAmount" to purchaseAmount,
                "purchaseDate" to purchaseDate,
            )
        }

        // Update the asset in the database
        currentAssets[asset.symbol] = updatedAsset
        userRef.update("Assets", currentAssets).await()
    }


    suspend fun sellUserAsset(userId: String, symbol: String, amount: Double, salePrice: Double) {
        val userRef = firestore.collection("Users").document(userId)
        val snapshot = userRef.get().await()

        // Fetch current assets
        val currentAssets = snapshot.get("Assets") as? MutableMap<String, Map<String, Any>> ?: mutableMapOf()

        // Check if the asset exists
        val assetDetails = currentAssets[symbol] ?: throw Exception("Asset $symbol not found for user $userId")

        // Extract the current quantity
        val currentQuantity = (assetDetails["quantity"] as? Double) ?: throw Exception("Invalid quantity for asset $symbol")

        // Ensure the user has enough quantity to sell
        if (currentQuantity < amount) throw Exception("Insufficient quantity for asset $symbol")

        // Update the asset's quantity
        val updatedQuantity = currentQuantity - amount
        if (updatedQuantity > 0) {
            currentAssets[symbol] = assetDetails.toMutableMap().apply {
                this["quantity"] = updatedQuantity
            }
        } else {
            // If quantity is zero, remove the asset entirely
            currentAssets.remove(symbol)
        }

        // Update user balance by adding sale proceeds
        val currentBalance = snapshot.getDouble("Balance") ?: 0.0
        val updatedBalance = currentBalance + (amount * salePrice)

        // Write updated data to Firestore
        firestore.runBatch { batch ->
            batch.update(userRef, "Assets", currentAssets)
            batch.update(userRef, "Balance", updatedBalance)
        }
    }
}