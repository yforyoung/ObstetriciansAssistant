package com.yyl.obstetriciansassistant.model

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Adv
import com.yyl.obstetriciansassistant.beans.Advertisement
import com.yyl.obstetriciansassistant.beans.ResponseData

class AdvertisementModelImpl : AdvertisementModel {
    override fun getAdv(json: String): Adv? {
        val responseData =
                SingleTon.instance.gson.fromJson<ResponseData<List<Adv>>>(json,object :TypeToken<ResponseData<List<Adv>>>(){}.type)
        val list = responseData.data
        return if (list == null) {
            null
        } else {
            list[0]
        }
    }

    override fun getAdv(): Adv? {
        return null
    }

    private lateinit var ad: Advertisement

    override fun getAdvertisement(type: Int): Advertisement {
        //从服务获得ad
        Log.i(TAG, "get ad")
        ad = Advertisement()
        ad.url = "https://m.baidu.com"    //test
        return ad
    }

    override fun visitAdvertisement(c: Context) {
        Log.i(TAG, "show")

        val intent = Intent()
        intent.data = Uri.parse(ad.url)
        intent.action = Intent.ACTION_VIEW
        c.startActivity(intent)
    }
}