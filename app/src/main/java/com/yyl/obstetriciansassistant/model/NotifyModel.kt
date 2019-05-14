package com.yyl.obstetriciansassistant.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.yyl.obstetriciansassistant.App
import com.yyl.obstetriciansassistant.SingleTon
import com.yyl.obstetriciansassistant.TB_NOTIFY_HISTORY
import com.yyl.obstetriciansassistant.beans.Notify
import com.yyl.obstetriciansassistant.beans.User
import com.yyl.obstetriciansassistant.log
import com.yyl.obstetriciansassistant.utils.DatabaseHelper
import java.sql.SQLException

class NotifyModel {

    private val helper = DatabaseHelper(App.context)
    private lateinit var database: SQLiteDatabase
    fun getNotify(): List<Notify> {
        database = helper.readableDatabase
        val list = arrayListOf<Notify>()
        val cursor = database.rawQuery("select * from $TB_NOTIFY_HISTORY where user=? order by read asc,_id desc", arrayOf(SingleTon.instance.user!!.id))
        while (cursor.moveToNext()) {
            list.add(Notify(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getString(cursor.getColumnIndex("question_id")),
                cursor.getString(cursor.getColumnIndex("read")) == "1",
                cursor.getString(cursor.getColumnIndex("type"))
            ))
        }
        cursor.close()
        database.close()
        return list
    }

    fun addNotify(notify: Notify) {
        database = helper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("user",SingleTon.instance.user!!.id)
        contentValues.put("name", notify.name)
        contentValues.put("content", notify.content)
        contentValues.put("question_id", notify.id)
        contentValues.put("read", "0")
        contentValues.put("type",notify.type)
        database.insert(TB_NOTIFY_HISTORY, null, contentValues)
        database.close()
    }

    fun readNotify(id: Int) {
        database = helper.writableDatabase
        try {
            val contentValues=ContentValues()
            contentValues.put("read","1")
            database.update(TB_NOTIFY_HISTORY,contentValues,"_id=?", arrayOf("$id"))
            //database.execSQL("update $TB_NOTIFY_HISTORY set read='1' where _id=?", arrayOf(id))

        }catch (e:SQLException){
            log(e.toString())
        }
        database.close()
    }
}