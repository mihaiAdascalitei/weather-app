package com.madsq.weather.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * Created by mihai.adascalitei on 25.10.2022.
 */

class RetrofitBuilder {
    //default retrofit class with basic interceptors and converters
    private val loginInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .build()
    }

    private fun build(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    companion object {
        private var INSTANCE: RetrofitBuilder? = null
        private const val BASE_URL = "https://api.weather.gov/"


        fun build(): Retrofit = (INSTANCE ?: RetrofitBuilder()).build()
    }
}