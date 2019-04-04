package com.yyl.obstetriciansassistant.beans

import java.util.*

class Answer {
    lateinit var question: Question
    lateinit var replier:User
    var isBest:Boolean=false
    lateinit var answerDate: String

    constructor(answerDate: String) {
        this.answerDate = answerDate
    }
}