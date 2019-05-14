package com.yyl.obstetriciansassistant.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jude.rollviewpager.hintview.ColorPointHintView
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Adv
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.model.*
import com.yyl.obstetriciansassistant.utils.HttpUtils
import com.yyl.obstetriciansassistant.view.activities.DetailActivity
import com.yyl.obstetriciansassistant.view.activities.EssayDetailActivity
import com.yyl.obstetriciansassistant.view.activities.SearchActivity
import com.yyl.obstetriciansassistant.view.adapter.HomeEssayAdapter
import com.yyl.obstetriciansassistant.view.adapter.HomeMedicineAdapter
import com.yyl.obstetriciansassistant.view.adapter.RollPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.HashMap

class HomeFragment : Fragment() {
    private val imgList = arrayListOf<Adv>()

    private var essayList = arrayListOf<Essay>()
    private var medicineList = arrayListOf<Medicine>()

    private lateinit var homeEssayAdapter: HomeEssayAdapter
    private lateinit var medicineAdapter: HomeMedicineAdapter
    private lateinit var adAdapter: RollPagerAdapter
    private lateinit var essayAdapter: HomeEssayAdapter

    private val essayModel = EssayModel()
    private val medicineModel = MedicineModel()

    private val adMode = AdvertisementModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

    }

    private fun initView() {
        initAdViewPager()
        initHomeEssay()
        initHomeMedicine()
        search_bt.setOnClickListener {
            activity!!.jump2Activity(activity!!, SearchActivity::class.java)
        }
    }

    private fun initHomeMedicine() {
        home_risk_list_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        medicineAdapter = HomeMedicineAdapter(medicineModel.getHotRiskMedicine())
        medicineAdapter.setOnClickListener(object : HomeMedicineAdapter.OnClickListener {
            override fun onItemClick(v: View, position: Int) {
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
        essayAdapter = HomeEssayAdapter(essayList)
        home_essay_list_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeEssayAdapter = essayAdapter
        homeEssayAdapter.setOnClickListener(object : HomeEssayAdapter.OnClickListener {
            override fun onItemClick(v: View, position: Int) {
                activity!!.jump2Activity(activity!!,EssayDetailActivity::class.java, ESSAY,essayList[position])
/*
                activity!!.jump2Activity(activity!!, DetailActivity::class.java, ESSAY, essayList[position])
*/
            }
        })
        home_essay_list_view.adapter = homeEssayAdapter
        home_essay_list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        refreshHotEssay()
    }

    private fun refreshHotEssay() {
        GlobalScope.launch {
            val re = essayModel.getHotEssayResponse()
            if (re.retcode == 1) {
                essayList.clear()
                essayList.addAll(re.data!!)
                launch(UI) {
                    essayAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initAdViewPager() {
        adAdapter = RollPagerAdapter(imgList)
        roll_viewpager.setAdapter(adAdapter)
        roll_viewpager.setPlayDelay(3000)
        roll_viewpager.setAnimationDurtion(500)
        roll_viewpager.setHintView(ColorPointHintView(activity!!, Color.parseColor("#FF00B0FF"), Color.WHITE))
        initAdvData()
    }

    private fun initAdvData() {
        val map = HashMap<String, String>()
        map["type"] = "首页"

        GlobalScope.launch(UI) {
            val re = adMode.getHomeAdvResponse()
            if (re.retcode == 1) {
                imgList.clear()
                imgList.addAll(re.data!!)
            } else {
                toast(re.retmsg)
            }
            adAdapter.notifyDataSetChanged()
        }

    }
}