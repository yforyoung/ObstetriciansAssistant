package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class User {
    lateinit var id:String
    lateinit var name:String
    @SerializedName("hostpital")
    lateinit var hospital:String
    lateinit var position:String
    lateinit var password:String
    lateinit var department:String
    lateinit var administrators:String
    lateinit var createTime:String
    lateinit var attachment:String

}