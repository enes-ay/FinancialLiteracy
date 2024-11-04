package com.enesay.financialliteracy.di

import com.enesay.financialliteracy.data.datasource.CryptoRemoteDataSource
import com.enesay.financialliteracy.data.repository.CryptoRepository
import com.enesay.financialliteracy.service.AuthInterceptor
import com.enesay.financialliteracy.service.CryptoService
import com.enesay.financialliteracy.utils.Constants.API_KEY_CRYPTO
import com.enesay.financialliteracy.utils.Constants.BASE_URL_CRYPTO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(remoteDataSource: CryptoRemoteDataSource): CryptoRepository {
        return CryptoRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCryptoRemoteDataSource(apiService: CryptoService): CryptoRemoteDataSource {
        return CryptoRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(): CryptoService {
        val apiKey = API_KEY_CRYPTO
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(apiKey))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_CRYPTO)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoService::class.java)
    }


}
