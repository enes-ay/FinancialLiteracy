package com.enesay.financialliteracy.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun bindAuthRepository(auth: FirebaseAuth) = AuthRepository(auth)
}