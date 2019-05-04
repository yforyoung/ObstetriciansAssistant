package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Essay
import kotlinx.android.synthetic.main.item_essay_list.view.*

class EssayAdapter(val list:List<Essay>): RecyclerView.Adapter<EssayAdapter.ViewHolder>() {
    var onItemClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v=LayoutInflater.from(p0.context).inflate(R.layout.item_essay_list,p0,false)
        if (onItemClickListener!=null){
            v.setOnClickListener { onItemClickListener!!.onItemClick(v,v.tag as Int) }
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int =list.size
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val essay=list[p1]
        with(p0.itemView){
            tag=p1
            essay_title.text=essay.title
            essay_author.text=essay.type
            essay_source.text=essay.source
        }
    }

    class ViewHolder (itemView:View): RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener{
        fun  onItemClick(v:View,position:Int)
    }
}
