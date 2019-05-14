package com.yyl.obstetriciansassistant.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Essay
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.model.EssayModel
import com.yyl.obstetriciansassistant.utils.HttpUtils
import kotlinx.android.synthetic.main.activity_essay_detail.*
import kotlinx.android.synthetic.main.content_essay_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EssayDetailActivity : AppCompatActivity() {
    private lateinit var essay: Essay
    private val essayModel = EssayModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_essay_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        initView()
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }


    @SuppressLint("SetTextI18n")
    private fun initData() {

        GlobalScope.launch(UI) {
            essay_detail_star.isSelected = essayModel.isCollected(essay.id)
        }

        essay = intent!!.getBundleExtra(VALUE).getSerializable(VALUE) as Essay
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

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            log(p1.toString())
            if (p1 == 0) {
                toolbar_layout!!.title = ""
                toolbar_expands_layout.visibility=View.VISIBLE
            } else {
                toolbar_layout!!.title = essay.title
                toolbar_expands_layout.visibility=View.GONE

            }
        })

    }

    private fun initView() {


        essay_detail_content.movementMethod = ScrollingMovementMethod.getInstance()
        val font = PreferenceManager.getDefaultSharedPreferences(this).getString("prf_font_size", "小")
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
