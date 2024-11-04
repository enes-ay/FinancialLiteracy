package com.enesay.financialliteracy.model

data class LatestResponse(
    val `data`: List<DataCrypto>,
    val status: Status
)