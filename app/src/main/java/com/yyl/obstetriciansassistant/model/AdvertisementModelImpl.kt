package com.yyl.obstetriciansassistant.model

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.yyl.obstetriciansassistant.beans.Advertisement

class AdvertisementModelImpl : AdvertisementModel {
    private lateinit var ad: Advertisement

    override fun getAdvertisement(type: Int): Advertisement {
        //从服务获得ad
        Log.i(TAG,"get ad")
        ad= Advertisement()
        ad.url="https://m.baidu.com"    //test
        return ad
    }

    override fun visitAdvertisement(c:Context) {
        Log.i(TAG,"show")

        val intent= Intent()
        intent.data = Uri.parse(ad.url)
        intent.action=Intent.ACTION_VIEW
        c.startActivity(intent)
    }
}