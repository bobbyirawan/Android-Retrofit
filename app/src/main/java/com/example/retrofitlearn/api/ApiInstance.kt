package com.example.retrofitlearn.api

import com.example.retrofitlearn.utils.Constans.Companion.BASE_URL
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
}