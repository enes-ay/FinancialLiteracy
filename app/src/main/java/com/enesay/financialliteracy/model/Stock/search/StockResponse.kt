package com.enesay.financialliteracy.model.Stock.search

import com.enesay.financialliteracy.model.Trade.Asset
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

fun StockResponse.toStock(symbol: String): Asset {
    return Asset(
        id = symbol,
        name = "",
        symbol = "",
        price = this.c,
        max_supply = 0.0,
        cmc_rank = 0,
        self_reported_market_cap = 0.0,
        volume_24h = 0.0,
        asset_type = 0
    )
}
