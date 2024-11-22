package com.enesay.financialliteracy.data.datasource

import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.model.Trade.Transaction
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WalletDatasource @Inject constructor(val firestore: FirebaseFirestore) {

    // Kullanıcı bakiyesini al
    suspend fun getUserBalance(userId: String): Double {
        val document = firestore.collection("users").document(userId).get().await()
        return document.getDouble("balance") ?: 0.0
    }

    // Kullanıcı bakiyesini güncelle
    suspend fun updateUserBalance(userId: String, newBalance: Double) {
        firestore.collection("users").document(userId)
            .update("balance", newBalance).await()
    }

    // Kullanıcı cüzdanına varlık ekle
    suspend fun addAssetToWallet(userId: String, assetId: String, symbol: String, amount: Double, price: Double) {
        val assetData = hashMapOf(
            "symbol" to symbol,
            "amount" to amount,
            "purchasePrice" to price,
            "purchaseDate" to Timestamp.now()
        )
        firestore.collection("users").document(userId)
            .collection("wallet").document(assetId).set(assetData).await()
    }

    // Kullanıcı cüzdanını al
    suspend fun getUserWallet(userId: String): List<Asset> {
        val documents = firestore.collection("users").document(userId)
            .collection("wallet").get().await()
        return documents.mapNotNull { it.toObject(Asset::class.java) }
    }

    // Kullanıcının işlem geçmişine ekleme
    suspend fun addTransaction(userId: String, transactionType: String, symbol: String, amount: Double, price: Double) {
        val transactionData = hashMapOf(
            "type" to transactionType,
            "asset" to symbol,
            "amount" to amount,
            "price" to price,
            "date" to Timestamp.now()
        )
        firestore.collection("users").document(userId)
            .collection("transactions").add(transactionData).await()
    }

    // Kullanıcı işlem geçmişini al
    suspend fun getUserTransactions(userId: String): List<Transaction> {
        val documents = firestore.collection("users").document(userId)
            .collection("transactions").get().await()
        return documents.mapNotNull { it.toObject(Transaction::class.java) }
    }
}