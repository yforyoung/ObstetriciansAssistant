package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Medicine
import kotlinx.android.synthetic.main.item_risk_list.view.*

class HomeRiskAdapter(var list:List<Medicine>): RecyclerView.Adapter<HomeRiskAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener=onClickListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.item_risk_list,p0,false)
        view.setOnClickListener {
            if (onClickListener != null)
                onClickListener!!.onItemClick(view, view.tag as Int)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.tag=p1
        with(p0.itemView){
            medicine_name.text=list[p1].medicienName
            medicine_content.text=list[p1].medicineIntroduce
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnClickListener {
        fun onItemClick(v: View, position: Int)
    }
}