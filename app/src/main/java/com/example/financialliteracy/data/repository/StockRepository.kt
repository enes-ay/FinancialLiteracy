package com.example.financialliteracy.data.repository

import com.example.financialliteracy.data.datasource.StockDataSource

class StockRepository (var stockDataSource: StockDataSource){

    suspend fun getStocks(symbol: List<String>) = stockDataSource.getStocks(symbol)
}