package com.example.financialliteracy.data.repository

import com.example.financialliteracy.model.Trade.Asset
import com.example.financialliteracy.model.Trade.Transaction
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TradeRepository @Inject constructor(val firestore: FirebaseFirestore)  {
    private val userAssetsCollection = firestore.collection("Users").document("userId").collection("Assets")
    private val transactionsCollection = firestore.collection("Users").document("userId").collection("Transactions")

    suspend fun getAssets(): List<Asset> {
        return userAssetsCollection.get().await().toObjects(Asset::class.java)
    }

    fun buyAsset(asset: Asset, amount: Double) {
        val transaction = Transaction(type = "BUY", amount = amount, price = asset.price, symbol = asset.symbol, date = Timestamp.now())
        transactionsCollection.add(transaction)

        userAssetsCollection.document(asset.id).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val updatedQuantity = document.getDouble("quantity")!! + amount
                userAssetsCollection.document(asset.id).update("quantity", updatedQuantity)
            } else {
                userAssetsCollection.document(asset.id).set(asset.copy(quantity = amount))
            }
        }
    }
    fun sellAsset(asset: Asset, amount: Double) {
        val transaction = Transaction(type = "SELL", amount = amount)
        transactionsCollection.add(transaction)

        userAssetsCollection.document(asset.id).get().addOnSuccessListener { document ->
            if (document.exists()) {
                val updatedQuantity = document.getDouble("quantity")!! - amount
                userAssetsCollection.document(asset.id).update("quantity", updatedQuantity)
            }
        }
    }
}