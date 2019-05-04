package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.SearchResult
import com.yyl.obstetriciansassistant.model.SearchModelImpl
import com.yyl.obstetriciansassistant.view.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var adapter: SearchAdapter
    private val searModel = SearchModelImpl()
    private var type: String = ESSAY
    private var list = arrayListOf<SearchResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        initListView()

    }

    private fun initListView() {

        search_edit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    GlobalScope.launch(UI) {
                        list.clear()
                        list.addAll(searModel.getSearchResult(search_edit.text.toString(), "", type))
                        adapter.notifyDataSetChanged()
                    }
                }
                return false
            }
        })

        search_edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.toString()==""){
                    hot_search_title.visibility=View.VISIBLE
                    initHotSearchData()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                log(s.toString())
                if (s==""){
                    hot_search_title.visibility=View.VISIBLE
                    initHotSearchData()
                }else{
                    list.clear()
                    adapter.notifyDataSetChanged()
                    hot_search_title.visibility=View.GONE
                }
            }

        })

        adapter = SearchAdapter(list)
        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(v: View, tag: Int) {
                val item = list[tag]
                GlobalScope.launch(UI) {
                    when (item.type) {
                        "article" -> {
                            val re = searModel.getEssaySearch(item.title, item.id)
                            jump2Activity(this@SearchActivity, DetailActivity::class.java, ESSAY, re.data!![0])
                        }
                        "medicine" -> {
                            val re = searModel.getMedicineSearch(item.title, item.id)
                            jump2Activity(this@SearchActivity, DetailActivity::class.java, MEDICINE, re.data!![0])
                        }
                        "video" -> {
                            val re = searModel.getVideoSearch(item.title, item.id)
                            jump2Activity(this@SearchActivity, DetailActivity::class.java, TV_VIDEO, re.data!![0])
                        }

                    }
                }
            }

        })
        search_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_list_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        search_list_view.adapter = adapter

        initHotSearchData()

    }

    private fun initHotSearchData() {
        GlobalScope.launch(UI) {
            list.clear()
            list.addAll(searModel.getHotSearch())
            adapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        search_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = when (position) {
                    0 -> ESSAY
                    1 -> MEDICINE
                    2 -> TV_VIDEO
                    3 -> QA
                    else -> ESSAY
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
}
