package com.yyl.obstetriciansassistant.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jude.rollviewpager.adapter.StaticPagerAdapter
import com.squareup.picasso.Picasso
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.beans.Adv

class RollPagerAdapter(val list: List<Adv>) : StaticPagerAdapter() {
    override fun getView(container: ViewGroup?, position: Int): View {
       /* val v=LayoutInflater.from(container!!.context).inflate(R.layout.img_roll_pager,null,false)
        val img=v.findViewById<ImageView>(R.id.img_roll_pager)*/
        val img = ImageView(container!!.context)
        img.scaleType = ImageView.ScaleType.FIT_XY
       // Glide.with(container.context).load(list[position].adv).into(img)
        Picasso.with(container.context).load(list[position].adv).into(img)
        return img
    }

    override fun getCount(): Int = list.size
}