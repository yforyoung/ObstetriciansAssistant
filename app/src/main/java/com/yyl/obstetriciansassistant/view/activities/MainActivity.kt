package com.yyl.obstetriciansassistant.view.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yyl.obstetriciansassistant.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun Context.toast(text:String)=Toast.makeText(this,text,Toast.LENGTH_SHORT).show()

}
