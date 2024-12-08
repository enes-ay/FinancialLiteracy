package com.enesay.financialliteracy.service

import com.enesay.financialliteracy.model.Stock.ApiResponse
import com.enesay.financialliteracy.model.Stock.SearchResponse
import com.enesay.financialliteracy.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface StockDao {
    @GET("quote")
    suspend fun getStocks(
        @Query("symbol") symbols: String,
        @Query("token") apiKey: String = Constants.API_KEY
    ): ApiResponse

    @GET("search")
    suspend fun searchStock(
        @Query("q") query: String,
        @Query("token") apiKey: String = Constants.API_KEY
    ): SearchResponse
}