package com.yyl.obstetriciansassistant.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Comment
import kotlinx.android.synthetic.main.item_tv_comment_list.view.*

class TVCommentAdapter(val list: List<Comment>) : RecyclerView.Adapter<TVCommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_tv_comment_list, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val comment = list[p1]
        with(p0.itemView) {
            commenter.text=comment.name
            comment_content.text=comment.content
            comment_time.text=comment.createTime
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}