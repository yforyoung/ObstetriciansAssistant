package com.yyl.obstetriciansassistant.presenter

import com.yyl.obstetriciansassistant.beans.Advertisement
import com.yyl.obstetriciansassistant.model.AdvertisementModelImpl
import com.yyl.obstetriciansassistant.model.AdvertisementModel
import com.yyl.obstetriciansassistant.view.IAdvertisementView

class AdvertisementPresenter(private var adView: IAdvertisementView) {
    private var adModel: AdvertisementModel = AdvertisementModelImpl()

    fun getAdvertisement(): Advertisement = adModel.getAdvertisement(adView.getType())

    fun visitAdvertisement(){
        adModel.visitAdvertisement(adView.getViewContext())
    }


}