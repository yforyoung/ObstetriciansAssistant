package com.yyl.obstetriciansassistant.model

import android.content.Context
import com.yyl.obstetriciansassistant.beans.Advertisement

interface AdvertisementModel {
    fun getAdvertisement(type:Int):Advertisement
    fun visitAdvertisement(c:Context)
}