package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kk.taurus.playerbase.entity.DataSource
import com.kk.taurus.playerbase.event.OnPlayerEventListener
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener
import com.kk.taurus.playerbase.receiver.ReceiverGroup
import com.yyl.obstetriciansassistant.R
import kotlinx.android.synthetic.main.fragment_tv.*

class TVFragment:Fragment(), OnPlayerEventListener, OnReceiverEventListener {
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

        video_view.setOnPlayerEventListener(this)
        video_view.setOnReceiverEventListener(this)

        val dataSource=DataSource()
        dataSource.uri=DataSource.buildRawPath(activity!!.packageName,R.raw.vid)
        dataSource.title="title"
        video_view.setDataSource(dataSource)
        //video_view.start()
    }
}