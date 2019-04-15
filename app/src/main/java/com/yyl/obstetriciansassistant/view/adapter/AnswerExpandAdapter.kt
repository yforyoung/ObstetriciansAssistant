package com.yyl.obstetriciansassistant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Answer
import kotlinx.android.synthetic.main.item_answer_child.view.*
import kotlinx.android.synthetic.main.item_answer_group.view.*

class AnswerExpandAdapter(val list: List<Answer>) : BaseExpandableListAdapter() {

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val v=LayoutInflater.from(parent!!.context).inflate(R.layout.item_answer_child,parent,false)
        with(v){
            item_answer_child_content.text=list[groupPosition].replyList[childPosition]
        }
        return v
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return list[groupPosition].replyList.size
    }

    override fun getGroup(groupPosition: Int): Any = list[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any = list[groupPosition].replyList[childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun hasStableIds(): Boolean = true

    override fun getChildId(groupPosition: Int, childPosition: Int): Long =
        getCombinedChildId(groupPosition.toLong(), childPosition.toLong())

    /***********重要*/
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val v= LayoutInflater.from(parent!!.context).inflate(R.layout.item_answer_group, parent, false)
        v.tag=groupPosition
        with(v){
            item_answer_group_content.text=list[groupPosition].content
        }
        return v
    }

    override fun getGroupCount(): Int = list.size

}