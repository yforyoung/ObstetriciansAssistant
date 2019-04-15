package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.VALUE
import com.yyl.obstetriciansassistant.beans.Answer
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.QAModelImpl
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.view.adapter.AnswerExpandAdapter
import kotlinx.android.synthetic.main.fragment_qa_detail.*

class QADetailFragment : Fragment() {
    private lateinit var adapter:AnswerExpandAdapter
    private var list= arrayListOf<Answer>()
    private val qaModel=QAModelImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qa_detail,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        val question:Question= activity!!.intent.getBundleExtra(VALUE).getSerializable(VALUE) as Question
        list.clear()
        list.addAll(question.answers)
        adapter.notifyDataSetChanged()

    }

    private fun initView() {
        list= qaModel.getAnswer() as ArrayList<Answer>
        adapter=AnswerExpandAdapter(list)
        qa_answer_list_view.setAdapter(adapter)
        qa_answer_list_view.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener{
            override fun onGroupClick(parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long): Boolean {
                if (qa_answer_list_view.isGroupExpanded(groupPosition)){
                    qa_answer_list_view.collapseGroup(groupPosition)
                }else{
                    qa_answer_list_view.expandGroup(groupPosition)
                }
                return true
            }
        })

        qa_answer_list_view.setOnChildClickListener (object :ExpandableListView.OnChildClickListener{
            override fun onChildClick(
                parent: ExpandableListView?,
                v: View?,
                groupPosition: Int,
                childPosition: Int,
                id: Long
            ): Boolean {
                toast("点击了$childPosition")
                return false
            }
        })
    }

}
