package com.yyl.obstetriciansassistant.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Case
import kotlinx.android.synthetic.main.item_case_list.view.*

class CaseAdapter(var list:List<Case>) : RecyclerView.Adapter<CaseAdapter.ViewHolder>() {
    var onItemClickListener: OnItemClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.item_case_list,p0,false)
        view.setOnClickListener {
            onItemClickListener!!.onItemClick(view,view.tag as Int)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.tag=p1
        val case=list[p1]
        with(p0.itemView){
            case_name.text=case.name
            case_reason.text="病因：${case.reason}"
            case_treatment.text="治疗方案：${case.treatment}"
            case_last_update_date.text="最近更新：${case.updateDate}"
            case_create_date.text=case.createDate

            case_edit.setOnClickListener {
                onItemClickListener!!.onItemEditClick(case_edit,p1)
            }
            case_delete.setOnClickListener {
                onItemClickListener!!.onItemDeleteClick(case_delete,p1)
            }
        }
    }

    class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener{
        fun onItemClick(v:View,position:Int)
        fun onItemEditClick(v:View,position: Int)
        fun onItemDeleteClick(v: View,position: Int)
    }
}