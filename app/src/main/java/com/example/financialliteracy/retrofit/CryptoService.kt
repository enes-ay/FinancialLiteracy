package com.example.financialliteracy.retrofit

import com.example.financialliteracy.model.LatestResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoService {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getLatestCryptos(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("convert") convert: String = "USD"
    ): LatestResponse
}
