package com.example.financialliteracy.retrofit

import com.example.financialliteracy.model.StockResponse
import com.example.financialliteracy.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface StockDao {

    @GET("https://api.stockdata.org/v1/data/quote HTTP/1.1")
    fun getStocks(
        @Query("api_token") apiToken: String = Constants.API_KEY,
        @Query("symbols") symbols: String = "TSLA"
    ) : StockResponse
}