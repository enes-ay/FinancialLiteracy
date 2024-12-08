package com.enesay.financialliteracy.model.Stock

data class Data(
    val `52_week_high`: Double,
    val `52_week_low`: Double,
    val currency: String,
    val day_change: Double,
    val day_high: Double,
    val day_low: Double,
    val day_open: Double,
    val exchange_long: Any,
    val exchange_short: Any,
    val is_extended_hours_price: Boolean,
    val last_trade_time: String,
    val market_cap: Any,
    val mic_code: String,
    val name: String,
    val previous_close_price: Double,
    val previous_close_price_time: String,
    val price: Double,
    val ticker: String,
    val volume: Int
)