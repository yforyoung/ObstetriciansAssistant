package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.model.UserModelImpl
import com.yyl.obstetriciansassistant.utils.HttpUtils
import com.yyl.obstetriciansassistant.utils.SpfUtils
import kotlinx.android.synthetic.main.activity_load.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadActivity : AppCompatActivity(), View.OnClickListener {
    private val userModel=UserModelImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        if (SpfUtils.instance.getBoolean(STRING_IS_LOGIN,false)!!){
            jump2Activity(this,MainActivity::class.java)
        }
        initView()
    }

    private fun initView() {
        load_bt.setOnClickListener(this)
        load_to_register_bt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.load_to_register_bt->{
                jump2Activity(this,RegisterActivity::class.java)
            }
            R.id.load_bt->{
                login()
            }
        }
    }

    private fun login() {
        val name = load_name.text
        val pwd = load_pwd.text

        val params = HashMap<String, String>()
        params["name"] = name.toString()
        params["password"] = pwd.toString()

        progress_bar.visibility = View.VISIBLE

        HttpUtils.instance.doPost("$REQUEST_URL/login", params, object : HttpUtils.HttpCallBack {
            override fun success(json: String) {
                GlobalScope.launch(UI) {
                    loadFromJson(json)
                }
            }

        })
    }


    private fun loadFromJson(json:String) {
        if (userModel.isLoadSuccess(json)){
            jump2Activity(this@LoadActivity, MainActivity::class.java)
        }else{
            toast(userModel.getErrorMessage())
        }
        progress_bar.visibility = View.GONE
    }
}
