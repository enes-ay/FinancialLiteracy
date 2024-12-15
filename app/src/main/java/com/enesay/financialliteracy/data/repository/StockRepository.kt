package com.enesay.financialliteracy.data.repository

import com.enesay.financialliteracy.model.StockModels.Stock
import com.enesay.financialliteracy.service.StockService
import javax.inject.Inject

class StockRepository @Inject constructor(val api: StockService) {

    suspend fun getStockQuotes(symbols: String): List<Stock> {
        val response = api.getStockQuotes(symbols)
        return response.values.toList() // Convert Map values to a List
    }
}
