package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.model.AdvertisementModelImpl
import com.yyl.obstetriciansassistant.utils.HttpUtils
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    private val adModel = AdvertisementModelImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initAdvertisement()
        initView()


    }

    private fun initAdvertisement() {
        //获取广告页并显示

        val map=HashMap<String,String>()
        map["type"] = "启动页"
        HttpUtils.instance.doPost(
            "$REQUEST_URL/getadvertisement",
            map,object : HttpUtils.HttpCallBack{
                override fun success(json:String) {
                   GlobalScope.launch (UI){
                     showAd(json)
                   }
                }
            })

    }

    private fun showAd(json:String) {
        val ad= adModel.getAdv(json)

        val url = ad!!.adv
        Glide.with(this@SplashActivity).load(url).error(R.mipmap.ic_launcher).into(splash_ad_img)

        //3秒跳转至主页面
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                jump2Activity(this@SplashActivity, LoadActivity::class.java)
                finish()
            }
        }, 3000)
    }

    private fun initView() {
        jump_ad_text_bt.setOnClickListener {
            jump2Activity(this, LoadActivity::class.java)
            timer.cancel()
            finish()
        }

    }
}
