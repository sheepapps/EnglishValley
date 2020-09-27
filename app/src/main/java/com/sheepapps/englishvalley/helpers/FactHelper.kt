package com.sheepapps.englishvalley.helpers

import com.sheepapps.englishvalley.app.ValleyApp
import com.sheepapps.englishvalley.databases.CatsFacts
import com.sheepapps.englishvalley.repository.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactHelper(){

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun initFacts(){
        val cf = CatsFacts()
        cf.favoriteTime=0
        cf.category=16
        cf.favorite=0
        coroutineScope.launch {
            try {
                if (ValleyApp.getInstance().db.wordsDao().catsFacts.size<10){
                for(i in 0..10){
                    if (ValleyApp.getInstance().db.wordsDao().catsFacts.size>9){
                        return@launch
                    }
                val g= RetrofitRepository.getService().getFact()
                    cf.id= i.toLong()
                    cf.main=g.fact.toString()
                    ValleyApp.getInstance().db.wordsDao().addCatsFact(cf)

                }}
            }catch (e: Exception){
                e.message
            }
        }
    }
}