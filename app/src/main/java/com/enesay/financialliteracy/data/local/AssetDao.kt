package com.enesay.financialliteracy.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enesay.financialliteracy.model.Trade.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

//    @Query("SELECT * FROM assets")
//    fun getAllCryptos(): PagingSource<Int, Asset>

    @Query("SELECT * FROM assets ORDER BY cmc_rank ASC")
    fun getAllCryptos(): PagingSource<Int, Asset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptos: List<Asset>)

    @Query("DELETE FROM assets")
    suspend fun deleteAll()
}