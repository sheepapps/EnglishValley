@file:Suppress("DEPRECATION")

package com.sheepapps.englishvalley.API

import android.widget.Toast
import com.google.gson.GsonBuilder
import com.sheepapps.englishvalley.app.ValleyApp
import com.sheepapps.englishvalley.databases.Quote
import com.sheepapps.englishvalley.helpers.Constants.Categories.CATEGORY_QUOTE
import com.sheepapps.englishvalley.model.Facts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val list = mutableListOf<Quote>()
    private val api = Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(APIService::class.java)

    fun getExtra(): MutableList<Quote> {
        if (list.isEmpty()){
            GlobalScope.launch {
                lateinit var fact : Facts
                if (api.getApi().awaitResponse().isSuccessful) fact = api.getApi().awaitResponse().body() as Facts

                val fct = if (api.getApi().awaitResponse().isSuccessful) {
                    api.getApi().awaitResponse().body() as Facts
                } else {
                    null
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(ValleyApp.getInstance(),"Facts about cats add to quote directory",Toast.LENGTH_LONG).show()
                    for (item in 1..7) {
                        list.add( Quote.getQuote(fact.all[item].text,
                                "" + fact.all[item].user.name.first + " " +
                                        fact.all[item].user.name.last, CATEGORY_QUOTE))
                    }}
            }
        }
        return list
    }
}