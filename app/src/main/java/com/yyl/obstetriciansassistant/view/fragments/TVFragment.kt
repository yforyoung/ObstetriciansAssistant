package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.UI
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.jump2Activity
import com.yyl.obstetriciansassistant.model.TVModel
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.view.activities.TVDetailActivity
import com.yyl.obstetriciansassistant.view.adapter.TVAdapter
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVFragment : Fragment(){

    private var list = arrayListOf<Video>()
    private val tvMode = TVModel()

    private lateinit var tvAdapter:TVAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

    }

    private fun initView() {
        tvAdapter = TVAdapter(list)
        tv_list_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        tv_list_view.adapter = tvAdapter

        tvAdapter.onItemClickListener=object : TVAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                activity!!.jump2Activity(activity!!,TVDetailActivity::class.java,list[position])
            }

        }


        GlobalScope.launch(UI) {
            list.clear()
            val re = tvMode.getTVList()
            if (re.retcode == 1) {
                list.addAll(re.data!!)
                tvAdapter.notifyDataSetChanged()
            } else {
                toast(re.retmsg)
            }
        }
    }

}