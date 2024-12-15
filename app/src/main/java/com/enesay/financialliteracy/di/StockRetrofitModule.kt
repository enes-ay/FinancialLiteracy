package com.enesay.financialliteracy.di

import com.enesay.financialliteracy.data.repository.StockRepository
import com.enesay.financialliteracy.service.StockInterceptor
import com.enesay.financialliteracy.service.StockService
import com.enesay.financialliteracy.utils.Constants
import com.enesay.financialliteracy.utils.Constants.API_KEY_STOCK
import com.enesay.financialliteracy.utils.Constants.BASE_URL_STOCK
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StockRetrofitModule {

    @Provides
    @Singleton
    @Named("stockService")
    fun provideStockService(): StockService {
        val apiKey = API_KEY_STOCK
        val client = OkHttpClient.Builder()
            .addInterceptor(StockInterceptor(apiKey))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_STOCK)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockService::class.java)
    }

    @Provides
    @Singleton
    fun provideStockRepository(@Named("stockService") apiService: StockService): StockRepository {
        return StockRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideStockDao(): StockService {
        return Constants.getStockDao()
    }
}