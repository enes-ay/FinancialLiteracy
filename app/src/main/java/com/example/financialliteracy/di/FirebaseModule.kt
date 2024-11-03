package com.example.financialliteracy.di

import com.example.financialliteracy.data.datasource.WalletDatasource
import com.example.financialliteracy.data.repository.TradeRepository
import com.example.financialliteracy.data.repository.WalletRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDataSource(firestore: FirebaseFirestore): WalletDatasource {
        return WalletDatasource(firestore)
    }
    @Provides
    @Singleton
    fun provideTradeRepository(firestore: FirebaseFirestore): TradeRepository {
        return TradeRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseDataSource: WalletDatasource): WalletRepository {
        return WalletRepository(firebaseDataSource)
    }
}