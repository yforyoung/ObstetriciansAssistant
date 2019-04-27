package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Case
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.utils.HttpUtils
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap

class CaseModelImpl : CaseModel {
    override suspend fun getCases(): List<Case> {
      /*  val params = HashMap<String, String>()
        params["id"] = SingleTon.instance.user!!.id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/selectpatientrecord", params)
        val responseData=SingleTon.instance.gson.fromJson<ResponseData<List<Case>>>(json,
            object :TypeToken<ResponseData<List<Case>>>(){}.type)
        return responseData.data!!
*/
        val date= Date(119, 4, 1)
        val s= DateFormat.getDateInstance().format(date)
        val c1 = Case(1, s, s, "超长的中文名有十个字", "resonresonresonresonresonresonresonresonresonresonreson", "treatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatment")
        val c2 = Case(1, s, s, "test naem", "reson", "treatment")
        val c3 = Case(1, s, s, "test naem", "resonresonresonresonresonresonresonresonresonresonreson", "treatment")
        val c4 = Case(1, s, s, "test naem", "reson", "treatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatment")
        val c5 = Case(1, s, s, "test naem", "resonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonreson", "treatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatment")
        val c6 = Case(1, s, s, "test naem", "resonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonreson", "treatment")

        return arrayListOf(c1, c2, c3, c4, c5, c6)
    }

    override fun deleteCase(id: Int): Boolean {
        return id % 2 == 0
    }

    override fun editCase(id: Int): Boolean {
        return true
    }
}