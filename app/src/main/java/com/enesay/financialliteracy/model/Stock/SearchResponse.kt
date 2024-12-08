package com.enesay.financialliteracy.model.Stock

data class SearchResponse(
    val count: Int,
    val result: List<Result>
)