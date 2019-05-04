package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import kotlinx.android.synthetic.main.fragment_data.*

class DataFragment : Fragment() {
    var fragmentList = arrayListOf(MedicineFragment(),EssayFragment())
    var titles = arrayListOf("药物", "文章")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView() {
        data_viewpager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(p0: Int): Fragment = fragmentList[p0]

            override fun getCount(): Int = fragmentList.size

            override fun getPageTitle(position: Int): CharSequence? = titles[position]

            override fun restoreState(state: Parcelable?, loader: ClassLoader?) {

            }

        }

        data_tab.setupWithViewPager(data_viewpager)
    }

}