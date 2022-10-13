package com.rafaelmfer.githubrepo.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

private const val TIMEOUT_30 = 30L

class HTTPClient {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_30, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_30, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_30, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T : Any> create(clazz: KClass<T>, baseUrl: String): T = provideRetrofit(baseUrl).create(clazz.java)
}