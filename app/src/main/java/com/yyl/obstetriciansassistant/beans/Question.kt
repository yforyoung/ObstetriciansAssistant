package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Question : Serializable {
    lateinit var id:String
    @SerializedName("createname")
    lateinit var createName:String
    lateinit var type:String
    lateinit var content:String
    @SerializedName("createtime")
    lateinit var createDate: String
    lateinit var title:String


}