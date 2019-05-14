package com.yyl.obstetriciansassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.Main
import java.io.Serializable


const val ESSAY: String = "essay"
const val MEDICINE: String = "medicine"
const val TV_VIDEO: String = "tv"
const val CASE: String = "case"
const val QA: String = "qa"
const val CHANGE: Boolean = true
const val TYPE: String = "type"
const val VALUE: String = "value"
const val IS_CHANGE: String = "isChange"
const val VIEW_CASE=0
const val EDIT_CASE=1
const val CREATE_CASE=2

val UI=Dispatchers.Main
/*
const val REQUEST_URL:String="http://sl1996.viphk.ngrok.org/Dc"
*/
const val REQUEST_URL:String="http://sl.vipgz1.idcfengye.com/Dc"

/*spf的字段*/
const val SPF_NAME:String="obstetrician"
const val STRING_IS_LOGIN:String="is_login"
const val STRING_USER:String="user"
const val BABY_WEIGHT:String="胎儿体重计算器"
const val EXCEPT_DAY:String="预产期计算器"
const val HEALTHY_WEIGHT:String="健康体重计算器"

const val DB_NAME:String="obstetriciansAssistant"
const val VERSION:Int=1
const val TB_SEARCH_HISTORY:String="tb_search_history"
const val TB_NOTIFY_HISTORY:String="tb_notify_history"



fun toast(text: String) = Toast.makeText(App.context, text, Toast.LENGTH_SHORT).show()
fun Context.jump2Activity(c: Context, cls: Class<*>) = startActivity(Intent(c, cls))
fun Context.jump2Activity(c: Context, cls: Class<*>, f: String) {
    val intent = Intent(c, cls)
    intent.putExtra(TYPE, f)
    startActivity(intent)
}

fun Context.jump2Activity(c: Context, cls: Class<*>,value: Serializable) {
    val intent = Intent(c, cls)
    val bundle = Bundle()
    bundle.putSerializable(VALUE, value)
    intent.putExtra(VALUE, bundle)
    startActivity(intent)
}

fun Context.jump2Activity(c: Context, cls: Class<*>, f: String, b: Int,value: Serializable?) {
    val intent = Intent(c, cls)
    intent.putExtra(TYPE, f)
    intent.putExtra(IS_CHANGE, b)
    val bundle = Bundle()
    bundle.putSerializable(VALUE, value)
    intent.putExtra(VALUE, bundle)
    startActivity(intent)
}

fun Context.jump2Activity(c: Context, cls: Class<*>, f: String, value: Serializable) {
    val intent = Intent(c, cls)
    intent.putExtra(TYPE, f)
    val bundle = Bundle()
    bundle.putSerializable(VALUE, value)
    intent.putExtra(VALUE, bundle)
    startActivity(intent)
}


fun Any.log(s:String){
    Log.e("young",s)
}
