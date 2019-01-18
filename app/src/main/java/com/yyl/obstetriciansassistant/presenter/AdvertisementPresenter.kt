package com.yyl.obstetriciansassistant.presenter

import com.yyl.obstetriciansassistant.beans.Advertisement
import com.yyl.obstetriciansassistant.model.AdvertisementModelImpl
import com.yyl.obstetriciansassistant.model.IAdvertisementModel
import com.yyl.obstetriciansassistant.view.IAdvertisementView

class AdvertisementPresenter(private var adView: IAdvertisementView) {
    private var adModel: IAdvertisementModel = AdvertisementModelImpl()

    fun getAdvertisement(): Advertisement = adModel.getAdvertisement(adView.getType())

    fun visitAdvertisment(){
        adModel.visitAdvertisement(adView.getViewContext())
    }


}