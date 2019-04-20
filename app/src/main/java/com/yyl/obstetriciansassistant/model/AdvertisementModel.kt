package com.yyl.obstetriciansassistant.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.yyl.obstetriciansassistant.beans.Adv
import com.yyl.obstetriciansassistant.beans.Advertisement

interface AdvertisementModel {
    fun getAdvertisement(type: Int): Advertisement
    fun visitAdvertisement(c: Context)
    fun getAdvImages(json:String,ctx:Context): List<ImageView>?
    fun getAdv(json: String): Adv?
}