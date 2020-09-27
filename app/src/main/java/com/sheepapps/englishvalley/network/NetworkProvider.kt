package com.sheepapps.englishvalley.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkProvider {

    private const val url = "https://cat-fact.herokuapp.com"
    private fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    val quoteApi: FactApi = retrofit().create(FactApi::class.java)

}