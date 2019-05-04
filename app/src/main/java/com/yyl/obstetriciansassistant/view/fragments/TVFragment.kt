package com.yyl.obstetriciansassistant.view.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kk.taurus.playerbase.event.OnPlayerEventListener
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.UI
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.model.TVModelImpl
import com.yyl.obstetriciansassistant.myview.play.AssistPlayer
import com.yyl.obstetriciansassistant.myview.play.DataInter
import com.yyl.obstetriciansassistant.toast
import com.yyl.obstetriciansassistant.utils.VideoUtil
import com.yyl.obstetriciansassistant.view.adapter.TVAdapter
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.item_tv_list.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVFragment : Fragment(), OnPlayerEventListener, OnReceiverEventListener {

    private var list = arrayListOf<Video>()
    private lateinit var adapter: TVAdapter
    private val tvMode = TVModelImpl()

    private var isLandScape = false          //是否横屏

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        initTV()
        tv_list_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val manager = recyclerView.layoutManager as LinearLayoutManager
                    val first = manager.findFirstVisibleItemPosition()
                    val pos = manager.findFirstCompletelyVisibleItemPosition()
                    if (pos != adapter.mPlayPosition) {
                        val view = recyclerView.getChildAt(pos - first)
                        val image = view.findViewById<ImageView>(R.id.adapter_video_list_image)
                        image.performClick()
                    }
                }
            }
        })
        AssistPlayer.get().receiverGroup.groupValue.putBoolean(DataInter.Key.KEY_IS_HAS_NEXT, true)
        AssistPlayer.get().addOnPlayerEventListener(this)
        AssistPlayer.get().addOnReceiverEventListener(this)
    }

    private fun initTV() {
        adapter = TVAdapter(list)
        tv_list_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        tv_list_view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        tv_list_view.adapter = adapter
        adapter.mOnVideoPlayClickListener = object : TVAdapter.OnVideoPlayClickListener {
            override fun onVideoPlay(position: Int) {
                val manager = tv_list_view.layoutManager as LinearLayoutManager
                val first = manager.findFirstVisibleItemPosition()
                if (position == 0 && !tv_list_view.canScrollVertically(-1)) {
                    return
                }
                val view = tv_list_view.getChildAt(position - first)
                tv_list_view.smoothScrollBy(0, view.top - VideoUtil.dip2px(context!!, 40f))
            }

        }

        GlobalScope.launch(UI) {
            list.clear()
            val re = tvMode.getTVList()
            if (re.retcode == 1) {
                list.addAll(re.data!!)
                adapter.notifyDataSetChanged()
            } else {
                toast(re.retmsg)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        AssistPlayer.get().receiverGroup.groupValue.putBoolean(DataInter.Key.KEY_IS_HAS_NEXT, false)
        AssistPlayer.get().removePlayerEventListener(this)
        tv_list_view.clearOnScrollListeners()
    }


    /**
     * 监听是否有下一个
     */
    override fun onPlayerEvent(eventCode: Int, bundle: Bundle?) {
        when (eventCode) {
            OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE -> {
                // if (!isShowComment) {
                val pos = adapter.mPlayPosition
                if (pos <= list.size - 2) {
                    val manager = tv_list_view.layoutManager as LinearLayoutManager
                    val first = manager.findFirstVisibleItemPosition()
                    val view = tv_list_view.getChildAt(pos + 1 - first)
                    tv_list_view.smoothScrollBy(0, view.top)
                }
                //   }
            }
        }
    }


    override fun onReceiverEvent(eventCode: Int, bundle: Bundle?) {
        when (eventCode) {
            DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN -> activity!!.requestedOrientation = if (isLandScape)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

  /*  override fun closeCommentFragment() {
        isShowComment = false
        commentFragment!!.closeFragment()
        tv_list_view.postDelayed({
            val transaction = fragmentManager!!.beginTransaction()
            transaction.remove(commentFragment!!)
            transaction.commit()
            val holder =
                tv_list_view.findViewHolderForLayoutPosition(mAdapter!!.mPlayPosition) as VideoListAdapter.VideoHolder
            AssistPlayer.get().play(holder.container, null)
        }, MainActivity.DURATION)
    }*/


    fun isPlayingFirst(): Boolean {
        return AssistPlayer.get().isPlaying && adapter.mPlayPosition == 0
    }

    fun attachList() {
        val holder =
            tv_list_view.findViewHolderForLayoutPosition(adapter.mPlayPosition) as TVAdapter.ViewHolder
        AssistPlayer.get().play(holder.itemView.adapter_video_list_container, null)
    }

    /*fun attachCommentContainer() {
        commentFragment!!.attachContainer()
    }*/


}