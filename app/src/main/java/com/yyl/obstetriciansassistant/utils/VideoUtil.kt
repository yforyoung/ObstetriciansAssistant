package com.yyl.obstetriciansassistant.utils

import android.content.Context

object VideoUtil {
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun dip2px(context: Context, value: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (value * scale + 0.5f).toInt()
    }

    fun getStatusBarHeight(context: Context): Int {
        var height = 0
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val o = c.newInstance()
            val field = c.getField("status_bar_height")
            val x: Int = field.get(o) as Int
            height = context.resources.getDimensionPixelOffset(x)
        } catch (e: Exception) {
        } finally {
            return height
        }
    }
}