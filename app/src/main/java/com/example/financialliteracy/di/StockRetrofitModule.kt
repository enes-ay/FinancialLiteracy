package com.example.financialliteracy.di

import com.example.financialliteracy.data.datasource.StockDataSource
import com.example.financialliteracy.data.repository.StockRepository
import com.example.financialliteracy.retrofit.StockDao
import com.example.financialliteracy.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StockRetrofitModule {

    @Provides
    @Singleton
    fun provideStockRepository(stockDatasource: StockDataSource): StockRepository {
        return StockRepository(stockDatasource)
    }

    @Provides
    @Singleton
    fun provideStockDataSource(stockDao: StockDao): StockDataSource {
        return StockDataSource(stockDao)
    }

    @Provides
    @Singleton
    fun provideStockDao(): StockDao {
        return Constants.getStockDao()
    }


}