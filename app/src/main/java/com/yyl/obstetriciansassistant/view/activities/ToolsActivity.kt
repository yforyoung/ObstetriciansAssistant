package com.yyl.obstetriciansassistant.view.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.DatePicker
import com.yyl.obstetriciansassistant.*

import kotlinx.android.synthetic.main.activity_tools.*
import kotlinx.android.synthetic.main.dialog_baby_weight.*
import kotlinx.android.synthetic.main.dialog_baby_weight.view.*
import kotlinx.android.synthetic.main.dialog_except_date.*
import kotlinx.android.synthetic.main.dialog_except_date.view.*
import kotlinx.android.synthetic.main.dialog_healthy_weight.*
import kotlinx.android.synthetic.main.dialog_healthy_weight.view.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class ToolsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools)
        initView()
    }

    private fun initView() {
        val title = intent.getStringExtra(TYPE)
        toolbar_title.text = title
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        toolbar_back.setOnClickListener {
            finish()
        }

        when (title) {
            EXCEPT_DAY -> {
                except_date_layout.visibility = View.VISIBLE
                healthy_weight_layout.visibility = View.GONE
                baby_weight_layout.visibility = View.GONE
                calcuExceptDay()
            }
            HEALTHY_WEIGHT -> {
                except_date_layout.visibility = View.GONE
                healthy_weight_layout.visibility = View.VISIBLE
                baby_weight_layout.visibility = View.GONE
                calcuHealthyWeight()
            }
            BABY_WEIGHT -> {
                except_date_layout.visibility = View.GONE
                healthy_weight_layout.visibility = View.GONE
                baby_weight_layout.visibility = View.VISIBLE
                calcuBabyWeight()
            }
        }
    }

    private fun calcuHealthyWeight() {
        healthy_calculate_bt.setOnClickListener {
            val h = height_edit.text.toString().toFloat() / 100
            val w = weight_edit.text.toString().toFloat()
            if (h <= 0 || w <= 0) {
                toast("请输入正确的身高体重")
            }
            bmi.text = (w / (h * h)).toString()
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun calcuExceptDay() {
        last_menses_date.setOnClickListener {
            val ca = Calendar.getInstance()
            val mYear = ca.get(Calendar.YEAR)
            val mMonth = ca.get(Calendar.MONTH)
            val mDay = ca.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    last_menses_date.text = "$year-${month + 1}-$dayOfMonth"
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()

        }

        expect_date_calculate_bt.setOnClickListener {
            if (last_menses_date.text == "点击选择日期") {
                toast("请选择末次月经日期")
            } else {
                val format = SimpleDateFormat("yyyy-MM-dd")
                val date =
                    format.parse(last_menses_date.text.toString())!!
                val l:Long=1000 * 60 * 60 * 24 * 7 * 40L
                date.time = date.time + l
                except_date.text = format.format(date)
            }
        }
    }

    private fun calcuBabyWeight() {
        baby_weight_calculate_bt.setOnClickListener {
            val head: Double = baby_head_width.text.toString().toDouble()
            val bell = baby_bellies.text.toString().toDouble()
            val femur = baby_femur.text.toString().toDouble()
            val weight = head * head * head * 1.07 + 0.3 * bell * bell * femur
            except_baby_weight.text = weight.toFloat().toString()
        }
    }


}
