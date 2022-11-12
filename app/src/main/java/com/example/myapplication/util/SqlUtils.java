package com.example.myapplication.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class SqlUtils {
    SQLiteDatabase db;
    private Cursor cursor;

    public SqlUtils(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(String table, ContentValues value){
        db.insert(table,null,value);
    }
    public void del(){

    }
    public void upd(String table, ContentValues value, String where, String[] search){
        db.update(table,value,where,search);
    }

    public Cursor query(String table,String[] key,String where,String[] search,String groupBy,String having,String orderBy){
        cursor = db.query(table,key,where,search,groupBy,having,orderBy);
    return cursor;
    }
    public Cursor rawQuery(String sql,String[] search){
        cursor = db.rawQuery(sql,search);
        return cursor;
    }

    public void closeCursor(){
        cursor.close();
    }
}
