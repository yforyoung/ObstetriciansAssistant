package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.jump2Activity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_content.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar!!.title = ""
        navigation_view.setNavigationItemSelectedListener(this)

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
            R.id.navigation_settings ->{
                jump2Activity(this,SettingActivity::class.java)
              //  navigation_view.menu.findItem(R.id.navigation_settings).actionView= View.inflate(this,R.layout.menu_item_badge,null)
            }
        }
        return true
    }



}
