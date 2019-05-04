package com.yyl.obstetriciansassistant.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.model.EssayModelImpl
import com.yyl.obstetriciansassistant.utils.HttpUtils
import kotlinx.android.synthetic.main.fragment_essay_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EssayDetailFragment : Fragment() {
    private lateinit var essay: Essay
    private val essayModel = EssayModelImpl()
    lateinit var collectionId:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_essay_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {


        GlobalScope.launch(UI) {
            essay_detail_star.isSelected = essayModel.isCollected(essay.id)
        }

        essay = activity!!.intent!!.getBundleExtra(VALUE).getSerializable(VALUE) as Essay
        essay_detail_title.text = essay.title
        essay_detail_author.text = "来源：${essay.source}"
        essay_address.text = "点击访问原文地址:${essay.address}"
        essay_address.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(essay.address)
            startActivity(intent)
        }
        val s = essay.content
        val re = Html.fromHtml(s).toString()
        essay_detail_content.text = re

    }

    private fun initView() {
        essay_detail_content.movementMethod = ScrollingMovementMethod.getInstance()
        val font = PreferenceManager.getDefaultSharedPreferences(activity).getString("prf_font_size", "小")
        when (font) {
            "大" -> essay_detail_content.textSize = 32.0f
            "中" -> essay_detail_content.textSize = 20.0f
            "小" -> essay_detail_content.textSize = 14.0f
        }

        /*收藏文章*/
        essay_detail_star.setOnClickListener {
            val selected = essay_detail_star.isSelected
            if (selected) {
                GlobalScope.launch(UI) {
                    val paramCollection = HashMap<String, String>()
                    paramCollection["id"] = SingleTon.instance.collectionId
                    GlobalScope.launch(UI) {
                        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/deluserarticle", paramCollection)
                        val re = SingleTon.instance.gson.fromJson(json, ResponseData::class.java)
                        if (re.retcode == 1) {
                            toast("取消收藏")
                            essay_detail_star.isSelected = !selected
                        } else {
                            toast(re.retmsg)
                        }
                    }
                }
            } else {
                val paramCollection = HashMap<String, String>()
                paramCollection["userid"] = SingleTon.instance.user!!.id
                paramCollection["articleid"] = essay.id
                GlobalScope.launch(UI) {
                    val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/userarticle", paramCollection)
                    val re = SingleTon.instance.gson.fromJson(json, ResponseData::class.java)
                    if (re.retcode == 1) {
                        toast("已收藏")
                        essay_detail_star.isSelected = !selected
                    } else {
                        toast(re.retmsg)
                    }
                }
            }
        }

        /*返回顶部*/
        back_to_top.setOnClickListener {
            essay_detail_scroll_view.smoothScrollTo(0, 0)
        }

    }
}
