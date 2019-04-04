package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Question

interface QAModel {
    fun getQAList():List<Question>

}