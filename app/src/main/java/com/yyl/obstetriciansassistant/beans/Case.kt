package com.yyl.obstetriciansassistant.beans

import java.util.*

class Case {
    lateinit var hospital:Hospital
    lateinit var createDate: Date
    lateinit var updateDate: Date
    lateinit var creater:User
    lateinit var content:String
}