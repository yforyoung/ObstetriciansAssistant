package com.yyl.obstetriciansassistant.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.Notify
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.model.NotifyModel
import com.yyl.obstetriciansassistant.model.UserModel
import com.yyl.obstetriciansassistant.view.activities.*
import com.yyl.obstetriciansassistant.view.adapter.NotifyAdapter
import kotlinx.android.synthetic.main.fragment_user.*



class UserFragment : Fragment(), View.OnClickListener {
    private val user = SingleTon.instance.user!!
    private lateinit var mContext: FragmentActivity
    private val userModel = UserModel()
    private lateinit var notifyAdapter: NotifyAdapter
    private var notifyList= arrayListOf<Notify>()
    private val notifyModel = NotifyModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity!!
        initView()
    }

    override fun onResume() {
        super.onResume()
        notifyList.clear()
        notifyList.addAll(notifyModel.getNotify())
        notifyAdapter.notifyDataSetChanged()
    }

    private fun initView() {
        initNotify()

        user_name.text = user.name
        user_hospital.text = user.hospital
        user_position.text = user.position
        val options = RequestOptions().optionalCircleCrop()
        //Glide.with(context!!).load(R.mipmap.temp).apply(options).into(head_pic_img)
        Glide.with(context!!).load(user.attachment).apply(options).into(head_pic_img)


        logout_bt.setOnClickListener {
            userModel.logout()
            activity!!.jump2Activity(activity!!, LoadActivity::class.java)
            activity!!.finish()
        }
        case_bt.setOnClickListener(this)
        qa_bt.setOnClickListener(this)
        collection_bt.setOnClickListener(this)
        baby_weight_calculator.setOnClickListener(this)
        expected_date_calculator.setOnClickListener(this)
        healthy_weight_calculator.setOnClickListener(this)
        setting_bt.setOnClickListener(this)
    }

    private fun initNotify() {
        notifyAdapter= NotifyAdapter(notifyList)
        notifyAdapter.onItemClickListener=object :NotifyAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                val q=Question()
                q.id=notifyList[position].id
                notifyModel.readNotify(notifyList[position].notifyId)

                activity!!.jump2Activity(activity!!,DetailActivity::class.java, QA,q)
            }

        }
        notify_list_view.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        notify_list_view.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        notify_list_view.adapter=notifyAdapter

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.case_bt -> {
                mContext.jump2Activity(mContext, CaseActivity::class.java)
            }
            R.id.qa_bt -> {
                mContext.jump2Activity(mContext, MyQuestionActivity::class.java)
            }
            R.id.collection_bt -> {
                mContext.jump2Activity(mContext, MyCollectionActivity::class.java)
            }
            R.id.baby_weight_calculator -> {
                mContext.jump2Activity(mContext, ToolsActivity::class.java, BABY_WEIGHT)
            }
            R.id.expected_date_calculator -> {
                mContext.jump2Activity(mContext, ToolsActivity::class.java, EXCEPT_DAY)
            }
            R.id.healthy_weight_calculator -> {
                mContext.jump2Activity(mContext, ToolsActivity::class.java, HEALTHY_WEIGHT)
            }
            R.id.setting_bt -> {
                mContext.jump2Activity(mContext, SettingActivity::class.java)
            }
        }
    }
}