package com.sheepapps.englishvalley.netRestQuery.entity

import com.google.gson.annotations.SerializedName

class ListInfo(@SerializedName("all") val list: ArrayList<Info>) {
}