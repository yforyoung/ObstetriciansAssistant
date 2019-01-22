package com.yyl.obstetriciansassistant.beans

import java.util.*

class Retroaction {
    lateinit var id:String
    lateinit var user: User
    lateinit var retroactionConetent:String
    lateinit var retroactionTime:Date
    var isRead=false
}