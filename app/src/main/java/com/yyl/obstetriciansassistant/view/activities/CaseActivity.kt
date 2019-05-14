package com.yyl.obstetriciansassistant.view.activities

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Case
import com.yyl.obstetriciansassistant.model.CaseModel
import com.yyl.obstetriciansassistant.view.adapter.CaseAdapter
import kotlinx.android.synthetic.main.activity_case.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CaseActivity : AppCompatActivity() {

    private val list = arrayListOf<Case>()
    private val caseModel = CaseModel()
    private lateinit var adapter: CaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun initView() {
        initCase()
        add_case_bt.setOnClickListener {
            jump2Activity(this,DetailActivity::class.java, CASE, CREATE_CASE,null)
        }
    }

    private fun initCase() {
        adapter = CaseAdapter(list)
        adapter.onItemClickListener = object : CaseAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                jump2Activity(this@CaseActivity, DetailActivity::class.java, CASE, list[position])
            }

            override fun onItemEditClick(v: View, position: Int) {
                jump2Activity(this@CaseActivity, DetailActivity::class.java, CASE, EDIT_CASE, list[position])
            }

            override fun onItemDeleteClick(v: View, position: Int) {
                AlertDialog.Builder(this@CaseActivity)
                    .setTitle("提示")
                    .setMessage("您正在删除${list[position].name}的病例，请谨慎操作")
                    .setPositiveButton("删除") { _, _ ->
                        GlobalScope.launch (UI){
                            if (caseModel.deleteCase(list[position].id)) {
                                list.removeAt(position)
                                adapter.notifyDataSetChanged()
                            } else {
                                toast("删除失败")
                            }
                        }

                    }
                    .setNegativeButton("取消", null)
                    .create()
                    .show()

            }
        }
        case_list_view.layoutManager = LinearLayoutManager(this@CaseActivity, LinearLayoutManager.VERTICAL, false)
        case_list_view.adapter = adapter

        refreshCase()
    }


    override fun onResume() {
        super.onResume()
        refreshCase()
    }

    private fun refreshCase() {
        GlobalScope.launch(UI) {

            val re = caseModel.getCaseListResponse()
            list.clear()
            if (re.retcode == 1) {
                if (re.data != null) {
                    list.addAll(re.data!!)
                }
            }
            if (list.size >= 1) {
                empty_list_view.visibility = View.GONE
            } else {
                empty_list_view.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }
}
