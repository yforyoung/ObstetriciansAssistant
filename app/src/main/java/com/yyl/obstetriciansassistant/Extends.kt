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
val UI=Dispatchers.Main
const val REQUEST_URL:String="http://sl1996.viphk.ngrok.org/Dc"

/*spf的字段*/
const val SPF_NAME:String="obstetrician"
const val STRING_IS_LOGIN:String="is_login"
const val STRING_USER:String="user"


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

fun Context.jump2Activity(c: Context, cls: Class<*>, f: String, b: Boolean,value: Serializable) {
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
    Log.e(App.TAG,s)
}
