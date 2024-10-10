package com.example.financialliteracy.data.datasource

import com.example.financialliteracy.model.DataCrypto
import com.example.financialliteracy.model.LatestResponse
import com.example.financialliteracy.retrofit.CryptoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoRemoteDataSource @Inject constructor(val cryptoService: CryptoService) {

    fun getLatestCryptos(): Flow<List<DataCrypto>> = flow {
        val response = cryptoService.getLatestCryptos()

        // `status` alanını kontrol ederek yanıtın başarılı olup olmadığını doğruluyoruz
        if (response.status.error_code == 0) {
            response.data.let {
                emit(it)
            }
        } else {
            // Hata mesajını içeren bir Exception fırlatabiliriz
            throw Exception("Failed to fetch data: ${response.status.error_message}")
        }
    }.catch { e ->
        // Hata durumunda yapılacak işlemler
        println("Error: $e")
    }
}
