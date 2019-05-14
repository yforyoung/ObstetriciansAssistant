package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName

class RegisterBefore{
    @SerializedName("hostpitaldata")
    lateinit var hospitalList:List<Hospital>
    @SerializedName("positiondata")
    lateinit var positionList:List<Position>
    @SerializedName("departmentdata")
    lateinit var departmentList:List<Clazz>
}