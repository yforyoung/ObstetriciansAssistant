package com.yyl.obstetriciansassistant.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Notify
import kotlinx.android.synthetic.main.item_notify_list.view.*

class NotifyAdapter(val list: List<Notify>) : RecyclerView.Adapter<NotifyAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_notify_list, p0, false)
        if (onItemClickListener != null) {
            v.setOnClickListener {
                onItemClickListener!!.onItemClick(v, v.tag as Int)
            }
        }
        return ViewHolder(v)

    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val notify = list[p1]
        with(p0.itemView) {
            tag = p1

            notify_answer_name.text = "${notify.name} ${when (notify.type) {
                "回答"->"回答了您的提问"
                else ->"点赞了您的回答"
            }}"
            notify_question.text = notify.content
            if (notify.read) {
                notify_img.visibility = View.GONE
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

}