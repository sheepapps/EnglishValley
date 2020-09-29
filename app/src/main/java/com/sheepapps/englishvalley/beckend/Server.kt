package com.sheepapps.englishvalley.beckend

import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

object Server {
    val api = Retrofit.Builder()
            .baseUrl("https://www.random.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(Api::class.java)

    suspend fun getRandom():String{
        try{
            val response = api.getRandom()
            return response.string()
        }
        catch (e: IOException){
            e.printStackTrace()
        }
        catch(e: HttpException){
            e.printStackTrace()
        }
        return ""

    }
}