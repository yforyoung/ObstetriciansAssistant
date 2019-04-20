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

    lateinit var source:String
    lateinit var type: String

    constructor()
    constructor(title: String, author: String, content: String) {
        this.title = title
        this.content = content
        this.author = author
    }

}