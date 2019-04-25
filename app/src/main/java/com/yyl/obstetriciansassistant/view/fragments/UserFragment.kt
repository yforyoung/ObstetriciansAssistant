package com.yyl.obstetriciansassistant.view.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.jump2Activity
import com.yyl.obstetriciansassistant.view.activities.SettingActivity
import kotlinx.android.synthetic.main.dialog_baby_weight.view.*
import kotlinx.android.synthetic.main.dialog_except_date.view.*
import kotlinx.android.synthetic.main.dialog_healthy_weight.view.*
import kotlinx.android.synthetic.main.fragment_user.*
import java.text.SimpleDateFormat
import java.util.*

class UserFragment : Fragment(), View.OnClickListener {
    private val user = SingleTon.instance.user!!
    private lateinit var mContext: FragmentActivity
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity!!
        initView()
    }

    private fun initView() {
        user_name.text = user.name
        user_hospital.text = user.hospital
        user_position.text = user.position

        case_bt.setOnClickListener(this)
        qa_bt.setOnClickListener(this)
        collection_bt.setOnClickListener(this)
        baby_weight_calculator.setOnClickListener(this)
        expected_date_calculator.setOnClickListener(this)
        healthy_weight_calculator.setOnClickListener(this)
        setting_bt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.case_bt -> {
            }
            R.id.qa_bt -> {
            }
            R.id.collection_bt -> {
            }
            R.id.baby_weight_calculator -> {
                showBabyWeightCalculator()
            }
            R.id.expected_date_calculator -> {
                showExpectedDateCalculator()
            }
            R.id.healthy_weight_calculator -> {
                showHealthyWeightCalculator()
            }
            R.id.setting_bt -> {
                mContext.jump2Activity(mContext, SettingActivity::class.java)
            }
        }
    }

    private fun showHealthyWeightCalculator() {
        dialog = Dialog(mContext, R.style.Base_ThemeOverlay_AppCompat_Dialog)
        val healthView = mContext.layoutInflater.inflate(R.layout.dialog_baby_weight, null, false)
        with(healthView) {
            healthy_calculate_bt.setOnClickListener {
                val bmi: Float
                val h = height_edit.text.toString().toFloat() / 100
                bmi = weight_edit.text.toString().toFloat() / h
                except_baby_weight.text = bmi.toString()
            }
        }
        dialog.setContentView(healthView)
        dialog.setTitle("健康体重计算器")
        dialog.create()
        dialog.show()
    }

    @SuppressLint("PrivateResource")
    private fun showExpectedDateCalculator() {
        dialog = Dialog(mContext, R.style.Base_ThemeOverlay_AppCompat_Dialog)
        val expectDateView = mContext.layoutInflater.inflate(R.layout.dialog_except_date, null, false)
        with(expectDateView) {
            expected_date_calculator.setOnClickListener {
                val format = SimpleDateFormat("yyyy-MM-dd")
                val date =
                    format.parse("${last_menses_date.year}-${last_menses_date.month}-${last_menses_date.dayOfMonth}")!!
                date.time = date.time + 7 * 24 * 60 * 60 * 40
                except_baby_weight.text = format.format(date)
            }
        }
        dialog.setContentView(expectDateView)
        dialog.setTitle("预产期计算器")
        dialog.create()
        dialog.show()
    }

    @SuppressLint("PrivateResource")
    private fun showBabyWeightCalculator() {
        dialog = Dialog(mContext, R.style.Base_ThemeOverlay_AppCompat_Dialog)
        val babyWeightView = mContext.layoutInflater.inflate(R.layout.dialog_baby_weight, null, false)
        with(babyWeightView) {
            calculate_bt.setOnClickListener {
                val head: Double = baby_head_width.text.toString().toDouble()
                val bell = baby_bellies.text.toString().toDouble()
                val femur = baby_femur.text.toString().toDouble()
                val weight = head * head * head * 0.0261 + 0.30408 * bell * bell * femur
                except_baby_weight.setText(weight.toInt())
            }
        }
        dialog.setContentView(babyWeightView)
        dialog.setTitle("胎儿体重计算器")
        dialog.create()
        dialog.show()
    }
}