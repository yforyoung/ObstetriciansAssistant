package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Video
import kotlinx.android.synthetic.main.item_tv_list.view.*

class TVAdapter(val list: List<Video>) : RecyclerView.Adapter<TVAdapter.ViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_tv_list, p0, false)
        v.setOnClickListener {
            onItemClickListener!!.onItemClick(v, v.tag as Int)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val video = list[p1]
        with(p0.itemView) {
            tag=p1
            adapter_video_list_title.text = video.title
            Glide.with(context).load(video.imgUrl).into(adapter_video_list_image)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }
}