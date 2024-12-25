package com.enesay.financialliteracy.model.Stock.search

data class StockSearchResponse(
    val count: Int,
    val result: List<Result>
)

data class Result(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val type: String
)