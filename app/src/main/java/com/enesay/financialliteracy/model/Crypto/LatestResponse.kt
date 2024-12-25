package com.enesay.financialliteracy.model.Crypto

data class LatestResponse(
    val `data`: List<DataCrypto>,
    val status: Status
)