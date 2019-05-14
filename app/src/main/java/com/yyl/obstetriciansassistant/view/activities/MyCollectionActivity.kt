package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.model.EssayModel
import com.yyl.obstetriciansassistant.model.SearchModel
import com.yyl.obstetriciansassistant.view.adapter.EssayAdapter
import kotlinx.android.synthetic.main.activity_my_collection.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyCollectionActivity : AppCompatActivity() {
    private val essayModel = EssayModel()
    val list = arrayListOf<Essay>()
    lateinit var adapter: EssayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun initView() {
        adapter = EssayAdapter(list)
        my_collection_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        my_collection_list_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        my_collection_list_view.adapter = adapter
        adapter.onItemClickListener = object : EssayAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                GlobalScope.launch(UI) {
                    val re = SearchModel().getEssaySearch(list[position].title, list[position].id)
                    if (re.retcode == 1) {
/*
                        jump2Activity(this@MyCollectionActivity, DetailActivity::class.java, ESSAY, re.data!![0])
*/
                        jump2Activity(this@MyCollectionActivity, EssayDetailActivity::class.java, ESSAY, re.data!![0])

                    }
                }
            }
        }
        initData()
    }

    private fun initData() {

        GlobalScope.launch(UI) {
            val re = essayModel.getCollectionResponse()
            if (re.retcode == 1) {
                list.clear()
                for (c in re.data!!){
                    list.add(Essay(c.title,c.articleId,c.type))
                }
            } else {
                toast(re.retmsg)
            }
            if (list.size > 0) {
                empty_list_view.visibility = View.GONE
            } else {
                empty_list_view.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }
}
