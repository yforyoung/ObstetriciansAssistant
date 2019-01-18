package com.yyl.obstetriciansassistant.model

import android.content.Context
import com.yyl.obstetriciansassistant.beans.Advertisement

interface IAdvertisementModel {
    fun getAdvertisement(type:Int):Advertisement
    fun visitAdvertisement(c:Context)
}