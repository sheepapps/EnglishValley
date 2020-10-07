package com.sheepapps.englishvalley.helpers

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sheepapps.englishvalley.data.QuoteRetrofit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesLoader(private val responseLiveData: MutableLiveData<Event<List<QuoteRetrofit>>>){

    var api: Api = RetrofitService.retrofitService()

    init {
        request {
            api.getQuotes()
        }
    }

    private fun <T> request( request: suspend () -> List<T>) {

        responseLiveData.postValue(Event.loading())
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO){

            val response : List<T>
            try {
                response = request.invoke()
                responseLiveData.postValue(Event.success((response as List<QuoteRetrofit>)))

            } catch (e: Exception) {
                responseLiveData.postValue(Event.error(null))
            }
        }

    }



}



