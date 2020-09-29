package com.sheepapps.englishvalley.helpers

import com.sheepapps.englishvalley.beckend.Server
import com.sheepapps.englishvalley.databases.Quote
import kotlinx.coroutines.runBlocking

class QuoteHelper {
    var name = ""
    var myList: ArrayList<Quote> = ArrayList()

    fun getList() {
        runBlocking {
            for (i in 0..5) {
                name = Server.getRandom()
                val stringNames = name.split("\n")
                myList.add( Quote())
                myList[i].main = stringNames[0]
                myList[i].example = stringNames[1]
                myList[i].category = Constants.Categories.CATEGORY_QUOTE
                myList[i].extra = stringNames[2]
            }

        }
    }
    fun addList(quoteList:List<Quote>){
        myList.addAll(quoteList)
    }

}