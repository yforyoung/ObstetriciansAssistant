package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.SearchResult

interface SearchModel{
    fun getHotSearch():List<SearchResult>
    fun getSearchResult(type:Int):List<SearchResult>
}