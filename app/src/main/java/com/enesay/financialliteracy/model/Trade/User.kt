package com.enesay.financialliteracy.model.Trade


data class User(
    val id: String = "",
    val balance: Double = 0.0,
    val wallet: List<Asset> = emptyList(),
    val transactions: List<Transaction> = emptyList()
)