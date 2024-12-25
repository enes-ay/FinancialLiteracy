package com.enesay.financialliteracy.service

import com.enesay.financialliteracy.model.Stock.search.StockResponse
import com.enesay.financialliteracy.model.Stock.search.StockSearchResponse
import com.enesay.financialliteracy.utils.Constants.API_KEY_STOCK
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {
    @GET("quote")
    suspend fun getStockQuotes(
        @Query("symbol") symbols: String,
        @Query("token") apiKey: String = API_KEY_STOCK
    ): Response<StockResponse>

    @GET("search")
    suspend fun searchStockSymbol(
        @Query("q") symbols: String,
        @Query("exchange") exchange: String = "US",
        @Query("token") apiKey: String = API_KEY_STOCK
    ): Response<StockSearchResponse>


}