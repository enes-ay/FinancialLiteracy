package com.enesay.financialliteracy.model.Stock.search

import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.model.Trade.StockAsset
import com.enesay.financialliteracy.model.Trade.toAsset

data class StockResponse(
    val c: Double,
    val d: Double,
    val dp: Double,
    val h: Double,
    val l: Double,
    val o: Double,
    val pc: Double,
    val t: Int
)

fun StockResponse.toStock(symbol: String): StockAsset {
    return StockAsset(
        id = symbol,
        name = symbol,
        symbol = symbol,
        currentPrice = this.c,
        lowPrice = this.l,
        highPrice = this.h,
        openPrice = this.o,
        dailyChange =this.dp,
        asset_type = 0
    )
}
