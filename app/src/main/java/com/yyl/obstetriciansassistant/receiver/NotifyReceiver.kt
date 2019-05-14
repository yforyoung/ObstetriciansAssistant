package com.yyl.obstetriciansassistant.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Notify
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.NotifyModel
import com.yyl.obstetriciansassistant.view.activities.DetailActivity

class NotifyReceiver :BroadcastReceiver(){
    private lateinit var notify:Notify
    private val notifyModel=NotifyModel()

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle= intent!!.extras
        when(intent.action){
            JPushInterface.ACTION_CONNECTION_CHANGE->{
                if (bundle!!.getBoolean(JPushInterface.EXTRA_CONNECTION_CHANGE, false)){
                }
            }
            JPushInterface.ACTION_MESSAGE_RECEIVED->{
                //收到信息
                val json=bundle!!.getString(JPushInterface.EXTRA_MESSAGE)
                notify=SingleTon.instance.gson.fromJson(json,Notify::class.java)
                notifyModel.addNotify(notify)
                log(bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
            }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED->{
                //收到notify
                log(bundle.getString(JPushInterface.EXTRA_ALERT)!!)

            }
            JPushInterface.ACTION_NOTIFICATION_OPENED->{
                //点击notify
                log(bundle!!.getString(JPushInterface.EXTRA_EXTRA)!!+"extra")
                /*val q= Question()
                q.id=notify.id
                notifyModel.readNotify(notify.notifyId)
                bundle!!.getString(JPushInterface.EXTRA_EXTRA)
                context!!.jump2Activity(context, DetailActivity::class.java, QA,q)*/
            }
        }
    }

}