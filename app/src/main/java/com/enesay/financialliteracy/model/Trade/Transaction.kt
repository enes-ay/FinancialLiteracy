package com.enesay.financialliteracy.model.Trade

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
data class Transaction(
    val type: String = "", // Buy or Sell
    val symbol: String = "",
    val amount: Double = 0.0,
    val price: Double = 0.0,
    val date: Timestamp? = null
)