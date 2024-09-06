package com.example.financialliteracy.di

import com.example.financialliteracy.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Binds
    fun bindAuthRepository(auth: FirebaseAuth) = AuthRepository(auth)
}