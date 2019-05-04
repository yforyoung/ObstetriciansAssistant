package com.yyl.obstetriciansassistant.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Answer
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.QAModelImpl
import com.yyl.obstetriciansassistant.model.SearchModelImpl
import com.yyl.obstetriciansassistant.view.adapter.AnswerAdapter
import kotlinx.android.synthetic.main.fragment_qa_detail.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QADetailFragment : Fragment() {


    private lateinit var question: Question
    private var list = arrayListOf<Answer>()
    private val qaModel = QAModelImpl()
    private lateinit var adapter: AnswerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qa_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        question = activity!!.intent.getBundleExtra(VALUE).getSerializable(VALUE) as Question
        question.createName=SingleTon.instance.user!!.name
        initView()
    }


    private fun initView() {

        initQuestion()

        qa_detail_content.text = question.content
        qa_detail_title.text = question.title
        qa_detail_questioner.text = question.createName
        add_answer_bt.setOnClickListener {
            val s = add_answer_edit.text.toString()
            if (s.isNotEmpty()) {
                progress_bar.visibility = View.VISIBLE
                GlobalScope.launch {
                    if (qaModel.addAnswer(question.id, s)) {
                        refreshAnswer()
                    }
                    else{
                        GlobalScope.launch (UI){
                            toast("回答失败！")
                            progress_bar.visibility=View.GONE

                        }
                    }
                }
            }
        }

        initAnswer()

    }

    private fun initQuestion() {

    }

    private fun initAnswer() {
        progress_bar.visibility = View.VISIBLE

        adapter = AnswerAdapter(list)
        qa_answer_list_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        qa_answer_list_view.adapter = adapter
        refreshAnswer()
        qa_answer_list_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.onLikeClickListener = object : AnswerAdapter.OnLikeClickListener {
            override fun onLikeClick(v: View, position: Int) {
                val answer = list[position]
                if (answer.type != "是") {
                    answer.type = "是"
                    GlobalScope.launch {
                        qaModel.setLike(answer.id)
                    }
                    answer.collection = (Integer.parseInt(answer.collection) + 1).toString()
                } else {
                    answer.type = "否"
/*
                    v.isSelected=true
*/
                    GlobalScope.launch {
                        qaModel.unLike(answer.id)
                    }
                    answer.collection = (Integer.parseInt(answer.collection) - 1).toString()
                }
                adapter.notifyDataSetChanged()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun refreshAnswer() {
        list.clear()
        GlobalScope.launch {
            val re=qaModel.getAnswers(question.id)
            GlobalScope.launch(UI) {
                if (re.retcode==1){
                    list.addAll(re.data!!)
                    adapter.notifyDataSetChanged()
                    qa_detail_answer_count.text = "${list.size} 个回答"
                    add_answer_edit.clearFocus()
                    add_answer_edit.setText("")
                }else{
                    toast("暂时没有回答哦")
                }
                progress_bar.visibility = View.GONE

            }
        }
    }

}
