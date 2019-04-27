package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.utils.HttpUtils

class TVModelImpl :TVModel {
    override suspend fun getTVList(): ResponseData<List<Video>> {
      val json=  HttpUtils.instance.doPostAsync("$REQUEST_URL/video",null)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Video>>>(json,object :TypeToken<ResponseData<List<Video>>>(){}.type)
    }
}