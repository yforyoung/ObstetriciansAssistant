package com.yyl.obstetriciansassistant.view.activities

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.QAModelImpl
import com.yyl.obstetriciansassistant.view.adapter.QAAdapter
import kotlinx.android.synthetic.main.activity_my_question.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyQuestionActivity : AppCompatActivity() {
    private lateinit var adapter: QAAdapter
    private var list = arrayListOf<Question>()

    private val qaModel = QAModelImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_question)
        initView()
    }

    @SuppressLint("InflateParams")
    private fun initView() {
        initQA()
    }

    private fun initQA() {
        my_qa_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = QAAdapter(list)
        adapter.setOnItemClickListener(object : QAAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                jump2Activity(this@MyQuestionActivity, DetailActivity::class.java, QA, list[position])
            }
        })
        my_qa_list_view.adapter = adapter

        updateQuestion()
    }

    private fun updateQuestion() {
        GlobalScope.launch(UI) {
            list.clear()
            val re = qaModel.getMyQaList()
            if (re.retcode == 1) {
                list.clear()
                if (re.data != null) {
                    for (q in re.data!!) {
                        if (q.type == "正常")
                            list.add(q)
                    }
                }
            }
            if (list.size > 0)
                empty_list_view.visibility = View.GONE
            else {
                //显示空
                empty_list_view.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }
}
