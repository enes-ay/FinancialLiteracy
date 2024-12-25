package com.enesay.financialliteracy.data.repository

import android.util.Log
import com.enesay.financialliteracy.model.Stock.search.StockResponse
import com.enesay.financialliteracy.model.Stock.search.StockSearchResponse
import com.enesay.financialliteracy.service.StockService
import retrofit2.Response
import javax.inject.Inject

class StockRepository @Inject constructor(val stockService: StockService) {

    suspend fun getStockQuotes(symbol: String): Response<StockResponse> {
        val response = stockService.getStockQuotes(symbol)

        if(response.isSuccessful){
            return response
        }
        else{
            throw Exception("Failed to fetch data: ${response.errorBody()?.string()}")
            Log.d("asset", response.errorBody().toString())
        }
    }

    suspend fun searchStockSymbol(symbol: String): Response<StockSearchResponse> {
        val response = stockService.searchStockSymbol(symbol)
        if (response.isSuccessful) {
            return response
        } else {
            throw Exception("Failed to fetch data: ${response.errorBody()?.string()}")
        }
    }
}
