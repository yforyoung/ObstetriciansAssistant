package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.STRING_IS_LOGIN
import com.yyl.obstetriciansassistant.STRING_USER
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.*
import com.yyl.obstetriciansassistant.utils.HttpUtils
import com.yyl.obstetriciansassistant.utils.SpfUtils

class UserModelImpl : UserModel {
    override fun getUser(): User {
        return user!!
    }

    override fun logout() {
        SpfUtils.instance.putString(STRING_USER,"")
        SpfUtils.instance.putBoolean(STRING_IS_LOGIN,false)
    }


    private var registerBefore=RegisterBefore()
    private var user: User?=null
    private lateinit var msg: String


    override fun getHospitalId(i: Int): String {
        return registerBefore.hostpitaldata[i].id
    }

    override fun getPositionId(i: Int): String {
        return registerBefore.positiondata[i].id
    }

    override fun getClazzId(i: Int): String {
        return registerBefore.departmentdata[i].id
    }


    override fun getHospitals(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (h in registerBefore.hostpitaldata) {
            list.add(h.name)
        }
        return list
    }

    override fun getPositions(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (p in registerBefore.positiondata) {
            list.add(p.title)
        }
        return list
    }

    override fun getClazz(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (h in registerBefore.departmentdata) {
            list.add(h.department)
        }
        return list
    }


    override fun getErrorMessage(): String {
        return msg
    }

    override fun isLoadSuccess(json: String): Boolean {
        val responseData =
            SingleTon.instance.gson.fromJson<ResponseData<User>>(json, object : TypeToken<ResponseData<User>>() {}.type)
        return if (responseData.retcode == 1) {
            val user = responseData.data
            SingleTon.instance.user = user
            SpfUtils.instance.putBoolean(STRING_IS_LOGIN, true)
            SpfUtils.instance.putString(STRING_USER, SingleTon.instance.gson.toJson(user!!))
            true
        } else {
            msg = responseData.retmsg
            false
        }
    }

    override fun initUser() {
        if (user==null){
            user=SingleTon.instance.gson.fromJson(SpfUtils.instance.getString(STRING_USER,""),User::class.java)
        }
        SingleTon.instance.user=user
    }

    suspend fun setRegisterBefore():Boolean{
        val json=HttpUtils.instance.doPostAsync("$REQUEST_URL/registerbefore", null)
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<RegisterBefore>>>(json,
            object : TypeToken<ResponseData<List<RegisterBefore>>>() {}.type
        )
        return if(responseData.retcode==1){
            registerBefore=responseData.data!![0]
            true
        }else
            false
    }


    override fun setRegisterBefore(json: String) {

        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<RegisterBefore>>>(json,
            object : TypeToken<ResponseData<List<RegisterBefore>>>() {}.type
        )
        registerBefore = responseData.data!![0]
    }
}