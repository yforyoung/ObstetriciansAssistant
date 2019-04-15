package com.yyl.obstetriciansassistant.myview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ExpandableListView

class NestedExpandableListView(context: Context, attributes: AttributeSet) : ExpandableListView(context,attributes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE / 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}