package com.enesay.financialliteracy.model.Trade

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesay.financialliteracy.model.Crypto.DataCrypto

@Entity(tableName = "assets")
data class Asset (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "symbol")
    val symbol: String = "",
    @ColumnInfo(name = "price")
    var price: Double = 0.0,
    @ColumnInfo(name = "quantity")
    var quantity: Double = 0.0,
    @ColumnInfo(name = "max_supply")
    val max_supply: Double,
    @ColumnInfo(name = "cmc_rank")
    val cmc_rank: Int,
    @ColumnInfo(name = "self_reported_market_cap")
    val self_reported_market_cap: Double,
    @ColumnInfo(name = "volume_24h")
    val volume_24h: Double,
    )

fun DataCrypto.toAsset(): Asset {
    return Asset(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        price = this.quote.USD.price,
        max_supply = this.max_supply,
        cmc_rank = this.cmc_rank,
        self_reported_market_cap = this.quote.USD.market_cap,
        volume_24h = this.quote.USD.volume_24h,
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