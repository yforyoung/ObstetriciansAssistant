package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class HotSearch {
    lateinit var title: String
    lateinit var content: String
    lateinit var id:String

    @SerializedName("createname")
    lateinit var author: String
    lateinit var number:String

    @SerializedName("createtime")
    lateinit var createTime:String

    lateinit var source:String
    lateinit var type: String
    lateinit var address:String

    @SerializedName("riskcode")
    lateinit var riskCode:String
    lateinit var name:String
}