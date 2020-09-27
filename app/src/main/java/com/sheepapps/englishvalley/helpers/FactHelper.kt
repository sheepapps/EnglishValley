package com.sheepapps.englishvalley.helpers

import com.sheepapps.englishvalley.app.ValleyApp
import com.sheepapps.englishvalley.databases.system.Storage
import com.sheepapps.englishvalley.network.Fact

object FactHelper {

    private val FACT_STORAGE: Storage = Storage()
    val facts = mutableListOf<Fact>()

    init {
        FACT_STORAGE.id = Constants.Categories.CATEGORY_FACT
        FACT_STORAGE.name = "Facts"
        facts.addAll(loadFacts())
        FACT_STORAGE.total = facts.size
    }

    private fun loadFacts() =
            ValleyApp.getInstance().repository.getFacts()

    fun getFactStorage() = FACT_STORAGE
}