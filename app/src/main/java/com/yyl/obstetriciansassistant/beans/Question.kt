package com.yyl.obstetriciansassistant.beans

import java.util.*

class Question {
    lateinit var id:String
    lateinit var creator:User
    var type:Int = -1
    lateinit var content:String
    lateinit var createDate: Date
}