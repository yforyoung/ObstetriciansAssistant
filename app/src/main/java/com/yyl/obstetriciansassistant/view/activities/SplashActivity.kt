package com.yyl.obstetriciansassistant.view.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Advertisement
import com.yyl.obstetriciansassistant.presenter.AdvertisementPresenter
import com.yyl.obstetriciansassistant.view.IAdvertisementView
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity(), IAdvertisementView {
    private val adPresenter=AdvertisementPresenter(this)
    override fun getType(): Int =Advertisement.type.SPLASH_AD

    override fun getViewContext(): Context = applicationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initAdvertisement()
        initView()

        //3秒跳转至主页面
        Timer().schedule(object : TimerTask() {
            override fun run() {
                jump2Activity(this@SplashActivity, MainActivity::class.java)
            }
        }, 3000)
    }

    private fun initAdvertisement() {
        //获取广告页并显示
        var ad=adPresenter.getAdvertisement()

    }

    private fun initView() {
        jump_ad_text_bt.setOnClickListener {
            jump2Activity(this, MainActivity::class.java)
        }

        splash_ad_img.setOnClickListener {
            //访问网页
            adPresenter.visitAdvertisment()
        }
    }

    fun Context.jump2Activity(c: Context, cls: Class<*>) = startActivity(Intent(c, cls))


}
