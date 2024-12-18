package com.enesay.financialliteracy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AssetLocal::class], version = 1, exportSchema = false)
abstract class AssetDatabase: RoomDatabase(){
    abstract fun assetDao() : AssetDao
}