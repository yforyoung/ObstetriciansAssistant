package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.QAModelImpl
import com.yyl.obstetriciansassistant.view.activities.DetailActivity
import com.yyl.obstetriciansassistant.view.adapter.QAAdapter
import kotlinx.android.synthetic.main.fragment_qa.*

class QAFragment : Fragment() {
    private lateinit var adapter: QAAdapter
    private lateinit var list: List<Question>

    private val qaModel = QAModelImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qa, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initQA()
    }

    private fun initQA() {
        list = qaModel.getQAList()
        qa_list_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = QAAdapter(list)
        adapter.setOnItemClickListener(object : QAAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                activity!!.jump2Activity(activity!!, DetailActivity::class.java, QA,list[position])
            }
        })
        qa_list_view.adapter=adapter
    }
}