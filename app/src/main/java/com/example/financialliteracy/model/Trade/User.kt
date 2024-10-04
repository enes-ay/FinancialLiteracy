package com.example.financialliteracy.model.Trade

import com.example.financialliteracy.ui.presentation.Portfolio.Asset

data class User(
    val id: String = "",
    val balance: Double = 0.0,
    val wallet: List<Asset> = emptyList(),
    val transactions: List<Transaction> = emptyList()
)