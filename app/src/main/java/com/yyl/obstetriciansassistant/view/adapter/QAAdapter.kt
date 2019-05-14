package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Question
import kotlinx.android.synthetic.main.item_qa_list.view.*

class QAAdapter(val list: List<Question>):RecyclerView.Adapter<QAAdapter.ViewHolder>() {
    private var onItemClickListener:OnItemClickListener?=null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v=LayoutInflater.from(p0.context).inflate(R.layout.item_qa_list,p0,false)
        v.setOnClickListener {
            onItemClickListener!!.onItemClick(v,v.tag as Int)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val question=list[p1]
        with(p0.itemView){
            tag=p1
            qa_title.text=question.title
            qa_content.text=question.content
            question_create_date.text=question.createDate

            /*
            answer_count.text= question.answers.size.toString()
            view_count.text=question.viewCount.toString()
            latest_answer.text=question.answers[question.answers.size-1].answerDate*/
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    interface OnItemClickListener{
        fun onItemClick(v:View,position:Int)
    }
}