package com.enesay.financialliteracy.service

import com.enesay.financialliteracy.model.Stock.ApiResponse
import com.enesay.financialliteracy.model.Stock.SearchResponse
import com.enesay.financialliteracy.model.Stock.StockResponse
import com.enesay.financialliteracy.model.StockModels.Stock
import com.enesay.financialliteracy.model.StockModels.StockApiResponse
import com.enesay.financialliteracy.utils.Constants
import com.enesay.financialliteracy.utils.Constants.API_KEY_STOCK
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {
    @GET("quote")
    suspend fun getStockQuotes(
        @Query("symbol") symbols: String,
        @Query("apikey") apiKey: String = API_KEY_STOCK
    ): Map<String, Stock>
}