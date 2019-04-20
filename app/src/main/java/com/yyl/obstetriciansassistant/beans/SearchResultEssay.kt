package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class SearchResultEssay {
    lateinit var title: String
    lateinit var id:String

    @SerializedName("createname")
    lateinit var author: String
    lateinit var number:String

    @SerializedName("createtime")
    lateinit var createTime:String

    lateinit var source:String
    lateinit var type: String

}