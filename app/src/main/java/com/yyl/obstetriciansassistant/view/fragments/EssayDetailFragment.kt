package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.VALUE
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.toast
import kotlinx.android.synthetic.main.fragment_essay_detail.*
import java.net.URLDecoder

class EssayDetailFragment : Fragment() {
    private lateinit var essay: Essay
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_essay_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initData() {
        essay= activity!!.intent!!.getBundleExtra(VALUE).getSerializable(VALUE) as Essay
        essay_detail_title.text=essay.title
        essay_detail_author.text=essay.source
        val s=essay.content
        val re=Html.fromHtml(s).toString()
/*
        val re=URLDecoder.decode(s, "utf-8")
*/
        essay_detail_content.text=re

    }

    private fun initView() {
        essay_detail_content.movementMethod = ScrollingMovementMethod.getInstance()

        /*收藏文章*/
        essay_detail_star.setOnClickListener {
            val selected=essay_detail_star.isSelected
            if (selected){
                toast("取消收藏")
            }
            else{
                toast("已收藏")
            }
            essay_detail_star.isSelected = !selected
        }

        /*返回顶部*/
        back_to_top.setOnClickListener {
            essay_detail_scroll_view.smoothScrollTo(0,0)
        }

    }
}
