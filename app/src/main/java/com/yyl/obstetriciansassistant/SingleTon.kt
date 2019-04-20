package com.yyl.obstetriciansassistant

import com.google.gson.Gson
import com.yyl.obstetriciansassistant.beans.User

class SingleTon private constructor() {
    var user: User? = null
    val gson: Gson = Gson()


    companion object {
        val instance: SingleTon = SingleTon()
    }


}