package com.example.financialliteracy.di

import com.example.financialliteracy.retrofit.AuthInterceptor
import com.example.financialliteracy.retrofit.CryptoDao
import com.example.financialliteracy.utils.Constants.API_KEY_CRYPTO
import com.example.financialliteracy.utils.Constants.BASE_URL_CRYPTO
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
    fun provideCryptoRemoteDataSource(apiService: CryptoDao): CryptoRemoteDataSource {
        return CryptoRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(): CryptoDao {
        val apiKey = API_KEY_CRYPTO
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(apiKey))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_CRYPTO)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoDao::class.java)
    }


}
