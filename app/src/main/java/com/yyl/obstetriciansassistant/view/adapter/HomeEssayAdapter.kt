package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Essay
import kotlinx.android.synthetic.main.item_essay_list.view.*


class HomeEssayAdapter(var list: List<Essay>) : RecyclerView.Adapter<HomeEssayAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_essay_list, p0, false)
        view.setOnClickListener {
            if (onClickListener != null)
                onClickListener!!.onItemClick(view, view.tag as Int)
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.itemView.tag = p1
        with(p0.itemView) {
            essay_title.text = list[p1].title
            essay_author.text = list[p1].author
            val content = list[p1].content
            if (content.length > 100)
                essay_content.text = content.substring(0, 100)
            else
                essay_content.text = list[p1].content
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnClickListener {
        fun onItemClick(v: View, position: Int)
    }

}