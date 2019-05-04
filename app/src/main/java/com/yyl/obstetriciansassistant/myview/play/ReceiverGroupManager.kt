package com.yyl.obstetriciansassistant.myview.play

import android.content.Context
import com.kk.taurus.playerbase.receiver.GroupValue
import com.kk.taurus.playerbase.receiver.ReceiverGroup
import com.yyl.obstetriciansassistant.myview.cover.CompleteCover
import com.yyl.obstetriciansassistant.myview.cover.ControllerCover
import com.yyl.obstetriciansassistant.myview.cover.ErrorCover
import com.yyl.obstetriciansassistant.myview.cover.LoadingCover
import com.yyl.obstetriciansassistant.myview.play.DataInter.ReceiverKey.*


/**
 * Created by zhangzhihao on 2018/6/19 15:34.
 */

class ReceiverGroupManager private constructor() {

    fun getLittleReceiverGroup(context: Context): ReceiverGroup {
        return getLiteReceiverGroup(context, null)
    }

    fun getLittleReceiverGroup(context: Context, groupValue: GroupValue): ReceiverGroup {
        val receiverGroup = ReceiverGroup(groupValue)
        receiverGroup.addReceiver(KEY_LOADING_COVER, LoadingCover(context))
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, CompleteCover(context))
        receiverGroup.addReceiver(KEY_ERROR_COVER, ErrorCover(context))
        return receiverGroup
    }

    @JvmOverloads
    fun getLiteReceiverGroup(context: Context, groupValue: GroupValue? = null): ReceiverGroup {
        val receiverGroup = ReceiverGroup(groupValue)
        receiverGroup.addReceiver(KEY_LOADING_COVER, LoadingCover(context))
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, ControllerCover(context))
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, CompleteCover(context))
        receiverGroup.addReceiver(KEY_ERROR_COVER, ErrorCover(context))
        return receiverGroup
    }

    @JvmOverloads
    fun getReceiverGroup(context: Context, groupValue: GroupValue? = null): ReceiverGroup {
        val receiverGroup = ReceiverGroup(groupValue)
        receiverGroup.addReceiver(KEY_LOADING_COVER, LoadingCover(context))
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, ControllerCover(context))
        //        receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, CompleteCover(context))
        receiverGroup.addReceiver(KEY_ERROR_COVER, ErrorCover(context))
        return receiverGroup
    }

    companion object {

        private var i: ReceiverGroupManager? = null

        fun get(): ReceiverGroupManager {
            if (null == i) {
                synchronized(ReceiverGroupManager::class.java) {
                    if (null == i) {
                        i = ReceiverGroupManager()
                    }
                }
            }
            return i!!
        }
    }

}

