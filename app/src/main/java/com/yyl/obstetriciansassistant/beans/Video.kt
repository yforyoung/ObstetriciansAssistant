package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class Video {
    lateinit var id:String
    lateinit var title:String
    lateinit var url:String
    lateinit var search:String
    @SerializedName("createtime")
    lateinit var createTime:String
}