package com.sheepapps.englishvalley.netRestQuery.entity

import com.google.gson.annotations.SerializedName

class Info(
        @SerializedName("_id") val id: String,
        @SerializedName("text") val text: String,
        @SerializedName("type") val type: String
)

