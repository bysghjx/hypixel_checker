package com.example.myapplication.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

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
        db.execSQL(IOUtils.read(this.context.getResources().openRawResource(R.raw.emp_table)));
        db.execSQL(IOUtils.read(this.context.getResources().openRawResource(R.raw.emp_table_index)));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
