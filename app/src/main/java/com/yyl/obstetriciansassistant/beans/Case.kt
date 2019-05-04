package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Case : Serializable {
    lateinit var id:String
    @SerializedName("createtime")
    lateinit var createDate: String
    lateinit var content:String
    lateinit var name:String
    lateinit var reason:String
    lateinit var treatment:String
    var age=0
    lateinit var tips:String

}