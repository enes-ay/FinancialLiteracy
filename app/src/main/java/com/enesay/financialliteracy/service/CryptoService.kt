package com.enesay.financialliteracy.service

import com.enesay.financialliteracy.model.Crypto.LatestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoService {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getLatestCryptos(
        @Query("start") start: Int,
        @Query("limit") limit: Int,
        @Query("convert") convert: String = "USD"
    ): Response<LatestResponse>

//    @GET("/v2/cryptocurrency/quotes/latest")
//    suspend fun getLatestCrypto(
//        @Query("id") symbol: String = "1",
//        @Query("convert") convert: String = "USD"
//    ): DetailResponse
}
