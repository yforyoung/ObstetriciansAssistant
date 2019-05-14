package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Video :Serializable{
    lateinit var id:String
    lateinit var title:String
    lateinit var url:String
    lateinit var search:String
    @SerializedName("createtime")
    lateinit var createTime:String
    @SerializedName("imgurl")
    lateinit var imgUrl:String
}