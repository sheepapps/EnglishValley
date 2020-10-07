package com.sheepapps.englishvalley.helpers

import com.sheepapps.englishvalley.data.QuoteRetrofit
import retrofit2.http.GET

interface Api {

    @GET("facts/random?amount=10")
    suspend fun getQuotes(): List<QuoteRetrofit>
}