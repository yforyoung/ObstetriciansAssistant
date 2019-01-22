package com.yyl.obstetriciansassistant.beans

import java.util.*

class Advertisement {
    lateinit var id:String
    lateinit var url:String
    lateinit var contact:String
    lateinit var startDate:Date
    lateinit var endDate:Date
    var type:Int=-1


    object TYPE{
        const val SPLASH_AD=1
        const val HOME_AD=2
    }

}