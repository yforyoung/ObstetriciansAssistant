package com.yyl.obstetriciansassistant

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun toast(text: String) = Toast.makeText(App.context, text, Toast.LENGTH_SHORT).show()
fun Context.jump2Activity(c: Context, cls: Class<*>) = startActivity(Intent(c, cls))
