package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.IS_CHANGE
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.VALUE
import com.yyl.obstetriciansassistant.beans.Case
import kotlinx.android.synthetic.main.fragment_case_detail.*

class CaseDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_case_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        val case:Case= activity!!.intent.getBundleExtra(VALUE).getSerializable(VALUE) as Case
        case_detail_name.setText(case.name)
        case_detail_create_time.text = case.createDate
        case_detail_last_update.text=case.updateDate
        case_detail_reason.setText(case.reason)
        case_detail_treatment.setText(case.treatment)


    }

    private fun initView() {
        /* textChangeable(arguments?.getBoolean(IS_CHANGE,false))*/
        textChangeable(activity!!.intent.getBooleanExtra(IS_CHANGE, false))
        case_detail_change_bt.setOnClickListener {
            saveChange()
            textChangeable(false)
        }
        case_detail_change_cancel_bt.setOnClickListener {
            restore()
            textChangeable(false)
        }
    }

    private fun saveChange() {
        //保存
    }

    private fun restore() {
        //重新加载
    }

    private fun textChangeable(b: Boolean) {
        case_detail_age.isFocusable = b
        case_detail_depiction.isFocusable = b
        case_detail_name.isFocusable = b
        case_detail_reason.isFocusable = b
        case_detail_treatment.isFocusable = b
        case_detail_tips.isFocusable = b
        if (b) {
            case_detail_change_bt_layout.visibility = View.VISIBLE
        } else {
            case_detail_change_bt_layout.visibility = View.GONE
        }

    }

}
