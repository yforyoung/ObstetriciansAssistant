package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Answer
import kotlinx.android.synthetic.main.item_answer.view.*

class AnswerAdapter (val list: List<Answer>) : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    var onLikeClickListener:OnLikeClickListener?=null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.item_answer,p0,false)
        with(view){
            answer_like.setOnClickListener {
                if (onLikeClickListener!=null){
                    onLikeClickListener!!.onLikeClick(view, view.tag as Int)
                }
            }
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val answer=list[p1]
        with(p0.itemView){
            tag=p1
            answerer.text=answer.createName
            answer_content.text=answer.content
            answer_time.text=answer.createTime
            answer_like_count.text=answer.collection
            answer_like.isSelected=answer.type=="是"
            answer_like.tag=p1
        }
    }

    class ViewHolder (itemView:View): RecyclerView.ViewHolder(itemView)
    interface OnLikeClickListener{
        fun onLikeClick(v:View,position:Int)
    }
}