package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class CaseT {
    lateinit var id:String
    lateinit var content:String
    @SerializedName("patientname")
    lateinit var name:String
    @SerializedName("updatetime")
    lateinit var updateTime:String

    @SerializedName("createtime")
    lateinit var createTime:String
}