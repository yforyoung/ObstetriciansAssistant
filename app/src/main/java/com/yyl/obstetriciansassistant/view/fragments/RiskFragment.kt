package com.yyl.obstetriciansassistant.view.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.model.RiskMedicineModel
import com.yyl.obstetriciansassistant.model.RiskMedicineModelImpl
import com.yyl.obstetriciansassistant.view.activities.RiskMoreActivity
import kotlinx.android.synthetic.main.fragment_risk.*

class RiskFragment : Fragment() {
    private lateinit var adapter: ArrayAdapter<String>
    private val riskMedicineModel: RiskMedicineModel = RiskMedicineModelImpl()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_risk, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRisk()
    }

    private fun initRisk() {
        adapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            riskMedicineModel.getInitial()
        )
        initial_list_view.adapter = adapter
        initial_list_view.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                run {
                    val intent = Intent(activity, RiskMoreActivity::class.java)
                    intent.putExtra("initial", riskMedicineModel.getInitial()[position])
                    startActivity(intent)
                }
            }
    }




}
