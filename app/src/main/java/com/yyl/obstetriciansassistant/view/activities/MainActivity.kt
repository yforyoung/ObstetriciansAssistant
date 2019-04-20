package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.MenuItem
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.jump2Activity
import com.yyl.obstetriciansassistant.model.UserModelImpl
import com.yyl.obstetriciansassistant.view.adapter.HomePagerAdapter
import com.yyl.obstetriciansassistant.view.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_content.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private val userModel = UserModelImpl()

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(p0: Int) {
        bottom_navigation_view.menu.getItem(p0).isChecked = true
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        userModel.getUser()
    }

    private fun initView() {
        setSupportActionBar(main_toolbar)
        supportActionBar!!.title = ""
        navigation_view.setNavigationItemSelectedListener(this)


        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(HomeFragment())
        fragmentList.add(MedicineFragment())
        fragmentList.add(TVFragment())
        fragmentList.add(CaseFragment())
        fragmentList.add(QAFragment())

        val adapter = HomePagerAdapter(supportFragmentManager)
        adapter.fragmentList = fragmentList
        home_viewpager.adapter = adapter

        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        home_viewpager.addOnPageChangeListener(this)


        search_bt.setOnClickListener {
            jump2Activity(this, SearchActivity::class.java)
        }

        toolbar_person_bt.setOnClickListener {
            if (!drawer_layout.isDrawerOpen(Gravity.START)) {
                drawer_layout.openDrawer(Gravity.START)
            }
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navigation_settings -> {
                jump2Activity(this, SettingActivity::class.java)
                //  navigation_view.menu.findItem(R.id.navigation_settings).actionView= View.inflate(this,R.layout.menu_item_badge,null)
            }
            R.id.menu_home -> {
                home_viewpager.currentItem = 0
            }
            R.id.menu_risk -> {
                home_viewpager.currentItem = 1
            }
            R.id.menu_tv -> {
                home_viewpager.currentItem = 2
            }
            R.id.menu_case -> {
                home_viewpager.currentItem = 3
            }
            R.id.menu_qa -> {
                home_viewpager.currentItem = 4
            }

        }
        return true
    }


}
