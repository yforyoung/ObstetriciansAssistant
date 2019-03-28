package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.model.RiskMedicineModelImpl
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.view.adapter.RiskAdapter
import kotlinx.android.synthetic.main.activity_risk_more.*

class RiskMoreActivity : AppCompatActivity() {
    private lateinit var adapter: RiskAdapter
    private val riskMedicineModel = RiskMedicineModelImpl()
    private lateinit var initial:String
    private lateinit var list:List<Medicine>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_more)

        initData()
        initView()
        initRisk()
    }

    private fun initView() {
        risk_more_toolbar.title=""
        toolbar_title.text=initial
        toolbar_back_bt.setOnClickListener {
            finish()
        }
        setSupportActionBar(risk_more_toolbar)
    }

    private fun initData() {
        initial=intent!!.getStringExtra("initial")
        list=riskMedicineModel.getRiskMedicineListFromInitail(initial)
    }

    private fun initRisk() {
        adapter = RiskAdapter(list)
        adapter.onItemClickListener = object : RiskAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                //TODO 显示medicine信息页面
                toast("点击了$position  ${list[position].medicienName}")
            }
        }
        risk_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        risk_list_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        risk_list_view.adapter=adapter

    }
}
