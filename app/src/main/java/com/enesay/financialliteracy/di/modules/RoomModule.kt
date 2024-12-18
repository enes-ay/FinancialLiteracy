package com.enesay.financialliteracy.di.modules

import android.content.Context
import androidx.room.Room
import com.enesay.financialliteracy.data.local.AssetDao
import com.enesay.financialliteracy.data.local.AssetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAssetDatabase(@ApplicationContext context: Context): AssetDatabase {
        return Room.databaseBuilder(
            context,
            AssetDatabase::class.java,
            "asset_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAssetDao(database: AssetDatabase): AssetDao = database.assetDao()

}