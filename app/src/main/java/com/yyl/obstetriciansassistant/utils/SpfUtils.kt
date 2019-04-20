package com.yyl.obstetriciansassistant.utils

import android.content.Context
import com.yyl.obstetriciansassistant.App
import com.yyl.obstetriciansassistant.SPF_NAME

class SpfUtils private constructor() {
    private val editor=App.context.getSharedPreferences(SPF_NAME,Context.MODE_PRIVATE).edit()
    private val spf=App.context.getSharedPreferences(SPF_NAME,Context.MODE_PRIVATE)

    companion object {
        val instance:SpfUtils= SpfUtils()
    }

    fun putString(key:String,value:String){
        editor.putString(key,value)
        editor.commit()
    }

    fun getString(key:String,default:String):String?{
        return spf.getString(key,default)
    }
    fun putInt(key:String,value:Int){
        editor.putInt(key,value)
        editor.commit()
    }

    fun getInt(key:String,default:Int):Int?{
        return spf.getInt(key,default)
    }

    fun putBoolean(key:String,value:Boolean){
        editor.putBoolean(key,value)
        editor.commit()
    }

    fun getBoolean(key:String,default:Boolean):Boolean?{
        return spf.getBoolean(key,default)
    }
}