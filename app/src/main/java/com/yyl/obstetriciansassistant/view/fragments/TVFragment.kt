package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.taurus.playerbase.entity.DataSource
import com.kk.taurus.playerbase.event.OnPlayerEventListener
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener
import com.kk.taurus.playerbase.receiver.ReceiverGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.UI
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.model.TVModelImpl
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.view.adapter.TVAdapter
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVFragment:Fragment(), OnPlayerEventListener, OnReceiverEventListener {
    private var list= arrayListOf<Video>()
    private lateinit var adapter:TVAdapter
    private val tvMode=TVModelImpl()

    override fun onReceiverEvent(eventCode: Int, bundle: Bundle?) {
        Log.e("young","$eventCode   onReceiver")

    }

    override fun onPlayerEvent(eventCode: Int, bundle: Bundle?) {
        Log.e("young","onPlayer $eventCode")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initTV()

        video_view.setOnPlayerEventListener(this)
        video_view.setOnReceiverEventListener(this)

        val dataSource=DataSource()
        dataSource.uri=DataSource.buildRawPath(activity!!.packageName,R.raw.vid)
        dataSource.title="title"
        video_view.setDataSource(dataSource)
        //video_view.start()
    }

    private fun initTV() {
        adapter= TVAdapter(list)
        tv_list_view.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        tv_list_view.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))

        GlobalScope.launch (UI){
            list.clear()
            val re=tvMode.getTVList()
            if(re.retcode==1){
                list.addAll(re.data!!)
                adapter.notifyDataSetChanged()
            }else{
                toast(re.retmsg)
            }

        }
    }
}