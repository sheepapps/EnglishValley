package com.sheepapps.englishvalley.network

import retrofit2.http.GET
import retrofit2.http.Query

interface FactApi {

    @GET("/facts/random")
    suspend fun getFacts(@Query("amount") amount: Int): List<Fact>

}