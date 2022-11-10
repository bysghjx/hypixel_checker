package com.example.myapplication.util;

import static com.example.myapplication.MainActivity7.ran;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.MainActivity7;
import com.example.myapplication.hypixel.BazaarInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class BazaarUtils {

    static JSONObject json;
    private static String TAG;


    public static int getBazaar(){
        String url = "https://sky.shiiyu.moe/api/v2/bazaar";
        HttpResult result = HttpUtils.get(url);
        if (!result.isSuccess()){
            return -1;
        }
        if (result.responseCode == 200) {
            result.read();

            json = JSON.parseObject(result.getContent());
            if(json == null){
                return -199;
            }
        }
        result.close();
        return 200;
    }

    @SuppressLint("Range")
    public static int getBazaars(String item, BazaarInfo info) {
        getBazaar();
        String itemName = BazaarItemName.Companion.parseItemName(item);
        if (getBazaar() == 200) {
            JSONObject jsonObject = JSON.parseObject(String.valueOf(json)).getJSONObject(itemName);

            Message message = new Message();
            message.what = ran;
            message.obj = message.what;
            MainActivity7.mHandler.sendMessage(message);

            JSONObject jsonObject1 = new JSONObject(json);
            Iterator<String> iterator = jsonObject1.keySet().iterator();
            ArrayList<String> keys = new ArrayList<String>();
            SqlUtils sqlUtils = new SqlUtils(MainActivity7.db);
            String key;

            while (iterator.hasNext()){
                key = (String) iterator.next();
                Cursor cursor = sqlUtils.query("emp", new String[]{"name"}, "name = ?", new String[]{key}, null, null, null);
                    if(!cursor.moveToNext()){
                        ContentValues values = new ContentValues();
                        values.put("name",key);
                        sqlUtils.add("emp",values);
                    }
                keys.add(key);
            }

            sqlUtils.closeCursor();

            Cursor cursor = sqlUtils.db.rawQuery("select name from emp",null);
            ContentValues values = new ContentValues();

            while (cursor.moveToNext()){
                String dbJs = cursor.getString(cursor.getColumnIndex("name"));
                JSONObject jsonObject2 = JSON.parseObject(String.valueOf(json)).getJSONObject(dbJs);

                if (dbJs != null) {
                    values.put("displayname",jsonObject2.getString("name"));
                    values.put("buyPrice",jsonObject2.getDouble("buyPrice"));
                    values.put("sellPrice",jsonObject2.getDouble("sellPrice"));
                    values.put("buyVolume",jsonObject2.getInteger("buyVolume"));
                    values.put("sellVolume",jsonObject2.getInteger("sellVolume"));
                    sqlUtils.upd("emp",values,"name = ?",new String[]{dbJs});
                }

            }
            cursor.close();

            if(jsonObject == null){
                return -199;
            }else{
                info.name = jsonObject.getString("name");
                double var1 = jsonObject.getDouble("buyPrice");
                info.buyPrice = Double.parseDouble(String.format("%.2f", var1));
                double var2 = jsonObject.getDouble("sellPrice");
                info.sellPrice = Double.parseDouble(String.format("%.2f", var2));
                info.buyVolume = jsonObject.getInteger("buyVolume");
                info.sellVolume = jsonObject.getInteger("sellVolume");
                return 200;
            }
        }
        return 200;
    }


    @Deprecated
    public static JSONObject getBazaarJson(String item){
        String itemName = BazaarItemName.Companion.parseItemName(item);
        if(getBazaar() == 200){

            JSONObject jsonObject = JSON.parseObject(String.valueOf(json)).getJSONObject(itemName);
            return jsonObject;
        }
        return null;
    }


}
