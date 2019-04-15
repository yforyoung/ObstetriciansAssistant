package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.SearchResult
import kotlinx.android.synthetic.main.item_search_list.view.*

class SearchAdapter(val list:List<SearchResult>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener?=null


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v=LayoutInflater.from(p0.context).inflate(R.layout.item_search_list,p0,false)
        v.setOnClickListener {
            onItemClickListener!!.onItemClick(v,v.tag as Int)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.tag=p1
        val searchResult=list[p1]
        with(p0.itemView){
            search_name.text=searchResult.title

            search_type.text=when(searchResult.type){
                0->"文章"
                1->"药物"
                2->"视频"
                3->"病例"
                4->"问题"
                else -> ""
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener{
        fun onItemClick(v:View,tag:Int)
    }
}