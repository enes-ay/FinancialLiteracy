package com.enesay.financialliteracy.di

import com.enesay.financialliteracy.data.datasource.WalletDatasource
import com.enesay.financialliteracy.data.repository.AuthRepository
import com.enesay.financialliteracy.data.repository.TradeRepository
import com.enesay.financialliteracy.data.repository.WalletRepository
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
    fun provideTradeRepository(firestore: FirebaseFirestore, walletRepository: WalletRepository, authRepository: AuthRepository): TradeRepository {
        return TradeRepository(firestore, authRepository, walletRepository)
    }

    @Provides
    @Singleton
    fun provideWalletRepository(firestore: FirebaseFirestore): WalletRepository {
        return WalletRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, walletRepository: WalletRepository, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepository(firebaseAuth, walletRepository, firestore)
    }
}