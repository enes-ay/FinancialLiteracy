package com.enesay.financialliteracy.di

import com.enesay.financialliteracy.data.datasource.StockDataSource
import com.enesay.financialliteracy.data.repository.StockRepository
import com.enesay.financialliteracy.service.StockDao
import com.enesay.financialliteracy.utils.Constants
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