package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.model.UserModelImpl
import com.yyl.obstetriciansassistant.utils.HttpUtils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val userModel = UserModelImpl()
    private var ne=false
    private var pe=false
    private var pce=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        initListener()
    }


    private fun initView() {

        GlobalScope.launch {
            if (userModel.setRegisterBefore()){
                val hospitalAdapter = ArrayAdapter<String>(
                    this@RegisterActivity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    userModel.getHospitals()
                )
                register_hospital_spinner.adapter = hospitalAdapter

                val positionAdapter = ArrayAdapter<String>(
                    this@RegisterActivity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    userModel.getPositions()
                )
                register_position_spinner.adapter = positionAdapter


                val clazzAdapter = ArrayAdapter<String>(
                    this@RegisterActivity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    userModel.getClazz()
                )
                register_clazz_spinner.adapter = clazzAdapter
            }
        }

/*
        HttpUtils.instance.doPost("$REQUEST_URL/registerbefore", null, object : HttpUtils.HttpCallBack {
            override fun success(json: String) {
                GlobalScope.launch(UI) {
                    userModel.setRegisterBefore(json)
                    if (userModel.setRegisterBefore()){
                        val hospitalAdapter = ArrayAdapter<String>(
                            this@RegisterActivity,
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            userModel.getHospitals()
                        )
                        register_hospital_spinner.adapter = hospitalAdapter

                        val positionAdapter = ArrayAdapter<String>(
                            this@RegisterActivity,
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            userModel.getPositions()
                        )
                        register_position_spinner.adapter = positionAdapter


                        val clazzAdapter = ArrayAdapter<String>(
                            this@RegisterActivity,
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            userModel.getClazz()
                        )
                        register_clazz_spinner.adapter = clazzAdapter
                    }
                }
            }
        })
*/
    }

    private fun initListener() {
        register_hospital_spinner.onItemSelectedListener = this
        register_position_spinner.onItemSelectedListener = this
        register_clazz_spinner.onItemSelectedListener = this

        register_pwd_confirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (register_pwd.text.toString() != s.toString()) {
                    register_pwd_confirm_layout.error = "两次密码输入不一致"
                    pce=false
                }else {
                    pce = true
                    register_pwd_confirm_layout.error=""
                }
            }

        })
        register_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s.length < 6){
                    register_pwd_layout.error = "密码小于6位"
                    pe=false
                }else {
                    pe= true
                    register_pwd_layout.error = ""

                }
            }

        })
        register_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s==null||s.length<2) {
                    register_name_layout.error = "账号名不能小于2位"
                    ne=false
                }else {
                    ne = true
                    register_name_layout.error = ""
                }
            }

        })

        register_bt.setOnClickListener {
            val params=HashMap<String,String>()
            params["name"]=register_name.text.toString()
            params["password"]=register_pwd.text.toString()
            params["hospitalid"]=userModel.getHospitalId(register_hospital_spinner.selectedItemPosition)
            params["positionid"]=userModel.getPositionId(register_position_spinner.selectedItemPosition)
            params["departmentid"]=userModel.getClazzId(register_clazz_spinner.selectedItemPosition)
            params["imgstr"]=""
            params["imgname"]="head_pic"

            if (ne&&pe&&pce) {
                progress_bar.visibility=View.VISIBLE

                HttpUtils.instance.doPost("$REQUEST_URL/register", params, object : HttpUtils.HttpCallBack {
                    override fun success(json: String) {
                        GlobalScope.launch(UI) {
                            val responseData = SingleTon.instance.gson.fromJson(json, ResponseData::class.java)
                            if (responseData.retcode == 1) {
                                toast("注册成功")
                                jump2Activity(this@RegisterActivity, LoadActivity::class.java)
                            } else {
                                toast("注册失败！${responseData.retmsg}")
                            }
                            progress_bar.visibility=View.GONE

                        }
                    }

                })
            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
