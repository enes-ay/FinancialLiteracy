package com.enesay.financialliteracy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM assets")
    fun getAllCryptos(): Flow<List<AssetLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptos: List<AssetLocal>)

    @Query("DELETE FROM assets")
    suspend fun deleteAll()
}