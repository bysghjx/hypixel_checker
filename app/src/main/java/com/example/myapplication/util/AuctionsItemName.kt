package com.example.myapplication.util

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.Bean.Bean
import com.example.myapplication.DAO.SqlUtils
import com.example.myapplication.interfaces.UCanUUPCallBack

class AuctionsItemName {

    companion object {
        val data: List<Bean> = ArrayList()
        var s:String? = null

        fun ahSql(db: SQLiteDatabase, inputName: String,callback : UCanUUPCallBack): Int {
            val sql = "select `name`,`displayname` from ah where `displayname` like '${inputName}'"
            val sqlUtils = SqlUtils(db)
            val cursor: Cursor = sqlUtils.rawQuery(sql, null)

            var i = 0

            return if (cursor.count != 1) {
                while (cursor.moveToNext()) {
                    i++
                    val displayname = Bean()
                    displayname.name = i.toString() + "." + cursor.getString(1)
                    data[i].name = displayname.toString()
                }
                callback.SQL(data)
                1
            } else {
                cursor.moveToNext()
                s = cursor.getString(1)
                2
            }
        }

    }


}