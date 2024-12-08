package com.enesay.financialliteracy.data.repository

import com.enesay.financialliteracy.data.datasource.CryptoRemoteDataSource
import com.enesay.financialliteracy.model.Crypto.DataCrypto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

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
//    fun getLatestCrypto(symbol: String): Flow<X1> {
//        return cryptoRemoteDataSource.getLatestCrypto(symbol)
//            .catch {
//                Log.e("detailerror", it.message.toString())
//            }
//    }
}