package com.yyl.obstetriciansassistant.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Case
import com.yyl.obstetriciansassistant.model.CaseModel
import kotlinx.android.synthetic.main.fragment_case_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CaseDetailFragment : Fragment() {
    private var change = 0
    private var caseModel = CaseModel()
    private var case: Case? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_case_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        if (change == VIEW_CASE || change == EDIT_CASE) {
            case = activity!!.intent.getBundleExtra(VALUE).getSerializable(VALUE) as Case
            case_detail_name.setText(case!!.name)
            case_detail_create_time.text = "创建时间：${case!!.createDate}"
            //rnf exception
            case_detail_reason.setText("${case!!.reason} ")
            case_detail_treatment.setText("${case!!.treatment} ")
            case_detail_age.setText("${case!!.age} ")
            case_detail_depiction.setText("${case!!.content} ")
            case_detail_tips.setText("${case!!.tips} ")
        }
    }

    private fun initView() {
        change = activity!!.intent.getIntExtra(IS_CHANGE, VIEW_CASE)

        /* textChangeable(arguments?.getBoolean(IS_CHANGE,false))*/
        textChangeable(change == EDIT_CASE || change == CREATE_CASE)

        case_detail_delete.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("您正在删除${case!!.name}的病例，请谨慎操作")
                .setPositiveButton("删除") { _, _ ->
                    GlobalScope.launch(UI) {
                        if (caseModel.deleteCase(case!!.id)) {
                            activity!!.finish()
                        } else {
                            toast("删除失败，请稍后再试")
                        }
                    }

                }
                .setNegativeButton("取消", null)
                .create()
                .show()

        }

        case_detail_change_bt.setOnClickListener {
            when (change) {
                EDIT_CASE -> saveChange()
                CREATE_CASE -> createCase()
            }

            textChangeable(false)
        }
        case_detail_change_cancel_bt.setOnClickListener {
            when (change) {
                EDIT_CASE -> restore()
                CREATE_CASE -> activity!!.finish()
            }
            textChangeable(false)
        }
    }

    private fun createCase() {
        val caseNew = Case()
        caseNew.treatment = case_detail_treatment.text.toString()
        caseNew.reason = case_detail_reason.text.toString()
        caseNew.name = case_detail_name.text.toString()
        caseNew.age = case_detail_age.text.toString().toInt()
        caseNew.tips = case_detail_tips.text.toString()
        caseNew.content = case_detail_depiction.text.toString()
        GlobalScope.launch(UI) {
            if (caseModel.saveCase(caseNew)) {
                toast("保存成功")
                activity!!.finish()
            }
        }
    }

    private fun saveChange() {
        val caseNew = Case()
        caseNew.treatment = case_detail_treatment.text.toString()
        caseNew.reason = case_detail_reason.text.toString()
        caseNew.name = case_detail_name.text.toString()
        caseNew.age = case_detail_age.text.toString().trim().toInt()
        caseNew.tips = case_detail_tips.text.toString()
        caseNew.content = case_detail_depiction.text.toString()
        GlobalScope.launch(UI) {
            if (caseModel.editCase(case!!.id ,caseNew)) {
                toast("保存成功")
            }
        }
    }

    private fun restore() {
        initData()
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
