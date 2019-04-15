package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Answer
import com.yyl.obstetriciansassistant.beans.Question

interface QAModel {
    fun getQAList():List<Question>
    fun getAnswer():List<Answer>

}