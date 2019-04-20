package com.yyl.obstetriciansassistant.view.fragments

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
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.App.Companion.TAG
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.model.*
import com.yyl.obstetriciansassistant.utils.HttpUtils
import com.yyl.obstetriciansassistant.view.activities.DetailActivity
import com.yyl.obstetriciansassistant.view.adapter.AdPagerAdapter
import com.yyl.obstetriciansassistant.view.adapter.HomeEssayAdapter
import com.yyl.obstetriciansassistant.view.adapter.HomeMedicineAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.HashMap

class HomeFragment : Fragment() {
    private val list = arrayListOf<ImageView>()
    private val delayMills: Long = 5000

    private var essayList = arrayListOf<Essay>()
    private var medicineList = arrayListOf<Medicine>()

    private lateinit var homeEssayAdapter: HomeEssayAdapter
    private lateinit var medicineAdapter: HomeMedicineAdapter
    private lateinit var adAdapter: AdPagerAdapter

    private val essayModel: EssayModel = EssayModelImpl()
    private val medicineModel: MedicineModel = MedicineModelImpl()

    private val adMode = AdvertisementModelImpl()

    private var handler = Handler {
        if (it.what == 0) {
            val curr = (ad_viewpager.currentItem + 1) % list.size
            ad_viewpager.currentItem = curr
            Log.e("TAG", "${ad_viewpager.currentItem}  +   ${list.size}")
            it.target.sendEmptyMessageDelayed(0, delayMills)
        }
        false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdViewPager()
        initHomeEssay()
        initHomeRisk()

    }

    private fun initHomeRisk() {
        home_risk_list_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        medicineAdapter = HomeMedicineAdapter(medicineModel.getHotRiskMedicine())
        medicineAdapter.setOnClickListener(object : HomeMedicineAdapter.OnClickListener {
            override fun onItemClick(v: View, position: Int) {
                Log.e(TAG, "risk item click $position")
                activity!!.jump2Activity(activity!!, DetailActivity::class.java, MEDICINE, medicineList[position])
            }
        })
        home_risk_list_view.adapter = medicineAdapter
        home_risk_list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        refreshHotMedicine()
    }

    private fun refreshHotMedicine() {
        HttpUtils.instance.doPost("$REQUEST_URL/popularsearch", null, object : HttpUtils.HttpCallBack {
            override fun success(json: String) {
                GlobalScope.launch(UI) {
                    medicineList.clear()
                    medicineModel.setHotMedicine(json)
                    medicineList.addAll(medicineModel.getHotRiskMedicine())
                    medicineAdapter.notifyDataSetChanged()

                }
            }

        })
    }

    private fun initHomeEssay() {
        home_essay_list_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeEssayAdapter = HomeEssayAdapter(essayList)
        homeEssayAdapter.setOnClickListener(object : HomeEssayAdapter.OnClickListener {
            override fun onItemClick(v: View, position: Int) {
                Log.e(TAG, "essay item click $position")
                activity!!.jump2Activity(activity!!, DetailActivity::class.java, ESSAY, essayList[position])
            }
        })
        home_essay_list_view.adapter = homeEssayAdapter
        home_essay_list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        refreshHotEssay()
    }

    private fun refreshHotEssay() {

        HttpUtils.instance.doPost("$REQUEST_URL/popularartcilesearch", null, object : HttpUtils.HttpCallBack {
            override fun success(json: String) {
                GlobalScope.launch {
                    essayList.clear()
                    essayList.addAll(essayModel.getHotEssay(json))
                    GlobalScope.launch (UI){
                        homeEssayAdapter.notifyDataSetChanged()

                    }
                }
            }

        })
    }

    private fun initAdViewPager() {
        initAdAdapter()

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

    private fun initAdAdapter() {
        val map = HashMap<String, String>()
        map["type"] = "首页"
        HttpUtils.instance.doPost(
            "$REQUEST_URL/getadvertisement",
            map, object : HttpUtils.HttpCallBack {
                override fun success(json: String) {
                    GlobalScope.launch(UI) {
                        showAd(json)
                    }
                }
            })
    }

    private fun showAd(json: String) {
        list.clear()
        list.addAll(adMode.getAdvImages(json, context!!)!!)

        for (i in 0..(list.size - 1)) {
            val img = ImageView(activity)
            img.setBackgroundResource(R.drawable.shape_circle_viewpager_bg)
            val param = LinearLayout.LayoutParams(
                resources.getDimensionPixelOffset(R.dimen.ad_circle_width),
                resources.getDimensionPixelOffset(R.dimen.ad_circle_height)
            )
            if (i > 0) {
                param.leftMargin = resources.getDimensionPixelOffset(R.dimen.ad_circle_margin)
            } else {
                img.isSelected = true
            }
            img.layoutParams = param
            point_group.addView(img)
        }

        adAdapter = AdPagerAdapter(list, ad_viewpager)
        ad_viewpager.adapter = adAdapter

    }

    override fun onStop() {
        handler.removeMessages(0)
        super.onStop()
    }
}