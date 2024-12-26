package com.enesay.financialliteracy.model.Crypto

import androidx.annotation.Keep

@Keep
data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: String,
    val timestamp: String
)