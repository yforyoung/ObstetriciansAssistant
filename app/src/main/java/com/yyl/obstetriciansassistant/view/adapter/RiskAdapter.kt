package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yyl.obstetriciansassistant.beans.Medicine

class RiskAdapter(var list:List<Medicine>) :RecyclerView.Adapter<RiskAdapter.ViewHolder>() {
    var onItemClickListener: OnItemClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v:View=LayoutInflater.from(p0.context).inflate(android.R.layout.simple_list_item_1,p0,false)
        v.setOnClickListener {
            onItemClickListener!!.onItemClick(v,v.tag as Int)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.tag=p1
        p0.text.text=list[p1].medicienName
    }

    class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val text= itemView.findViewById<TextView>(android.R.id.text1)!!
    }

    interface OnItemClickListener{
        fun onItemClick(v:View,position:Int)
    }
}