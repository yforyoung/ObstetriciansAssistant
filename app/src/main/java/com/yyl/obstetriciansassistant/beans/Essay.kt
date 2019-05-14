package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Essay :Serializable{
    lateinit var title: String
    lateinit var content: String
    lateinit var id:String

    @SerializedName("createname")
    lateinit var author: String
    lateinit var number:String

    @SerializedName("createtime")
    lateinit var createTime:String

    var source:String=""
    lateinit var type: String
    lateinit var address:String

    constructor(title: String, id: String, type: String) {
        this.title = title
        this.id = id
        this.type = type
    }

    constructor()
}