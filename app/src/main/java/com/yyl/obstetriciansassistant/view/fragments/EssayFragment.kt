package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.model.EssayModelImpl
import com.yyl.obstetriciansassistant.model.SearchModelImpl
import com.yyl.obstetriciansassistant.view.activities.DetailActivity
import com.yyl.obstetriciansassistant.view.adapter.EssayAdapter
import kotlinx.android.synthetic.main.fragment_essay.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EssayFragment : Fragment() {
    private val essayModel = EssayModelImpl()
    val list = arrayListOf<Essay>()
    lateinit var adapter: EssayAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_essay, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        adapter = EssayAdapter(list)
        essay_list_view.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        essay_list_view.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
        essay_list_view.adapter = adapter
        adapter.onItemClickListener=object :EssayAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                GlobalScope.launch (UI){
                    val re=SearchModelImpl().getEssaySearch(list[position].title,list[position].id)
                    if (re.retcode==1){
                        activity!!.jump2Activity(activity!!, DetailActivity::class.java, ESSAY, re.data!![0])
                    }
                }

            }

        }
        initData()
    }

    private fun initData() {

        GlobalScope.launch(UI) {
            val re = essayModel.getEssayResponse()
            if (re.retcode == 1) {
                list.clear()
                list.addAll(re.data!!)
            } else {
                toast(re.retmsg)
            }
            adapter.notifyDataSetChanged()
        }
    }
}