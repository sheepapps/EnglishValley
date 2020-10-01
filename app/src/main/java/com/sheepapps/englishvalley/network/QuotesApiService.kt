package com.sheepapps.englishvalley.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.random.org/"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface QuotesApiService {
    @GET("strings/?num=10&len=10&digits=on&unique=on&format=plain")
    fun getQuotes(): Deferred<String>
}

object QuotesApi{
    val retrofitService: QuotesApiService by lazy {
        retrofit.create(QuotesApiService::class.java)
    }
}



