package com.yyl.obstetriciansassistant.beans

import java.io.Serializable
import java.util.*

class Question : Serializable {
    lateinit var id:String
    lateinit var creator:User
    var type:Int = -1
    lateinit var content:String
    lateinit var createDate: Date
    lateinit var title:String
    lateinit var answers:List<Answer>
    var viewCount:Int=0


    constructor(content: String, title: String, answers: List<Answer>, viewCount: Int) {
        this.content = content
        this.title = title
        this.answers = answers
        this.viewCount = viewCount
    }
}