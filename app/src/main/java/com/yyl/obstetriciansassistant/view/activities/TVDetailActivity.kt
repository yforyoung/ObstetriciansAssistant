package com.yyl.obstetriciansassistant.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.kk.taurus.playerbase.entity.DataSource
import com.kk.taurus.playerbase.event.OnPlayerEventListener
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.UI
import com.yyl.obstetriciansassistant.VALUE
import com.yyl.obstetriciansassistant.beans.Comment
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.model.TVModel
import com.yyl.obstetriciansassistant.customizeview.play.AssistPlayer
import com.yyl.obstetriciansassistant.customizeview.play.DataInter
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.utils.SpfUtils
import com.yyl.obstetriciansassistant.view.adapter.TVCommentAdapter
import kotlinx.android.synthetic.main.activity_tvdetail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVDetailActivity : AppCompatActivity(), OnPlayerEventListener, OnReceiverEventListener {
    private var isLandScape = false          //是否横屏
    private var mFullScreen: FrameLayout? = null
    private var tvModel=TVModel()
    private var list= arrayListOf<Comment>()
    private lateinit var adapter:TVCommentAdapter


    override fun onReceiverEvent(eventCode: Int, bundle: Bundle?) {
        when (eventCode) {
            DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN -> requestedOrientation = if (isLandScape)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    override fun onPlayerEvent(eventCode: Int, bundle: Bundle?) {
        when (eventCode) {
            OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE -> AssistPlayer.get().stop()
        }
    }

    private lateinit var video: Video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_tvdetail)
        video = intent.getBundleExtra(VALUE).getSerializable(VALUE) as Video

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        tv_detail_title.text = "标题：${video.title}"
        tv_detail_create_date.text = "热度：${video.search}"
        tv_detail_search_times.text = "创建时间：${video.createTime}"

        initComment()

        add_tv_answer_bt.setOnClickListener {
            val s=add_tv_answer_edit.text.toString()
            if (s.trim()!=""){
                progress_bar.visibility=View.VISIBLE
                GlobalScope.launch (UI){
                    if (tvModel.addAnswer(s,video.id)){
                        refreshComment()
                        add_tv_answer_edit.clearFocus()
                        add_tv_answer_edit.setText("")
                    }else{
                        toast("添加回答失败，请稍后再试")
                    }
                    progress_bar.visibility=View.GONE
                }
            }
        }

        val dataSource = DataSource(video.url)
        AssistPlayer.get().prepare(detail_video_container, dataSource)
        if (isWifi()&&SpfUtils.instance.getSpfBoolean("prf_video_play_auto",true)){
            AssistPlayer.get().play(dataSource)
        }

        AssistPlayer.get().addOnPlayerEventListener(this)
        AssistPlayer.get().addOnReceiverEventListener(this)

        detail_video_image.setOnClickListener {
            AssistPlayer.get().play(dataSource)
        }

    }


    private fun isWifi(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }

    private fun initComment() {
        adapter=TVCommentAdapter(list)
        tv_comment_list_view.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        tv_comment_list_view.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        tv_comment_list_view.adapter=adapter
        refreshComment()
    }

    private fun refreshComment() {
        GlobalScope.launch (UI){
            val re=tvModel.getTVComment(video.id)
            list.clear()
            if (re.retcode==1){
                list.addAll(re.data!!)
            }
            if (list.size>0){
                empty_list_view.visibility=View.GONE
            }else{
                empty_list_view.visibility=View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AssistPlayer.get().removeReceiverEventListener(this)
        AssistPlayer.get().removePlayerEventListener(this)
        AssistPlayer.get().destroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        isLandScape = newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            attachFullScreen()
        } else {
            attachList()
        }
        AssistPlayer.get().receiverGroup.groupValue.putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandScape)
    }


    private fun attachFullScreen() {
        AssistPlayer.get().receiverGroup.groupValue.putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true)
        if (mFullScreen == null) {
            mFullScreen = FrameLayout(this)
            mFullScreen!!.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        root.addView(mFullScreen, -1)
        AssistPlayer.get().prepare(mFullScreen!!, null)
/*
        AssistPlayer.get().play(null)
*/
    }

    private fun attachList() {
        root.removeView(mFullScreen)
        AssistPlayer.get().receiverGroup.groupValue.putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false)
        AssistPlayer.get().prepare(detail_video_container, null)
/*
        AssistPlayer.get().play(null)
*/

    }

    override fun onBackPressed() {
        if (isLandScape) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            super.onBackPressed()
        }
    }
}
