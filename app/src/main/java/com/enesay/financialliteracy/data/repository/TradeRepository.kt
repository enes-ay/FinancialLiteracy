package com.enesay.financialliteracy.data.repository

import com.enesay.financialliteracy.common.Resource
import com.enesay.financialliteracy.model.Trade.Asset
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TradeRepository @Inject constructor(val firestore: FirebaseFirestore, val authRepository: AuthRepository, val walletRepository: WalletRepository)  {
    private val userAssetsCollection = firestore.collection("Users").document("userId").collection("Assets")
    private val transactionsCollection = firestore.collection("Users").document("userId").collection("Transactions")
    private val userId = authRepository.getCurrentUserId() ?: ""

    suspend fun getAssets(): List<Asset> {
        return userAssetsCollection.get().await().toObjects(Asset::class.java)
    }

    suspend fun buyAsset(asset: Asset, amount: Double): Resource<String> {

        val totalCost = asset.price * amount
        // Kullanıcının mevcut bakiyesini kontrol et
        val currentBalance = walletRepository.getUserBalance(userId)
        return if (currentBalance >= totalCost) {
            // Yeterli bakiye varsa, bakiye ve varlık listesini güncelle
            walletRepository.updateUserBalance(userId, currentBalance - totalCost)
            walletRepository.updateUserAsset(userId, asset.symbol, amount)
            Resource.Success("Asset purchased successfully")
        } else {
            Resource.Error(Exception("Insufficient balance"))
        }
    }
    suspend fun sellAsset(asset: Asset, amount: Double): Resource<String> {
        val userId = authRepository.getCurrentUserId() ?: return Resource.Error(Exception("User not logged in"))
        val userAssets = walletRepository.getUserAssets(userId)
        val assetQuantity = userAssets[asset.symbol] ?: 0.0

        return if (assetQuantity >= amount) {
            val totalRevenue = asset.price * amount
            val currentBalance = walletRepository.getUserBalance(userId)

            walletRepository.updateUserBalance(userId, currentBalance + totalRevenue)
            walletRepository.updateUserAsset(userId, asset.symbol, -amount)
            Resource.Success("Asset sold successfully")
        } else {
            Resource.Error(Exception("Insufficient asset quantity"))
        }
    }
}