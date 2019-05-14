package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class Comment {
    lateinit var id:String
    lateinit var name: String
    @SerializedName("createtime")
    lateinit var createTime: String
    lateinit var content:String
}