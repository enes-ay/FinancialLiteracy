package com.example.financialliteracy.data.datasource

import com.example.financialliteracy.service.StockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockDataSource (var stockDao: StockDao) {

    suspend fun getStocks(symbols:String) = withContext(Dispatchers.IO){
        return@withContext stockDao.getStocks(symbols= symbols)
    }

    suspend fun searchStock(query:String) = withContext(Dispatchers.IO){
        return@withContext stockDao.searchStock(query = query)
    }
}