package com.yyl.obstetriciansassistant.beans

import java.util.*

class User {
    lateinit var account:String
    lateinit var hospital:Hospital
    lateinit var password:String
    lateinit var position: DoctorPosition
    lateinit var headPic: HeadPic
    lateinit var createDate:Date
    lateinit var updateDate: Date
    lateinit var realName:String
    lateinit var auditor:User
    lateinit var updater:User
    var manager:Int=-1
    lateinit var clazz:Clazz

}