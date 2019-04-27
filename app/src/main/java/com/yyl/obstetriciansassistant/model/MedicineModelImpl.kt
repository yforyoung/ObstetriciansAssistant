package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.Risk
import com.yyl.obstetriciansassistant.utils.HttpUtils

class MedicineModelImpl : MedicineModel {
    override suspend fun getInitial(s: String): ResponseData<List<Medicine>>{
        val param = HashMap<String, String>()
        param["name"] = s

        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/initials", param)
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<Medicine>>>(json,
            object : TypeToken<ResponseData<List<Medicine>>>() {}.type
        )
        return responseData
    }

    private val hotMedicineList = arrayListOf<Medicine>()

    override fun setHotMedicine(json: String) {
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<Medicine>>>(
            json,
            object : TypeToken<ResponseData<List<Medicine>>>() {}.type
        )
        hotMedicineList.clear()
        hotMedicineList.addAll(responseData.data!!)

    }

    override fun getInitial(): List<String> {
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

    override fun getRiskMedicineListFromInitail(s: String): List<Medicine> {
        val r1 = Medicine(
            "medicine test1",
            "        val s=\"批准日期：2010-08-25%0D%0A剂型：其它剂型%0D%0A规格：每瓶装12毫升%3B30毫升%3B45毫升%3B88毫升%0D%0A储存：密封，置阴凉处%28不超过20°C%29。%0D%0A有效期：48个月。\"\n content",
            "englishName"
        )
        val r2 = Medicine(
            "medicine test2",
            "        val s=\"批准日期：2010-08-25%0D%0A剂型：其它剂型%0D%0A规格：每瓶装12毫升%3B30毫升%3B45毫升%3B88毫升%0D%0A储存：密封，置阴凉处%28不超过20°C%29。%0D%0A有效期：48个月。\"\n content",
            "englishName"
        )
        val r3 = Medicine(
            "medicine test",
            "        val s=\"批准日期：2010-08-25%0D%0A剂型：其它剂型%0D%0A规格：每瓶装12毫升%3B30毫升%3B45毫升%3B88毫升%0D%0A储存：密封，置阴凉处%28不超过20°C%29。%0D%0A有效期：48个月。\"\n content",
            "englishName"
        )
        val r4 = Medicine("medicine test", "medicine content", "englishName")
        val r5 = Medicine(
            "medicine test",
            "        val s=\"批准日期：2010-08-25%0D%0A剂型：其它剂型%0D%0A规格：每瓶装12毫升%3B30毫升%3B45毫升%3B88毫升%0D%0A储存：密封，置阴凉处%28不超过20°C%29。%0D%0A有效期：48个月。\"\n content",
            "englishName"
        )
        val r6 = Medicine("medicine test", "medicine content", "englishName")

        return arrayListOf(r1, r2, r3, r4, r5, r6)
    }
    /* val A=Risk("A")
     val B=Risk("B")
     val C=Risk("C")
     val D=Risk(0,"D")
     val E=Risk(0,"E")
     val F=Risk(0,"F")
     val G=Risk(0,"G")
     val H=Risk(0,"H")
     val I=Risk(0,"I")
     val J=Risk(0,"J")
     val K=Risk(0,"K")
     val L=Risk(0,"L")
     val M=Risk(0,"M")
     val N=Risk(0,"N")
     val O=Risk(0,"O")
     val P=Risk(0,"P")
     val Q=Risk(0,"Q")
     val R=Risk(0,"R")
     val S=Risk(0,"S")
     val T=Risk(0,"T")
     val U=Risk(0,"U")
     val V=Risk(0,"V")
     val W=Risk(0,"W")
     val X=Risk(0,"X")
     val Y=Risk(0,"Y")
     val Z=Risk(0,"Z")

     return arrayListOf(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z)*/

    override fun getRiskMedicine(): List<Risk> {
        val medicine = Medicine(
            "medicine test",
            "        val s=\"批准日期：2010-08-25%0D%0A剂型：其它剂型%0D%0A规格：每瓶装12毫升%3B30毫升%3B45毫升%3B88毫升%0D%0A储存：密封，置阴凉处%28不超过20°C%29。%0D%0A有效期：48个月。\"\n content",
            "englishName"
        )
        val r1 = Risk("A", medicine)
        val r2 = Risk("A", medicine)
        val r3 = Risk("B", medicine)
        val r4 = Risk("C", medicine)
        val r5 = Risk("D", medicine)
        val r6 = Risk("E", medicine)

        return arrayListOf(r1, r2, r3, r4, r5, r6)
    }

    override fun getHotRiskMedicine(): List<Medicine> {
        /*   val medicine=Medicine("test medicinemedicinemedicinemedicine","test medicine content")
           val medicine2=Medicine("test medicine2","test medicine content2medicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicine")

           return arrayListOf(medicine,medicine2)*/
        return hotMedicineList
    }
}