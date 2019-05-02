package com.yyl.obstetriciansassistant.model

import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.REQUEST_URL
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.beans.*
import com.yyl.obstetriciansassistant.utils.HttpUtils

class SearchModelImpl : SearchModel {
    override fun getHotSearch(): List<SearchResult> {
        val searchResult = SearchResult("search title0", 0)
        val s2 = SearchResult("search title1", 1)
        val s3 = SearchResult("search title2", 2)
        val s4 = SearchResult("search title3", 3)
        val s5 = SearchResult("search title4", 4)

        return arrayListOf(searchResult, s2, s3, s4, s5)
    }

    override fun getSearchResult(type: Int): List<SearchResult> {
        val searchResult = SearchResult("search title0", 0)
        val s2 = SearchResult("search title1", 1)
        val s3 = SearchResult("search title2", 2)
        val s4 = SearchResult("search title3", 3)
        val s5 = SearchResult("search title4", 4)
        val s6 = SearchResult("search title2", 2)
        val s7 = SearchResult("search title3", 3)
        return when (type) {
            0 -> arrayListOf(searchResult)
            1 -> arrayListOf(s2)
            2 -> arrayListOf(s3, s6)
            3 -> arrayListOf(s4, s7)
            4 -> arrayListOf(s5)
            else -> arrayListOf()
        }
    }

    suspend fun getMedicineSearch(name: String, riskCode: String?):ResponseData<List<Medicine>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (riskCode != null)
            param["riskcode"] = riskCode
        val json=HttpUtils.instance.doPostAsync("$REQUEST_URL/querymedicine",param)
        return SingleTon.instance.gson.fromJson(json,object :TypeToken<ResponseData<List<Medicine>>>(){}.type)
    }

    suspend fun getEssaySearch(name: String, id: String?):ResponseData<List<Essay>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (id != null)
            param["id"] = id
        val json=HttpUtils.instance.doPostAsync("$REQUEST_URL/queryarticle",param)
        return SingleTon.instance.gson.fromJson(json,object :TypeToken<ResponseData<List<Essay>>>(){}.type)
    }

    suspend fun getVideoSearch(name: String, id: String?):ResponseData<List<Video>> {
        val param = HashMap<String, String>()
        param["name"] = name
        if (id != null)
            param["id"] = id
        val json=HttpUtils.instance.doPostAsync("$REQUEST_URL/queryvideo",param)
        return SingleTon.instance.gson.fromJson(json,object :TypeToken<ResponseData<List<Video>>>(){}.type)
    }

}