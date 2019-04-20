package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Answer
import com.yyl.obstetriciansassistant.beans.Question

interface QAModel {
    suspend fun getQAList():List<Question>
    suspend fun getAnswer(id:String):List<Answer>
    suspend fun setLike(id:String)
    suspend fun unLike(id: String)
    suspend fun addAnswer(id: String,content:String): Boolean
    suspend fun addQuestion(title:String,content: String,id: String):Boolean
    fun getTypeId(position:Int):String
    suspend fun getTypeName(): ArrayList<String>

}