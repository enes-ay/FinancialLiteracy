package com.enesay.financialliteracy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enesay.financialliteracy.model.Trade.Asset

@Database(entities = [Asset::class], version = 1, exportSchema = false)
abstract class AssetDatabase: RoomDatabase(){
    abstract fun assetDao() : AssetDao
}