package com.example.myapplication.util;

import android.content.ContentValues;

public class ContentValuesBuilder {
    private ContentValues object;
    public ContentValuesBuilder() {
        this.object = new ContentValues();
    }

    public ContentValuesBuilder put(String key, String value) {
        this.object.put(key, value);
        return this;
    }

    public ContentValues build() {
        return object;
    }
}
