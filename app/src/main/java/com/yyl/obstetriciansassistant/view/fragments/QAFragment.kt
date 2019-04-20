package com.yyl.obstetriciansassistant.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.QAModelImpl
import com.yyl.obstetriciansassistant.view.activities.DetailActivity
import com.yyl.obstetriciansassistant.view.adapter.QAAdapter
import kotlinx.android.synthetic.main.dialog_add_question.view.*
import kotlinx.android.synthetic.main.fragment_qa.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QAFragment : Fragment() {
    private lateinit var adapter: QAAdapter
    private var list= arrayListOf<Question>()

    private val qaModel = QAModelImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qa, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

    }

    @SuppressLint("InflateParams")
    private fun initView() {
        initQA()
        add_question_bt.setOnClickListener {
            val dialog=Dialog(activity!!)

            val v=layoutInflater.inflate(R.layout.dialog_add_question,null,false)

            val typeList= arrayListOf<String>()
            val typeAdapter = ArrayAdapter<String>(
                context!!,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                typeList
            )
            with(v){
                question_type_spinner.adapter=typeAdapter
                GlobalScope.launch {
                    typeList.clear()
                    typeList.addAll(qaModel.getTypeName())
                    GlobalScope.launch(UI) {
                        typeAdapter.notifyDataSetChanged()
                    }
                }
                add_question_confirm_bt.setOnClickListener {
                    val title=add_question_title.text.toString()
                    val content=add_question_content.text.toString()
                    val type=qaModel.getTypeId(question_type_spinner.selectedItemPosition)

                    GlobalScope.launch {
                        if (title.isNotEmpty()){
                            if (qaModel.addQuestion(title,content,type)) {
                                updateQuestion()
                            }else{
                                toast("添加失败")
                            }
                            dialog.dismiss()

                        }
                    }
                }

                add_question_cancel_bt.setOnClickListener {
                    dialog.dismiss()
                }
            }
            dialog.setContentView(v)
            dialog.create()
            dialog.show()/*
            AlertDialog.Builder(activity)
                .setView(v)
                .create()
                .show()*/

        }

    }

    private fun initQA() {
        qa_list_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = QAAdapter(list)
        adapter.setOnItemClickListener(object : QAAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                activity!!.jump2Activity(activity!!, DetailActivity::class.java, QA,list[position])
            }
        })
        qa_list_view.adapter=adapter

        updateQuestion()
    }

    private fun updateQuestion() {
        GlobalScope.launch {
            list.clear()
            list .addAll(qaModel.getQAList())
            GlobalScope.launch (UI){
                adapter.notifyDataSetChanged()
            }
        }
    }
}