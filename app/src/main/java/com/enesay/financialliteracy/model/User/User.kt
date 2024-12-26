package com.enesay.financialliteracy.model.User

import androidx.annotation.Keep

@Keep
    data class User(
        val name: String = "",
        val surname: String = "",
        val email: String = "",
        val balance: Double = 0.0,
        val assets: Map<String, Map<String, Any>> = emptyMap()
    )