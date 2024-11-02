package com.example.financialliteracy.service

import com.example.financialliteracy.model.ApiResponse
import com.example.financialliteracy.model.SearchResponse
import com.example.financialliteracy.utils.Constants
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