package com.example.financialliteracy.data.datasource

import com.example.financialliteracy.retrofit.StockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockDataSource (var stockDao: StockDao) {

    suspend fun getStocks(symbols:List<String>) = withContext(Dispatchers.IO){
        return@withContext stockDao.getStocks(symbols= symbols)
    }
}