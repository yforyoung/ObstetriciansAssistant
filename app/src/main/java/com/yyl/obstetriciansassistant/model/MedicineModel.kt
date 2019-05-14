package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.utils.HttpUtils

class MedicineModel  {
     suspend fun getMedicineFromInitial(s: String): ResponseData<List<Medicine>>{
        val param = HashMap<String, String>()
        param["name"] = s

        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/initials", param)
         return SingleTon.instance.gson.fromJson(json,
             object : TypeToken<ResponseData<List<Medicine>>>() {}.type
         )
    }

    private val hotMedicineList = arrayListOf<Medicine>()

     fun setHotMedicine(json: String) {
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<Medicine>>>(
            json,
            object : TypeToken<ResponseData<List<Medicine>>>() {}.type
        )
        hotMedicineList.clear()
        hotMedicineList.addAll(responseData.data!!)

    }

     fun getInitial(): List<String> {
        return arrayListOf(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )
    }
     fun getHotRiskMedicine(): List<Medicine> {
        return hotMedicineList
    }
}