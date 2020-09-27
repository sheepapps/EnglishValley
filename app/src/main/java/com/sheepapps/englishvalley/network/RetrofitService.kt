package com.sheepapps.englishvalley.network

import com.sheepapps.englishvalley.network.data.GsonData
import retrofit2.http.GET

interface RetrofitService {
    @GET("facts/random")
    suspend fun getFact(): GsonData
}