package com.sheepapps.englishvalley.API

import com.sheepapps.englishvalley.model.Facts
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
     @GET("facts/")
     fun getApi()
     : Call<Facts>
}