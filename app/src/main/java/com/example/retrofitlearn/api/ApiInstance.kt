package com.example.retrofitlearn.api

import com.example.retrofitlearn.utils.Constans.Companion.BASE_URL
import com.example.retrofitlearn.utils.Constans.Companion.BASE_URL_SOAL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiEndpoint by lazy {
        retrofit.create(ApiEndpoint::class.java)
    }


    // ini untuk soal netha
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(ApiInterceptor())
    }.build()

    private val soalRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_SOAL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val soalApi: ApiEndpoint by lazy {
        soalRetrofit.create(ApiEndpoint::class.java)
    }
}