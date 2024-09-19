package com.example.financialliteracy.data.repository

import com.example.financialliteracy.data.datasource.StockDataSource

class StockRepository (var stockDataSource: StockDataSource){

    suspend fun getStocks(symbols: String) = stockDataSource.getStocks(symbols)
    suspend fun searchStock(query: String) = stockDataSource.searchStock(query)
}