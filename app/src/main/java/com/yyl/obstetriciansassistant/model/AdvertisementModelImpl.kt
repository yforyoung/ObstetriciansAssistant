package com.yyl.obstetriciansassistant.model

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.App
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Adv
import com.yyl.obstetriciansassistant.beans.Advertisement
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.utils.HttpUtils

class AdvertisementModelImpl : AdvertisementModel {
    override fun getAdv(json: String): Adv? {
        val responseData =
            SingleTon.instance.gson.fromJson<ResponseData<List<Adv>>>(json,
                object : TypeToken<ResponseData<List<Adv>>>() {}.type)
        val list = responseData.data
        return if (list == null) {
            null
        } else {
            list[0]
        }
    }

    suspend fun getAdvResponse() :ResponseData<List<Adv>>{
        val param = HashMap<String, String>()
        param["type"] = "启动页"
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getadvertisement", param)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Adv>>>(json,
                object : TypeToken<ResponseData<List<Adv>>>() {}.type)
    }

    override fun getAdvImages(json: String, ctx: Context): List<ImageView>? {
        val responseData =
            SingleTon.instance.gson.fromJson<ResponseData<List<Adv>>>(json,
                object : TypeToken<ResponseData<List<Adv>>>() {}.type)
        val list = responseData.data
        val imgList = arrayListOf<ImageView>()
        for (adv in list!!) {
            val img = ImageView(ctx)
            img.scaleType=ImageView.ScaleType.FIT_XY
            Glide.with(ctx).load(adv.adv).into(img)
            imgList.add(img)
        }
        return imgList
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