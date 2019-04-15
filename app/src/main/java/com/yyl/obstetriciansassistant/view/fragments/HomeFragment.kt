package com.yyl.obstetriciansassistant.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.yyl.obstetriciansassistant.App.Companion.TAG
import com.yyl.obstetriciansassistant.ESSAY
import com.yyl.obstetriciansassistant.MEDICINE
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.jump2Activity
import com.yyl.obstetriciansassistant.model.EssayModel
import com.yyl.obstetriciansassistant.model.EssayModelImpl
import com.yyl.obstetriciansassistant.model.MedicineModel
import com.yyl.obstetriciansassistant.model.MedicineModelImpl
import com.yyl.obstetriciansassistant.view.activities.DetailActivity
import com.yyl.obstetriciansassistant.view.adapter.AdPagerAdapter
import com.yyl.obstetriciansassistant.view.adapter.HomeEssayAdapter
import com.yyl.obstetriciansassistant.view.adapter.HomeMedicineAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class HomeFragment : Fragment() {
    private val list = ArrayList<ImageView>()
    private val delayMills: Long = 5000

    private var essayList= arrayListOf<Essay>()
    private var medicineList= arrayListOf<Medicine>()

    private lateinit var homeEssayAdapter:HomeEssayAdapter
    private lateinit var medicineAdapter: HomeMedicineAdapter

    private val essayModel:EssayModel=EssayModelImpl()
    private val medicineModel:MedicineModel=MedicineModelImpl()

    private var handler = Handler {
        if (it.what == 0) {
            val curr = (ad_viewpager.currentItem + 1) % list.size
            ad_viewpager.currentItem = curr
            Log.e("TAG", "${ad_viewpager.currentItem}  +   ${list.size}")
            it.target.sendEmptyMessageDelayed(0, delayMills)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*******假数据*********/
        val img1 = ImageView(activity)
        img1.setBackgroundColor(Color.BLACK)
        val img2 = ImageView(activity)
        img2.setBackgroundColor(Color.RED)
        val img3 = ImageView(activity)
        img2.setBackgroundColor(Color.BLUE)
        list.add(img1)
        list.add(img2)
        list.add(img3)
        /********************/

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdViewPager()
        initHomeEssay()
        initHomeRisk()

    }

    private fun initHomeRisk() {
        home_risk_list_view.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        medicineAdapter= HomeMedicineAdapter(medicineModel.getHotRiskMedicine())
        medicineAdapter.setOnClickListener(object :HomeMedicineAdapter.OnClickListener{
            override fun onItemClick(v: View, position: Int) {
                Log.e(TAG, "risk item click $position")
                activity!!.jump2Activity(activity!!,DetailActivity::class.java, MEDICINE,medicineList[position])
            }
        })
        home_risk_list_view.adapter=medicineAdapter
        home_risk_list_view.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        refreshHotMedicine()
    }

    private fun refreshHotMedicine(){
        medicineList.clear()
        medicineList.addAll(medicineModel.getHotRiskMedicine())
        medicineAdapter.notifyDataSetChanged()
    }

    private fun initHomeEssay() {
        home_essay_list_view.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        homeEssayAdapter= HomeEssayAdapter(essayModel.getHotEssay())
        homeEssayAdapter.setOnClickListener(object :HomeEssayAdapter.OnClickListener{
            override fun onItemClick(v: View, position: Int) {
                Log.e(TAG,"essay item click $position")
                activity!!.jump2Activity(activity!!, DetailActivity::class.java, ESSAY,essayList[position])
            }
        })
        home_essay_list_view.adapter=homeEssayAdapter
        home_essay_list_view.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        refreshHotEssay()

    }

    private fun refreshHotEssay(){
        essayList.clear()
        essayList.addAll(essayModel.getHotEssay())
        homeEssayAdapter.notifyDataSetChanged()
    }

    private fun initAdViewPager() {
        //----------测试用list-----------------------

        //----------------------------------------------

        for (i in 0..(list.size - 1)) {
            val img = ImageView(activity)
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

    override fun onStop() {
        handler.removeMessages(0)
        super.onStop()
    }
}