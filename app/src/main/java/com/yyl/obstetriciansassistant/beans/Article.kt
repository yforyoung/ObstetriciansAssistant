package com.yyl.obstetriciansassistant.beans

import java.util.*

class Article {
    lateinit var id:String
    lateinit var title:String
    lateinit var url:String
    var searchTime:Int = 0
    lateinit var uploader:User
    lateinit var uploadDate:Date
    lateinit var source:String

}