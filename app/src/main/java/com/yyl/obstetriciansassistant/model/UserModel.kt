package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.*

interface UserModel{
    fun initUser()
    fun setRegisterBefore(json:String)
    fun isLoadSuccess(json: String):Boolean
    fun getErrorMessage():String
    fun getHospitals():ArrayList<String>
    fun getPositions():ArrayList<String>
    fun getClazz():ArrayList<String>
    fun getHospitalId(i:Int):String
    fun getPositionId(i:Int):String
    fun getClazzId(i:Int):String
}