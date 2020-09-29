package com.sheepapps.englishvalley.beckend

import okhttp3.ResponseBody
import retrofit2.http.GET

interface Api {
    @GET("strings/?num=10&len=10&digits=on&unique=on&format=plain")
    suspend fun getRandom(): ResponseBody
}