package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.VALUE
import com.yyl.obstetriciansassistant.beans.Medicine
import kotlinx.android.synthetic.main.fragment_medicine_detail.*
import java.net.URLDecoder

class MedicineDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_medicine_detail,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        val medicine:Medicine= activity!!.intent!!.getBundleExtra(VALUE).getSerializable(VALUE) as Medicine
        medicine_detail_title.text=medicine.name
        medicine_detail_english_name.text=medicine.englishName
        val s=medicine.introduce
        val re= URLDecoder.decode(s, "utf-8")
        medicine_detail_content.text=re

    }

    private fun initView() {

    }

}