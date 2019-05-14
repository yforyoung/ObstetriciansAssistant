package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.STRING_IS_LOGIN
import com.yyl.obstetriciansassistant.STRING_USER
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.*
import com.yyl.obstetriciansassistant.utils.HttpUtils
import com.yyl.obstetriciansassistant.utils.SpfUtils

class UserModel {
    private var registerBefore = RegisterBefore()
    private var user: User? = null
    private lateinit var msg: String


    fun logout() {
        SpfUtils.instance.putString(STRING_USER, "")
        SpfUtils.instance.putBoolean(STRING_IS_LOGIN, false)
    }

    fun getHospitalId(i: Int): String {
        return registerBefore.hospitalList[i].id
    }

    fun getPositionId(i: Int): String {
        return registerBefore.positionList[i].id
    }

    fun getClazzId(i: Int): String {
        return registerBefore.departmentList[i].id
    }


    fun getHospitals(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (h in registerBefore.hospitalList) {
            list.add(h.name)
        }
        return list
    }

    fun getPositions(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (p in registerBefore.positionList) {
            list.add(p.title)
        }
        return list
    }

    fun getClazz(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (h in registerBefore.departmentList) {
            list.add(h.department)
        }
        return list
    }


    fun getErrorMessage(): String {
        return msg
    }

    fun isLoadSuccess(json: String): Boolean {
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

    fun initUser() {
        if (user == null) {
            user = SingleTon.instance.gson.fromJson(SpfUtils.instance.getString(STRING_USER, ""), User::class.java)
        }
        SingleTon.instance.user = user
    }

    suspend fun setRegisterBefore(): Boolean {
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/registerbefore", null)
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<RegisterBefore>>>(
            json,
            object : TypeToken<ResponseData<List<RegisterBefore>>>() {}.type
        )
        return if (responseData.retcode == 1) {
            registerBefore = responseData.data!![0]
            true
        } else
            false
    }

}