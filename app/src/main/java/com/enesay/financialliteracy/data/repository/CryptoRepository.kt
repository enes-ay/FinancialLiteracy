package com.enesay.financialliteracy.data.repository

import com.enesay.financialliteracy.model.Crypto.DataCrypto
import com.enesay.financialliteracy.service.CryptoService
import javax.inject.Inject

//@Singleton
class CryptoRepository @Inject constructor(
    val cryptoService: CryptoService) {

    suspend fun getLatestCryptos(start: Int, limit: Int): List<DataCrypto> {
        val response = cryptoService.getLatestCryptos(start, limit)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Failed to fetch data: ${response.errorBody()?.string()}")
        }
    }
}