package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Case
import com.yyl.obstetriciansassistant.model.CaseModelImpl
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.view.adapter.CaseAdapter
import kotlinx.android.synthetic.main.fragment_case.*

class CaseFragment : Fragment() {
    private var list: MutableList<Case>? = arrayListOf()
    private val caseModel = CaseModelImpl()
    private lateinit var adapter: CaseAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_case, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCase()
    }

    private fun initCase() {
        list=caseModel.getCases()
        adapter = CaseAdapter(list!!)
        adapter.onItemClickListener = object : CaseAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                toast("item click")
            }

            override fun onItemEditClick(v: View, position: Int) {
                toast("edit click $position")
            }

            override fun onItemDeleteClick(v: View, position: Int) {
                if (caseModel.deleteCase(position)){
                    list!!.removeAt(position)
                    adapter.notifyDataSetChanged()
                }else{
                    toast("delete failed $position")
                }
            }

        }
        case_list_view.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        //case_list_view.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        case_list_view.adapter=adapter
    }
}