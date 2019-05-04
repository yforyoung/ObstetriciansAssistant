package com.yyl.obstetriciansassistant.view.adapter

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.zzh.videodemo_kotlin.bean.ViewAttr
import com.kk.taurus.playerbase.entity.DataSource
import com.yyl.obstetriciansassistant.DURATION
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Video
import com.yyl.obstetriciansassistant.myview.play.AssistPlayer
import kotlinx.android.synthetic.main.item_tv_list.view.*

class TVAdapter(val list: List<Video>) : RecyclerView.Adapter<TVAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    var isAttach: Boolean = false
    var mPlayPosition: Int = -1
/*
    var attr: ViewAttr? = null
*/
    var mOnAnimationFinishListener: OnAnimationFinishListener? = null
/*
    var mOnMessageClickListener: OnMessageClickListener? = null
*/
    var mOnVideoPlayClickListener: OnVideoPlayClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_tv_list, p0, false)
        v.setOnClickListener {
            onItemClickListener!!.onItemClick(v, v.tag as Int)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val video = list[p1]
        with(p0.itemView){
            adapter_video_list_title.text=video.title
            adapter_video_list_container.removeAllViews()
            adapter_video_list_image.setOnClickListener {
                val dataSource = DataSource(video.url)
                AssistPlayer.get().play(adapter_video_list_container, dataSource)
                mPlayPosition = p0.layoutPosition
                mOnVideoPlayClickListener?.onVideoPlay(mPlayPosition)
            }

            viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    val l = IntArray(2)
                    getLocationOnScreen(l)
                   /* translationY = (attr!!.y - l[1]).toFloat()
                    adapter_video_list_container_layout.scaleX = attr!!.width / adapter_video_list_container_layout.measuredWidth.toFloat()
                    adapter_video_list_container_layout.scaleY = attr!!.height / adapter_video_list_container_layout.measuredHeight.toFloat()
                    */adapter_video_list_title.alpha = 0f
                    bottom_layout.alpha = 0f
                    animate().translationY(0f).duration = DURATION
                    adapter_video_list_container_layout.animate().scaleX(1f).scaleY(1f).duration = DURATION
                    adapter_video_list_title.animate().alpha(1f).duration = DURATION
                    bottom_layout.animate().alpha(1f).duration = DURATION
                    adapter_video_list_image.postDelayed({ mOnAnimationFinishListener?.onAnimationEnd() }, DURATION)
                    AssistPlayer.get().play(adapter_video_list_container_layout, null)
                    isAttach = false
                    mPlayPosition = 0
                    return true
                }
            })
        }
        p0.itemView.setOnClickListener {
            if (p0.layoutPosition != mPlayPosition) {
                p0.itemView.adapter_video_list_image.performClick()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)/*, AnimateViewHolder {
        override fun preAnimateAddImpl(Holder: RecyclerView.ViewHolder) {
            itemView.alpha = 0f
        }

        override fun preAnimateRemoveImpl(p0: RecyclerView.ViewHolder) {

        }

        override fun animateAddImpl(p0: RecyclerView.ViewHolder, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(itemView)
                .alpha(1f).setDuration(DURATION).start()
        }

        override fun animateRemoveImpl(p0: RecyclerView.ViewHolder, listener: ViewPropertyAnimatorListener) {
            ViewCompat.animate(itemView)
                .alpha(0f).setDuration(DURATION).start()
        }

    }*/

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    interface OnAnimationFinishListener {
        fun onAnimationEnd()
    }
    /*   interface onMessageClickListener {
           fun onMessageClick(bean: NewsBean, attr: ViewAttr)
       }*/

    interface OnVideoPlayClickListener {
        fun onVideoPlay(position: Int)
    }
}