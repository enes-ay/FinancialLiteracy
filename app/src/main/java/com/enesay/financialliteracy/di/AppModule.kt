package com.enesay.financialliteracy.di

import android.content.Context
import com.enesay.financialliteracy.utils.DataStoreHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDataStoreHelper(
        @ApplicationContext context: Context
    ): DataStoreHelper {
        return DataStoreHelper(context)
    }
}