package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.SearchResult

class SearchModelImpl : SearchModel {
    override fun getHotSearch(): List<SearchResult> {
        val searchResult = SearchResult("search title0", 0)
        val s2 = SearchResult("search title1", 1)
        val s3 = SearchResult("search title2", 2)
        val s4 = SearchResult("search title3", 3)
        val s5 = SearchResult("search title4", 4)
        val s6 = SearchResult("search title2", 2)
        val s7 = SearchResult("search title3", 3)

        return arrayListOf(searchResult, s2, s3, s4, s5, s6, s7)
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
}