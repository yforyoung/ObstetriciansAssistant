package com.yyl.obstetriciansassistant

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.kk.taurus.playerbase.config.PlayerConfig
import com.kk.taurus.playerbase.config.PlayerLibrary
import com.kk.taurus.playerbase.entity.DecoderPlan
import com.yyl.obstetriciansassistant.myview.play.ExoMediaPlayer

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        context = this

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

        PlayerConfig.addDecoderPlan(DecoderPlan(2, ExoMediaPlayer::class.java.name, "ExoPlayer"))
        PlayerConfig.setDefaultPlanId(2)
        PlayerConfig.setUseDefaultNetworkEventProducer(true)
        //初始化库
        PlayerLibrary.init(this)
    }



    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        val list = arrayListOf<Activity>()
        const val TAG: String = "yforyoung"
        fun addActivity(activity: Activity) {
            if (!list.contains(activity))
                list.add(activity)
        }

        fun finishActivity(){
            for (a in list){
                a.finish()
            }
        }
    }
}