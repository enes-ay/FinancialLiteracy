package com.example.financialliteracy.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-CMC_PRO_API_KEY", apiKey) // API anahtarını özel başlıkla ekliyoruz
            .build()
        return chain.proceed(request)
    }
}