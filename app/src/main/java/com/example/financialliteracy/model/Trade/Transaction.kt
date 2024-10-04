package com.example.financialliteracy.model.Trade

import com.google.firebase.Timestamp

data class Transaction(
    val type: String = "", // Buy or Sell
    val symbol: String = "",
    val amount: Double = 0.0,
    val price: Double = 0.0,
    val date: Timestamp? = null
)