package com.example.financialliteracy.model.Trade

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class Asset(
    @PropertyName("symbol") val symbol: String = "",
    @PropertyName("amount") val amount: Double = 0.0,
    @PropertyName("purchase_price") val purchasePrice: Double = 0.0,
    @PropertyName("purchase_date") val purchaseDate: Timestamp? = null
)