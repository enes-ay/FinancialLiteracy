package com.enesay.financialliteracy.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetLocal (
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "symbol")
    val symbol: String = "",
    @ColumnInfo(name = "price")
    var price: Double = 0.0,
    @ColumnInfo(name = "quantity")
    var quantity: Double = 0.0
)