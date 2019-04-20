package com.yyl.obstetriciansassistant.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class RegisterSpinnerAdapter<T>(context: Context, private val itemView:Int):ArrayAdapter<T>(context,itemView){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view=LayoutInflater.from(parent.context).inflate(itemView,parent,false)
        return super.getView(position, convertView, parent)
    }

}