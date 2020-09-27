package com.sheepapps.englishvalley.repository

import com.sheepapps.englishvalley.network.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {

    private lateinit var service: RetrofitService

    fun getService(): RetrofitService {
        if (!::service.isInitialized) {
            val retofit = Retrofit.Builder()
                    .baseUrl("https://cat-fact.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            service = retofit.create(RetrofitService::class.java)
        }
        return service
    }
}