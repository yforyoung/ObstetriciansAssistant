package com.yyl.obstetriciansassistant.view.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.jump2Activity
import com.yyl.obstetriciansassistant.view.adapter.AdPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_content.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val list = ArrayList<ImageView>()
    private val delayMills:Long=3000
    private var handler = Handler {
        if (it.what == 0) {
            val curr=(ad_viewpager.currentItem+1)%list.size
            ad_viewpager.currentItem=curr
            Log.e("TAG","${ad_viewpager.currentItem}  +   ${list.size}")
            it.target.sendEmptyMessageDelayed(0, delayMills)
        }
        false
    }


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(main_toolbar)
        supportActionBar!!.title = ""
        navigation_view.setNavigationItemSelectedListener(this)

        search_bt.setOnClickListener {
            jump2Activity(this, SearchActivity::class.java)
        }

        toolbar_person_bt.setOnClickListener {
            if (!drawer_layout.isDrawerOpen(Gravity.START)) {
                drawer_layout.openDrawer(Gravity.START)
            }
        }

        initAdViewPager()

    }

    private fun initAdViewPager() {
        //----------测试用list-----------------------
        val img1 = ImageView(this)
        img1.setBackgroundColor(Color.BLACK)
        val img2 = ImageView(this)
        img2.setBackgroundColor(Color.RED)
        val img3 = ImageView(this)
        img2.setBackgroundColor(Color.BLUE)
        list.add(img1)
        list.add(img2)
        list.add(img3)
        //----------------------------------------------

        for (i in 0..(list.size - 1)) {
            val img = ImageView(this)
            img.setBackgroundResource(R.drawable.shape_circle_viewpager_bg)
            val param = LinearLayout.LayoutParams(resources.getDimensionPixelOffset(R.dimen.ad_circle_width), resources.getDimensionPixelOffset(R.dimen.ad_circle_height))
            if (i > 0) {
                param.leftMargin = resources.getDimensionPixelOffset(R.dimen.ad_circle_margin)
            } else {
                img.isSelected = true
            }
            img.layoutParams = param
            point_group.addView(img)
        }

        ad_viewpager.adapter = AdPagerAdapter(list, ad_viewpager)
        ad_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            var p1 = 0
            override fun onPageSelected(p0: Int) {
                point_group.getChildAt(p0).isSelected = true
                point_group.getChildAt(p1).isSelected = false
                p1 = p0
            }

        })
        ad_viewpager.currentItem = 0
        handler.sendEmptyMessageDelayed(0, delayMills)

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
        }
        return true
    }


}
