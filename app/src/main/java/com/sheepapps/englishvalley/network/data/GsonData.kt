package com.sheepapps.englishvalley.network.data

import com.google.gson.annotations.SerializedName

data class GsonData(
        @SerializedName("_id")val id: String?,
        @SerializedName("text")val fact: String?
)