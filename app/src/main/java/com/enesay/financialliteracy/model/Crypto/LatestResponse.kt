package com.enesay.financialliteracy.model.Crypto

import com.enesay.financialliteracy.model.Stock.Status

data class LatestResponse(
    val `data`: List<DataCrypto>,
    val status: Status
)