package com.sheepapps.englishvalley.network
import com.sheepapps.englishvalley.databases.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val quoteList = mutableListOf<Quote>()

private val uiScope = CoroutineScope(Dispatchers.Main + Job())

fun getQuotes() {
    uiScope.launch {
        val quotes = QuotesApi.retrofitService.getQuotes().await().split('\n').dropLast(1);
        quotes
        for (i in quotes) {
            val quote = Quote()
            quote.main = i
            quote.category = 13
            quote.favorite = 0
            quote.favoriteTime = 0
            quote.sense = "random.org"
            quoteList.add(quote)
        }
    }
}