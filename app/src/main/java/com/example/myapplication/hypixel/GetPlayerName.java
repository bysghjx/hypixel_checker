package com.example.myapplication.hypixel;


import android.app.Activity;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.HypixelHttps;

public class GetPlayerName {

    Activity activity;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String uuid;
    String api_key;
    String p_data;

    public GetPlayerName(Activity activity) {
        this.activity = activity;
    }

    public String getUuid() {
        sp = activity.getSharedPreferences("api_data",0);
        uuid = sp.getString("uuid",null);
        return uuid;
    }

    public String getApi_key() {
        sp = activity.getSharedPreferences("api_data",0);
        api_key = sp.getString("api_key",null);
        return api_key;
    }

    public String getData() {

        sp = activity.getSharedPreferences("api_data",0);
        editor = sp.edit();
        getApi_key();
        getUuid();
        HypixelHttps gpn = new HypixelHttps(uuid,api_key);
        String p_data_ca = gpn.getP_data();
        String Pl_data_ca = JSON.parseObject(String.valueOf(p_data_ca)).getString("playername");
        editor.putString("P_data_ca",Pl_data_ca);
        editor.commit();

        return Pl_data_ca;
    }


}
