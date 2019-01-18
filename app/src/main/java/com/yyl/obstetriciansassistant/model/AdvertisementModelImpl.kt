package com.yyl.obstetriciansassistant.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yyl.obstetriciansassistant.beans.Advertisement

class AdvertisementModelImpl : IAdvertisementModel {
    private val ad: Advertisement? = null

    override fun getAdvertisement(type: Int): Advertisement {
        //从服务获得ad
        return ad!!
    }

    override fun visitAdvertisement(c:Context) {
        val intent= Intent()
        intent.data = Uri.parse(ad!!.url)
        intent.action=Intent.ACTION_VIEW
        c.startActivity(intent)
    }
}