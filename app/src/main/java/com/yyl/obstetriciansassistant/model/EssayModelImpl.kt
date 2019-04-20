package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.SearchResultEssay
import com.yyl.obstetriciansassistant.utils.HttpUtils

class EssayModelImpl : EssayModel {
    override fun setHotEssay(json: String) {
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<SearchResultEssay>>>(
            json,
            object : TypeToken<ResponseData<List<SearchResultEssay>>>() {}.type
        )
        val list = responseData.data!!

        for (se in list) {
            val param = HashMap<String, String>()
            param["name"] = se.title
            param["id"] = se.id
            HttpUtils.instance.doPost("$REQUEST_URL/queryarticle", param, object : HttpUtils.HttpCallBack {
                override fun success(json: String) {

                }
            })
        }
    }


    override suspend fun getHotEssay(json: String): List<Essay> {
        val hotEssayList = arrayListOf<Essay>()

        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<SearchResultEssay>>>(
            json,
            object : TypeToken<ResponseData<List<SearchResultEssay>>>() {}.type
        )
        val list = responseData.data!!

        for (se in list) {
            val param = HashMap<String, String>()
            param["name"] = se.title
            param["id"] = se.id
            val j = HttpUtils.instance.doPostAsync("$REQUEST_URL/queryarticle", param)

            val red = SingleTon.instance.gson.fromJson<ResponseData<List<Essay>>>(
                j,
                object : TypeToken<ResponseData<List<Essay>>>() {}.type
            )
            hotEssayList.addAll(red.data!!)
        }

        return hotEssayList

    }

    override fun getEssay(): List<Essay> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}