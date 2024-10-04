package com.example.financialliteracy.data.repository

import com.example.financialliteracy.data.datasource.WalletDatasource
import com.example.financialliteracy.ui.presentation.Portfolio.Asset
import javax.inject.Inject

class WalletRepository @Inject constructor(val walletDatasource: WalletDatasource) {

    suspend fun getUserBalance(userId: String): Double {
        return walletDatasource.getUserBalance(userId)
    }

    suspend fun updateUserBalance(userId: String, newBalance: Double) {
        walletDatasource.updateUserBalance(userId, newBalance)
    }

    suspend fun addAssetToWallet(userId: String, assetId: String, symbol: String, amount: Double, price: Double) {
        walletDatasource.addAssetToWallet(userId, assetId, symbol, amount, price)
    }

    suspend fun getUserWallet(userId: String): List<Asset> {
        return walletDatasource.getUserWallet(userId)
    }

    suspend fun addTransaction(userId: String, transactionType: String, symbol: String, amount: Double, price: Double) {
        walletDatasource.addTransaction(userId, transactionType, symbol, amount, price)
    }

    suspend fun getUserTransactions(userId: String): List<Transaction> {
        return walletDatasource.getUserTransactions(userId)
    }
}