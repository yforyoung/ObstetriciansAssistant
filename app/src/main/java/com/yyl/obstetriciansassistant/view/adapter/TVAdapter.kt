package com.yyl.obstetriciansassistant.view.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Video
import kotlinx.android.synthetic.main.fragment_tv.view.*
import kotlinx.android.synthetic.main.item_tv_list.view.*
import javax.sql.DataSource

class TVAdapter(val list: List<Video>) : RecyclerView.Adapter<TVAdapter.ViewHolder>() {
    private var onItemClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v=LayoutInflater.from(p0.context).inflate(R.layout.item_tv_list,p0,false)
        v.setOnClickListener {
            onItemClickListener!!.onItemClick(v,v.tag as Int)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int=list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        with(p0.itemView){
            tag=p1
            val dataSource= com.kk.taurus.playerbase.entity.DataSource()
            dataSource.uri= Uri.parse(list[p1].url)
            dataSource.title="title"
            tv_video_view.setDataSource(dataSource)
        }
    }

    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView)
    interface OnItemClickListener{
        fun onItemClick(v:View,position:Int)
    }
}