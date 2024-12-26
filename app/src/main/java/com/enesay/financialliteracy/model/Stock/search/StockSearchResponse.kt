package com.enesay.financialliteracy.model.Stock.search

import androidx.annotation.Keep

@Keep
data class StockSearchResponse(
    val count: Int,
    val result: List<Result>
)
@Keep
data class Result(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val type: String
)