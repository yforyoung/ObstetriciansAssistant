package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Comment
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.utils.HttpUtils

class TVModel {
     suspend fun getTVList(): ResponseData<List<Video>> {
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/video", null)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Video>>>(json,
            object : TypeToken<ResponseData<List<Video>>>() {}.type)
    }

    suspend fun getTVComment(id: String): ResponseData<List<Comment>> {
        val param = HashMap<String, String>()
        param["videoid"] = id

        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/uservideo", param)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Comment>>>(json,
            object : TypeToken<ResponseData<List<Comment>>>() {}.type)
    }

    suspend fun addAnswer(s: String,id:String): Boolean {
        val param = HashMap<String, String>()
        param["videoid"] = id
        param["userid"] = SingleTon.instance.user!!.id
        param["content"] = s
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/adduservideo", param)
        return SingleTon.instance.gson.fromJson(json,ResponseData::class.java).retcode==1
    }
}