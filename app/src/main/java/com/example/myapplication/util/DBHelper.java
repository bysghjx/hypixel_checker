package com.example.myapplication.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    static String name = "query.db";
    static int ver = 1;
    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, name, null, ver);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table emp(_id  integer primary key autoincrement,name text,displayname text,buyPrice varchar(6),sellPrice varchar(6),buyVolume int,sellVolume int)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
