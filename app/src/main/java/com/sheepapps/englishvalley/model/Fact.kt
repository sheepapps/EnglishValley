package com.sheepapps.englishvalley.model

import com.google.gson.annotations.SerializedName

data class Facts (

        @SerializedName("all") val all : List<All>
)

data class All (
        @SerializedName("_id") val _id : String,
        @SerializedName("text") val text : String,
        @SerializedName("type") val type : String,
        @SerializedName("user") val user : User,
        @SerializedName("upvotes") val upvotes : Int,
        @SerializedName("userUpvoted") val userUpvoted : String
)

data class User (
        @SerializedName("_id") val _id : String,
        @SerializedName("name") val name : Name
)

data class Name (

        @SerializedName("first") val first : String,
        @SerializedName("last") val last : String
)