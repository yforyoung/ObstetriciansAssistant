package com.yyl.obstetriciansassistant.beans

import java.io.Serializable

class Answer : Serializable {
    lateinit var question: Question
    lateinit var replier:User
    var isBest:Boolean=false
    lateinit var content:String
    lateinit var answerDate: String
    lateinit var replyList:List<String>

    constructor(answerDate: String,content:String) {
        this.answerDate = answerDate
        this.content=content
        replyList= arrayListOf("reply1","reply2","replay3")
    }
}