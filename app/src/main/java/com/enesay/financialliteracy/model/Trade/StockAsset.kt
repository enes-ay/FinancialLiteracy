package com.enesay.financialliteracy.model.Trade


import com.enesay.financialliteracy.model.Stock.search.Result

data class StockAsset(
    var id: String,
    var name: String = "",
    val symbol: String = "",
    var currentPrice: Double = 0.0,
    var quantity: Double = 0.0,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val dailyChange: Double,
    val asset_type: Int,
    )

fun Result.toAsset(): StockAsset {
    return StockAsset(
        id = this.symbol,
        name = this.displaySymbol,
        symbol = this.symbol,
        currentPrice = 0.0,
        highPrice = 0.0,
        lowPrice = 0.0,
        dailyChange = 0.0,
        openPrice = 0.0,
        asset_type = 0
    )
}