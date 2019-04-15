package com.yyl.obstetriciansassistant

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.kk.taurus.playerbase.config.PlayerConfig
import com.kk.taurus.playerbase.config.PlayerLibrary

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this

        PlayerConfig.setUseDefaultNetworkEventProducer(true)
        //初始化库
        PlayerLibrary.init(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TAG: String = "yforyoung"


    }
}