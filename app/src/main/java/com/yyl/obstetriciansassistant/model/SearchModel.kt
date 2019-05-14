package com.yyl.obstetriciansassistant.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.*
import com.yyl.obstetriciansassistant.utils.DatabaseHelper
import com.yyl.obstetriciansassistant.utils.HttpUtils
import java.sql.SQLException
import java.util.*

class SearchModel  {
    private val helper = DatabaseHelper(App.context)
    private lateinit var database: SQLiteDatabase

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

    suspend fun getSearchResult(name: String, id: String?, type: String): ArrayList<SearchResult> {
        addSearchHistory(name, type)
        val list = arrayListOf<SearchResult>()
        when (type) {
            ESSAY -> {
                val re = getEssaySearch(name, id)
                if (re.retcode == 1) {
                    for (e in re.data!!) {
                        list.add(SearchResult(e.title, "article", e.id))
                    }
                }
            }
            MEDICINE -> {
                val re = getMedicineSearch(name, id)
                if (re.retcode == 1) {
                    for (e in re.data!!) {
                        list.add(SearchResult(e.name, "medicine", e.riskCode))
                    }
                }

            }
            TV_VIDEO -> {
                val re = getVideoSearch(name, id)
                if (re.retcode == 1) {
                    for (e in re.data!!) {
                        list.add(SearchResult(e.title, "video", e.id))
                    }
                }
            }
        }
        return list
    }

    suspend fun getHotSearch(): ArrayList<SearchResult> {
        val list = arrayListOf<SearchResult>()
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/popular", null)
        val re = SingleTon.instance.gson.fromJson<ResponseData<List<HotSearch>>>(
            json,
            object : TypeToken<ResponseData<List<HotSearch>>>() {}.type
        )
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

    private fun addSearchHistory(s: String, type: String) {
        database = helper.writableDatabase
        val cursor = database.rawQuery("select * from $TB_SEARCH_HISTORY where title=? and type=?", arrayOf(s,type))
        if (cursor.moveToNext()) {
            database.execSQL("delete from $TB_SEARCH_HISTORY where _id=${cursor.getInt(0)}")
        }
        val contentValues = ContentValues()
        contentValues.put("title", s)
        contentValues.put("type", type)
        database.insert(TB_SEARCH_HISTORY, null, contentValues)
        cursor.close()
        database.close()
    }

    fun getSearchHistory(): List<SearchHistory> {
        val list = arrayListOf<SearchHistory>()
        database = helper.readableDatabase
        val s = "select * from $TB_SEARCH_HISTORY order by _id desc"
        val cursor = database.rawQuery(s, null)
        while (cursor.moveToNext()) {
            list.add(
                SearchHistory(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("type"))
                )
            )
        }
        cursor.close()
        database.close()
        return list
    }

    fun deleteSearchHistory(id: Int) {
        database = helper.writableDatabase
        try {
            database.execSQL("delete from $TB_SEARCH_HISTORY where _id=$id")
        } catch (e: SQLException) {
            log(e.toString())
        } finally {
            database.close()
        }

    }

}