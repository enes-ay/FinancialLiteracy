package com.example.financialliteracy.retrofit

import com.example.financialliteracy.model.StockResponse
import com.example.financialliteracy.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StockDao {
    // https://api.stockdata.org/v1/data/quote?symbols=AAPL%2CTSLA%2CMSFT&api_token=YOUR_API_TOKEN

    @GET("data/quote?symbols=AAPL%2CTSLA%2CMSFT&api_token=YOUR_API_TOKEN")
    suspend fun getStocks(
        @Query("api_token") apiToken: String = Constants.API_KEY,
        @Query("symbols") symbols: List<String>
    ) : StockResponse
}