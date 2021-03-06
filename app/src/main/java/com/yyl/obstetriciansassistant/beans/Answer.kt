package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Answer : Serializable {
    lateinit var id:String
    @SerializedName("createname")
    lateinit var createName:String
    lateinit var collection:String
    lateinit var content:String
    @SerializedName("createtime")
    lateinit var createTime:String
    lateinit var type:String //用户是否点赞
}