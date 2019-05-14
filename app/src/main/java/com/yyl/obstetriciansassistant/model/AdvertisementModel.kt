package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Adv
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.log
import com.yyl.obstetriciansassistant.utils.HttpUtils

class AdvertisementModel {

    suspend fun getStartAdvResponse(): ResponseData<List<Adv>> {
        val param = HashMap<String, String>()
        param["type"] = "启动页"
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getadvertisement", param)
        log(json)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Adv>>>(
            json,
            object : TypeToken<ResponseData<List<Adv>>>() {}.type
        )
    }

    suspend fun getHomeAdvResponse(): ResponseData<List<Adv>> {
        val param = HashMap<String, String>()
        param["type"] = "首页"
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getadvertisement", param)
        log(json)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Adv>>>(
            json,
            object : TypeToken<ResponseData<List<Adv>>>() {}.type
        )
    }

}