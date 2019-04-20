package com.yyl.obstetriciansassistant.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Medicine(medicienName: String, medicineIntroduce: String) : Serializable {
    @SerializedName("riskcode")
    lateinit var riskCode: String
    var name: String = medicienName
    lateinit var type: String
    lateinit var number: String
    @SerializedName("englishname")
    lateinit var englishName: String
    var introduce: String = medicineIntroduce

}