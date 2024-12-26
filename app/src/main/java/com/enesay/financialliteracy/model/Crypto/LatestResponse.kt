package com.enesay.financialliteracy.model.Crypto

import androidx.annotation.Keep

@Keep
data class LatestResponse(
    val `data`: List<DataCrypto>,
    val status: Status
)