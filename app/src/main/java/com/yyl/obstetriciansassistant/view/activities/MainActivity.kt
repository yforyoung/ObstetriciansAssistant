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
import com.yyl.obstetriciansassistant.App
import com.yyl.obstetriciansassistant.model.UserModelImpl
import com.yyl.obstetriciansassistant.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import com.yyl.obstetriciansassistant.R


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var fragmentManager: FragmentManager
    lateinit var transaction: FragmentTransaction
    private lateinit var tempFragment:Fragment
    private var homeFragment: HomeFragment? = null
    private var dataFragment: DataFragment? = null
    private var tvFragment: TVFragment? = null
    private var qaFragment: QAFragment? = null
    private var userFragment: UserFragment? = null

    private val userModel = UserModelImpl()
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userModel.initUser()
        initView()
        reqestionPermisson()
        App.addActivity(this)

    }

    private fun reqestionPermisson() {
        val pList= arrayListOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_PHONE_STATE)

    }

    private fun initView() {
        fragmentManager = supportFragmentManager
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        homeFragment= HomeFragment()
        tempFragment=homeFragment!!
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
