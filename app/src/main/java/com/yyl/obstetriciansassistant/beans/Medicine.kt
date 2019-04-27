package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Medicine(medicienName: String, medicineIntroduce: String,englishName:String) : Serializable {
    @SerializedName("riskcode")
    lateinit var riskCode: String
    var name: String = medicienName
    lateinit var type: String
    lateinit var number: String
    @SerializedName("englishname")
    var englishName: String=englishName
    var introduce: String = medicineIntroduce

}