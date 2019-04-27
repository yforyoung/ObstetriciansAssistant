package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.model.MedicineModelImpl
import com.yyl.obstetriciansassistant.view.adapter.MedicineAdapter
import kotlinx.android.synthetic.main.activity_risk_more.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RiskMoreActivity : AppCompatActivity() {
    private lateinit var adapter: MedicineAdapter
    private val riskMedicineModel = MedicineModelImpl()
    private lateinit var initial:String
    private var list= arrayListOf<Medicine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_more)

        initData()
        initView()
        initMedicine()
    }

    private fun initView() {
        risk_more_toolbar.title=""
        toolbar_title.text=initial
        risk_toolbar_back_bt.setOnClickListener {
            finish()
        }
        setSupportActionBar(risk_more_toolbar)
    }

    private fun initData() {
        initial=intent!!.getStringExtra("initial")
        list.clear()
        list.addAll(riskMedicineModel.getRiskMedicineListFromInitail(initial))

    }

    private fun initMedicine() {
        adapter = MedicineAdapter(list)
        GlobalScope.launch {
            list.clear()
            val re=riskMedicineModel.getInitial(initial)


            GlobalScope.launch(UI) {
                if(re.retcode==1){
                    list.addAll(re.data!!)
                    adapter.notifyDataSetChanged()
                }else{
                    toast(re.retmsg)
                }

            }
        }
        adapter.onItemClickListener = object : MedicineAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                //TODO 显示medicine信息页面

                jump2Activity(this@RiskMoreActivity,DetailActivity::class.java, MEDICINE,list[position])

            }
        }
        risk_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        risk_list_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        risk_list_view.adapter=adapter

    }
}
