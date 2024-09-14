package com.example.financialliteracy.data.repository

import com.example.financialliteracy.data.datasource.StockDataSource

class StockRepository (var stockDataSource: StockDataSource){

    suspend fun getStocks(symbols: List<String>) = stockDataSource.getStocks(symbols)
}