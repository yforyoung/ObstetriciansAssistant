package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.CollectionId
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.SearchResultEssay
import com.yyl.obstetriciansassistant.log
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

            log(j)
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

    suspend fun isCollected(id: String): Boolean {
        val param = HashMap<String, String>()
        param["userid"] = SingleTon.instance.user!!.id
        param["articleid"] = id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/queryuserarticle", param)
        val re = SingleTon.instance.gson.fromJson<ResponseData<List<CollectionId>>>(
            json,
            object : TypeToken<ResponseData<List<CollectionId>>>() {}.type
        )
        if (re.retcode == 1) SingleTon.instance.collectionId = re.data!![0].id
        return re.retcode == 1
    }

    suspend fun getCollectionResponse(): ResponseData<List<Essay>> {
        val param = HashMap<String, String>()
        param["userid"] = SingleTon.instance.user!!.id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/selectuserarticle", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Essay>>>() {}.type)
    }

    suspend fun getEssayResponse(): ResponseData<List<Essay>> {
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/article", null)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Essay>>>() {}.type)
    }

    suspend fun getHotEssayResponse(): ResponseData<List<Essay>> {
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/popularartcilesearch", null)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Essay>>>() {}.type)
    }
}