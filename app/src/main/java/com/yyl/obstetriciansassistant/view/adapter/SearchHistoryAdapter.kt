package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.ESSAY
import com.yyl.obstetriciansassistant.MEDICINE
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.TV_VIDEO
import com.yyl.obstetriciansassistant.beans.SearchHistory
import kotlinx.android.synthetic.main.item_search_history_list.view.*

class SearchHistoryAdapter(val list: List<SearchHistory>) : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {
    var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_search_history_list, p0, false)
        if (onItemClickListener != null) {
            v.setOnClickListener {
                onItemClickListener!!.onItemClick(v, v.tag as Int)
            }
        }
        return ViewHolder(v)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val s = list[p1]
        with(p0.itemView) {
            history_search_string.text = s.title
            history_search_type.text = when (s.type) {
                ESSAY -> "文章"
                MEDICINE -> "药物"
                TV_VIDEO -> "视频"
                else -> "文章"
            }
            tag = p1
            delete_history.tag = p1
            delete_history.setOnClickListener {
                onItemClickListener!!.onDeleteClick(delete_history, delete_history.tag as Int)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
        fun onDeleteClick(v: View, position: Int)
    }
}