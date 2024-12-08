package com.enesay.financialliteracy.model.Trade

import com.enesay.financialliteracy.model.Crypto.DataCrypto

data class Asset(
    val id: String = "",
    val name: String = "",
    val symbol: String = "",
    val price: Double = 0.0,
    val quantity: Double = 0.0

)

fun DataCrypto.toAsset(): Asset {
    return Asset(
        id = this.id.toString(),
        name = this.name,
        symbol = this.symbol,
        price = this.quote.USD.price
    )
}

// DataStock benzeri bir sınıf geldiğinde
//fun DataStock.toAsset(): Asset {
//    return Asset(
//        id = this.id,
//        name = this.name,
//        symbol = this.symbol,
//        price = this.currentPrice
//    )
//}