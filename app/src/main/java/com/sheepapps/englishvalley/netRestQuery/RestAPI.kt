package com.sheepapps.englishvalley.netRestQuery

import com.sheepapps.englishvalley.netRestQuery.entity.Info
import com.sheepapps.englishvalley.netRestQuery.entity.ListInfo
import io.reactivex.Observable
import retrofit2.http.GET

interface RestAPI {
    @GET("facts")
    fun loadInfo(): Observable<ListInfo>
}