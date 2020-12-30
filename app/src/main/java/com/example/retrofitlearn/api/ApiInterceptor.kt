package com.example.retrofitlearn.api

import com.example.retrofitlearn.utils.Constans.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()
        return chain.proceed(request)
    }
}