package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.SearchResult
import com.yyl.obstetriciansassistant.model.SearchModelImpl
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.view.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var adapter:SearchAdapter
    private val searModel = SearchModelImpl()
    private var type:Int=0
    private var list= arrayListOf<SearchResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        initListView()

    }

    private fun initListView() {
        list.clear()
        list.addAll(searModel.getHotSearch())
        adapter= SearchAdapter(list)
        adapter.setOnItemClickListener(object :SearchAdapter.OnItemClickListener{
            override fun onItemClick(v: View, tag: Int) {
                toast("click $tag")
            }

        })
        search_list_view.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        search_list_view.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        search_list_view.adapter=adapter
    }

    private fun initView() {
        search_spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type=position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        search_edit.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                hot_search_title.visibility=View.GONE
                list.clear()
                list.addAll(searModel.getSearchResult(type))
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("young",newText)
                if(newText==""){
                    hot_search_title.visibility=View.VISIBLE
                    list.clear()
                    list.addAll(searModel.getHotSearch())
                    adapter.notifyDataSetChanged()
                }
                return false
            }

        })
    }
}
