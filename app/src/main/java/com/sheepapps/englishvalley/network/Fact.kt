package com.sheepapps.englishvalley.network

import com.sheepapps.englishvalley.databases.WordAbs
import com.sheepapps.englishvalley.helpers.Constants

data class Fact(
        val text: String
) : WordAbs() {

    init {
        main = text
        sense = ""
        category = Constants.Categories.CATEGORY_FACT
        example = ""
        extra = ""
    }

}