package com.yyl.obstetriciansassistant.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class HomePagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    var fragmentList=ArrayList<Fragment>()
    override fun getItem(p0: Int): Fragment =fragmentList[p0]

    override fun getCount(): Int =fragmentList.size
}