package com.yyl.obstetriciansassistant.view.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import cn.jpush.android.api.JPushInterface
import com.yyl.obstetriciansassistant.App
import com.yyl.obstetriciansassistant.model.UserModel
import com.yyl.obstetriciansassistant.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.utils.SpfUtils
import com.yyl.obstetriciansassistant.utils.UpdateAppUtil
import com.yyl.obstetriciansassistant.SingleTon
import cn.jpush.android.api.BasicPushNotificationBuilder
import android.app.Notification


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var fragmentManager: FragmentManager
    lateinit var transaction: FragmentTransaction
    private lateinit var tempFragment: Fragment
    private var homeFragment: HomeFragment? = null
    private var dataFragment: DataFragment? = null
    private var tvFragment: TVFragment? = null
    private var qaFragment: QAFragment? = null
    private var userFragment: UserFragment? = null

    private val userModel = UserModel()
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userModel.initUser()
        initView()

        JPushInterface.setTags(App.context,1, setOf(SingleTon.instance.user!!.id))
      //  myReceiver()
        JPushInterface.requestPermission(this)
        if (SpfUtils.instance.getSpfBoolean("prf_check_auto_update", true)) {
            UpdateAppUtil.getInstance().updateApp(0,this)
        }
    }

    override fun onResume() {
        super.onResume()


       /* if (!SpfUtils.instance.getSpfBoolean("prf_notify",true)){
            JPushInterface.setPushTime(applicationContext, setOf<Int>(), 0, 0)
        }else{
            JPushInterface.setPushTime(applicationContext, setOf(0,1,2,3,4,5,6), 0, 23)
        }*/

        if (!SpfUtils.instance.getSpfBoolean("prf_notify_ring",true)){
            val builder = BasicPushNotificationBuilder(this@MainActivity)
            builder.notificationFlags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_SHOW_LIGHTS  //设置为自动消失和呼吸灯闪烁
            builder.notificationDefaults = (Notification.DEFAULT_SOUND
                    or Notification.DEFAULT_VIBRATE
                    or Notification.DEFAULT_LIGHTS)  // 设置为铃声、震动、呼吸灯闪烁都要
            JPushInterface.setPushNotificationBuilder(1, builder)
          //  JPushInterface.setSilenceTime(applicationContext, 0, 0, 23, 59)
        }else{
            val builder = BasicPushNotificationBuilder(this@MainActivity)
            builder.notificationFlags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_SHOW_LIGHTS  //设置为自动消失和呼吸灯闪烁
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS  // 设置为铃声、震动、呼吸灯闪烁都要
            JPushInterface.setPushNotificationBuilder(1, builder)
          //  JPushInterface.setSilenceTime(applicationContext, 0, 0, 0, 0)
        }
    }


    private fun initView() {
        fragmentManager = supportFragmentManager
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        homeFragment = HomeFragment()
        tempFragment = homeFragment!!
        showFragment()

    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.menu_home -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                }
                tempFragment = homeFragment!!
            }
            R.id.menu_medicine -> {
                if (dataFragment == null) {
                    dataFragment = DataFragment()
                }
                tempFragment = dataFragment!!
            }
            R.id.menu_tv -> {
                if (tvFragment == null) {
                    tvFragment = TVFragment()
                }
                tempFragment = tvFragment!!
            }
            R.id.menu_essay -> {
                if (qaFragment == null) {
                    qaFragment = QAFragment()
                }
                tempFragment = qaFragment!!
            }
            R.id.menu_mine -> {
                if (userFragment == null) {
                    userFragment = UserFragment()
                }
                tempFragment = userFragment!!
            }
        }
        showFragment()
        return true
    }


    @SuppressLint("CommitTransaction")
    private fun showFragment() {
        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, tempFragment)
        transaction.commit()
    }

}
