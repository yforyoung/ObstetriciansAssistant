package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class Collection {
    @SerializedName("articleid")
    lateinit var articleId:String
    lateinit var id: String
    lateinit var title:String
    lateinit var type:String
}