package com.yyl.obstetriciansassistant.model

import android.content.Context
import com.yyl.obstetriciansassistant.beans.Adv
import com.yyl.obstetriciansassistant.beans.Advertisement

interface AdvertisementModel {
    fun getAdvertisement(type: Int): Advertisement
    fun visitAdvertisement(c: Context)
    fun getAdv(): Adv?
    fun getAdv(json: String): Adv?
}