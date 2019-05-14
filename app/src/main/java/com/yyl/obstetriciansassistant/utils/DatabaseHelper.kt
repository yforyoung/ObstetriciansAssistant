package com.yyl.obstetriciansassistant.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yyl.obstetriciansassistant.DB_NAME
import com.yyl.obstetriciansassistant.TB_NOTIFY_HISTORY
import com.yyl.obstetriciansassistant.TB_SEARCH_HISTORY
import com.yyl.obstetriciansassistant.VERSION

class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DB_NAME,null,VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table $TB_SEARCH_HISTORY(_id integer primary key autoincrement,title text,type text)")
        db.execSQL("create table $TB_NOTIFY_HISTORY(_id integer primary key autoincrement,name text,content text,question_id text,read text,type text,user text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}