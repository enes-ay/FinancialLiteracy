package com.enesay.financialliteracy.model

data class SearchResponse(
    val count: Int,
    val result: List<Result>
)