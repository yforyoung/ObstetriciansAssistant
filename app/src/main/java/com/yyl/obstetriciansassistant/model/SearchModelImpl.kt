package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.*
import com.yyl.obstetriciansassistant.utils.HttpUtils

class SearchModelImpl : SearchModel {

    suspend fun getMedicineSearch(name: String, riskCode: String?): ResponseData<List<Medicine>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (riskCode != null)
            param["riskcode"] = riskCode
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/querymedicine", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Medicine>>>() {}.type)
    }

    suspend fun getEssaySearch(name: String, id: String?): ResponseData<List<Essay>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (id != null)
            param["id"] = id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/queryarticle", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Essay>>>() {}.type)
    }

    suspend fun getVideoSearch(name: String, id: String?): ResponseData<List<Video>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (id != null)
            param["id"] = id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/queryvideo", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Video>>>() {}.type)
    }

    suspend fun getQuestionSearch(name: String, id: String?): ResponseData<List<Question>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (id != null)
            param["id"] = id
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/queryquestion", param)
        return SingleTon.instance.gson.fromJson(json, object : TypeToken<ResponseData<List<Video>>>() {}.type)
    }

    suspend fun getSearchResult(name: String,id: String?,type:String):ArrayList<SearchResult>{
        val list= arrayListOf<SearchResult>()
        when(type){
            ESSAY->{
                val re=getEssaySearch(name,id)
                if (re.retcode==1){
                    for (e in re.data!!){
                        list.add(SearchResult(e.title,"article",e.id))
                    }
                }
            }
            MEDICINE->{
                val re=getMedicineSearch(name,id)
                if (re.retcode==1){
                    for (e in re.data!!){
                        list.add(SearchResult(e.name,"medicine",e.riskCode))
                    }
                }
            }
            QA->{
                val re=getQuestionSearch(name,id)
                if (re.retcode==1){
                    for (e in re.data!!){
                        list.add(SearchResult(e.title,"question",e.id))
                    }
                }
            }
            TV_VIDEO->{
                val re=getVideoSearch(name,id)
                if (re.retcode==1){
                    for (e in re.data!!){
                        list.add(SearchResult(e.title,"video",e.id))
                    }
                }
            }
        }
        return list
    }

    suspend fun getHotSearch(): ArrayList<SearchResult> {
        val list = arrayListOf<SearchResult>()
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/popular", null)
        val re = SingleTon.instance.gson.fromJson<ResponseData<List<HotSearch>>>(json,
            object : TypeToken<ResponseData<List<HotSearch>>>() {}.type)
        if (re.retcode == 1) {
            for (s in re.data!!) {
                if (s.type == "article" || s.type == "video") {
                    list.add(SearchResult(s.title, s.type, s.id))
                } else {
                    list.add(SearchResult(s.name, s.type, s.riskCode))
                }
            }
        }
        return list
    }

}