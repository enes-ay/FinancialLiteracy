package com.enesay.financialliteracy.service

import com.enesay.financialliteracy.model.LatestResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoService {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getLatestCryptos(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("convert") convert: String = "USD"
    ): LatestResponse

//    @GET("/v2/cryptocurrency/quotes/latest")
//    suspend fun getLatestCrypto(
//        @Query("id") symbol: String = "1",
//        @Query("convert") convert: String = "USD"
//    ): DetailResponse
}
