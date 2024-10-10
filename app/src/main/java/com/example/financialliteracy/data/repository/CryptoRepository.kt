package com.example.financialliteracy.data.repository

import com.example.financialliteracy.data.datasource.CryptoRemoteDataSource
import com.example.financialliteracy.model.DataCrypto
import com.example.financialliteracy.model.LatestResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class CryptoRepository @Inject constructor(val cryptoRemoteDataSource: CryptoRemoteDataSource) {

    fun getLatestCryptos(): Flow<List<DataCrypto>> {
        return cryptoRemoteDataSource.getLatestCryptos()
            .catch { e ->
                // Hata durumunda loglama veya başka işlemler yapılabilir
                println("Repository error: $e")
                emit(emptyList()) // Hata durumunda boş bir liste dönebiliriz
            }
    }
}