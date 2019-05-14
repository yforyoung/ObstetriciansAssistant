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
import com.yyl.obstetriciansassistant.beans.SearchHistory
import com.yyl.obstetriciansassistant.beans.SearchResult
import com.yyl.obstetriciansassistant.model.SearchModel
import com.yyl.obstetriciansassistant.view.adapter.SearchAdapter
import com.yyl.obstetriciansassistant.view.adapter.SearchHistoryAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var adapter: SearchAdapter
    private val searModel = SearchModel()
    private var type: String = ESSAY
    private var list = arrayListOf<SearchResult>()
    private lateinit var historyAdapter: SearchHistoryAdapter
    private var historyList = arrayListOf<SearchHistory>()

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
                    search(search_edit.text.toString(), type)
                }
                return false
            }
        })

        search_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    hot_search_title.visibility = View.VISIBLE
                    search_history_list.visibility = View.VISIBLE
                    empty_list_view.visibility = View.GONE
                    initHotSearchData()
                    refreshHistory()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                log(s.toString())
                if (s == "") {
                    hot_search_title.visibility = View.VISIBLE
                    initHotSearchData()
                    refreshHistory()
                } else {
                    list.clear()
                    adapter.notifyDataSetChanged()
                    hot_search_title.visibility = View.GONE
                    search_history_list.visibility = View.GONE

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
                            jump2Activity(this@SearchActivity, EssayDetailActivity::class.java, ESSAY, re.data!![0])
                        }
                        "medicine" -> {
                            val re = searModel.getMedicineSearch(item.title, item.id)
                            jump2Activity(this@SearchActivity, DetailActivity::class.java, MEDICINE, re.data!![0])
                        }
                        "video" -> {
                            val re = searModel.getVideoSearch(item.title, item.id)
                            jump2Activity(this@SearchActivity, TVDetailActivity::class.java, TV_VIDEO, re.data!![0])
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

    private fun search(name: String, type: String) {
        GlobalScope.launch(UI) {
            list.clear()
            list.addAll(searModel.getSearchResult(name, "", type))
            if (list.size > 0) {
                empty_list_view.visibility = View.GONE
            } else {
                empty_list_view.visibility = View.VISIBLE
            }

            adapter.notifyDataSetChanged()

        }

        hot_search_title.visibility = View.GONE
        search_history_list.visibility = View.GONE
    }

    private fun initHotSearchData() {
        GlobalScope.launch(UI) {
            list.clear()
            list.addAll(searModel.getHotSearch())
            adapter.notifyDataSetChanged()
        }
    }

    private fun initView() {

        initHistory()

        search_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = when (position) {
                    0 -> ESSAY
                    1 -> MEDICINE
                    2 -> TV_VIDEO
                    else -> ESSAY
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun initHistory() {
        historyAdapter = SearchHistoryAdapter(historyList)
        search_history_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_history_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        search_history_list.adapter = historyAdapter

        historyAdapter.onItemClickListener = object : SearchHistoryAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                search_edit.setText(historyList[position].title)
                search_spinner.setSelection(
                    when (historyList[position].type) {
                        ESSAY -> 0
                        MEDICINE -> 1
                        QA -> 2
                        else -> 0
                    }
                )
                type=historyList[position].type
                search_edit.requestFocus()
            }

            override fun onDeleteClick(v: View, position: Int) {
                searModel.deleteSearchHistory(historyList[position].id)
                historyList.removeAt(position)
                historyAdapter.notifyDataSetChanged()
            }

        }

        refreshHistory()
    }

    private fun refreshHistory() {
        historyList.clear()
        historyList.addAll(searModel.getSearchHistory())
        historyAdapter.notifyDataSetChanged()
    }
}
