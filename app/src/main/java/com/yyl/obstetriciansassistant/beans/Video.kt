package com.yyl.obstetriciansassistant.beans

import java.util.*

class Video {
    lateinit var id:String
    lateinit var title:String
    lateinit var url:String
    var searchTime:Int=0
    lateinit var uploadDate:Date
    lateinit var uploader:User
}