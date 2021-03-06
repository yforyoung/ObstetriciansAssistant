package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.Answer
import com.yyl.obstetriciansassistant.beans.Question
import com.yyl.obstetriciansassistant.beans.QuestionType
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.utils.HttpUtils

class QAModel {
    private var type = arrayListOf<QuestionType>()

    suspend fun addQuestion(title: String, content: String, id: String): Boolean {
        val param = HashMap<String, String>()
        param["createid"] = SingleTon.instance.user!!.id
        param["typeid"] = id
        param["content"] = content
        param["title"] = title
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/addquestions", param)
        return SingleTon.instance.gson.fromJson(json, ResponseData::class.java).retcode == 1

    }

     fun getTypeId(position: Int): String {
        return type[position].id
    }

     suspend fun getTypeName(): ArrayList<String> {
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getquestionstype", null)
        val responseData = SingleTon.instance.gson.fromJson<ResponseData<List<QuestionType>>>(
            json,
            object : TypeToken<ResponseData<List<QuestionType>>>() {}.type
        )
        val nameList = arrayListOf<String>()
        if (responseData.retcode == 1) {
            type.clear()
            type.addAll(responseData.data!!)
            for (t in type) {
                nameList.add(t.type)
            }
        }

        return nameList
    }

     suspend fun addAnswer(id: String, content: String): Boolean {
        val param = HashMap<String, String>()
        param["questionsid"] = id
        param["content"] = content
        param["userid"] = SingleTon.instance.user!!.id
        param["name"]=SingleTon.instance.user!!.name
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/answerquestions", param)
        if (SingleTon.instance.gson.fromJson(json, ResponseData::class.java).retcode == 1)
            return true
        return false
    }

     suspend fun unLike(id: String) {
        val param = HashMap<String, String>()
        param["userquestionsid"] = id
        param["userid"] = SingleTon.instance.user!!.id
        HttpUtils.instance.doPostAsync("$REQUEST_URL/dellikes", param)
    }

     suspend fun setLike(id: String) {
        val param = HashMap<String, String>()
        param["userquestionsid"] = id
        param["userid"] = SingleTon.instance.user!!.id
        param["name"]=SingleTon.instance.user!!.name
        HttpUtils.instance.doPostAsync("$REQUEST_URL/likes", param)
    }

    suspend fun getAnswers(id: String): ResponseData<List<Answer>> {
        val param = HashMap<String, String>()
        param["userid"] = SingleTon.instance.user!!.id
        param["id"] = id

        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getcomment", param)
/*
         val res = SingleTon.instance.gson.fromJson<ResponseData<List<Answer>>>(
             json,
             object : TypeToken<ResponseData<List<Answer>>>() {}.type
         )*/
        return SingleTon.instance.gson.fromJson<ResponseData<List<Answer>>>(
            json,
            object : TypeToken<ResponseData<List<Answer>>>() {}.type
        )
    }


     suspend fun getQAList(): List<Question> {

        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getquestions", null)

        val res = SingleTon.instance.gson.fromJson<ResponseData<List<Question>>>(
            json,
            object : TypeToken<ResponseData<List<Question>>>() {}.type
        )
        return res.data!!
    }

    suspend fun getQuestion(id:String):ResponseData<List<Question>>{
        val param=HashMap<String,String>()
        param["id"]=id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/queryquestions", param)

        return SingleTon.instance.gson.fromJson(
            json,
            object : TypeToken<ResponseData<List<Question>>>() {}.type
        )
    }

    suspend fun getMyQaList(): ResponseData<List<Question>> {
        val param = HashMap<String, String>()
        param["createid"] = SingleTon.instance.user!!.id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/myquestions", param)
        return SingleTon.instance.gson.fromJson<ResponseData<List<Question>>>(
            json,
            object : TypeToken<ResponseData<List<Question>>>() {}.type
        )
    }

}