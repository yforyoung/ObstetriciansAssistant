package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Case
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.utils.HttpUtils
import kotlin.collections.HashMap

class CaseModelImpl : CaseModel {


    override suspend fun deleteCase(id: String): Boolean {
        val param = HashMap<String, String>()
        param["id"] = id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/delfeedback", param)
        return SingleTon.instance.gson.fromJson(json, ResponseData::class.java).retcode == 1
    }

    override suspend fun editCase(id: String,case:Case): Boolean {
        val param = HashMap<String, String>()
        param["id"]=id
        param["content"] = case.content
        param["name"] = case.name
        param["age"] = case.age.toString()
        param["treatment"] = case.treatment
        param["tips"] = case.tips
        param["reason"] = case.reason
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/updatepatientrecord", param)
        return SingleTon.instance.gson.fromJson(json, ResponseData::class.java).retcode == 1
    }

    suspend fun getCaseListResponse(): ResponseData<List<Case>> {
        val param = HashMap<String, String>()
        param["id"] = SingleTon.instance.user!!.id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/selectpatientrecord", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Case>>>() {}.type)
    }

    suspend fun saveCase(caseNew: Case): Boolean {
        val param = HashMap<String, String>()
        param["createid"] = SingleTon.instance.user!!.id
        param["content"] = caseNew.content
        param["name"] = caseNew.name
        param["age"] = caseNew.age.toString()
        param["treatment"] = caseNew.treatment
        param["tips"] = caseNew.tips
        param["reason"] = caseNew.reason
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/addpatientrecord", param)
        return SingleTon.instance.gson.fromJson(json, ResponseData::class.java).retcode == 1
    }

    /*suspend fun  insertCase(case:Case):Boolean{

    }*/
}