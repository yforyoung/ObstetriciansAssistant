package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Collection
import com.yyl.obstetriciansassistant.beans.CollectionId
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.utils.HttpUtils

class EssayModel  {
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

    suspend fun getCollectionResponse():
            ResponseData<List<Collection>> {
        val param = HashMap<String, String>()
        param["userid"] = SingleTon.instance.user!!.id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/selectuserarticle", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Collection>>>() {}.type)
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