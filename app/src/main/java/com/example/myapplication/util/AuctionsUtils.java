package com.example.myapplication.util;

import static com.example.myapplication.MainActivity7.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.hypixel.BazaarInfo;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

public class AuctionsUtils {

    static JSONObject json;
    private static String TAG;


    public static int getAllAuctions() throws IOException {
        String url = "https://maro-api.flowerinsnow.online:10443/api/auctions/all";
        HttpResult result = HttpUtils.get(url);
        if (!result.isSuccess()) {
            return -1;
        }
        if (result.responseCode == 200) {
            result.read();

            json = JSON.parseObject(result.getContent());
            if (json == null) {
                return -199;
            }
        }
        result.close();
        return 200;
    }
    public static int getAuctions(String item, BazaarInfo info) throws IOException{
        getAllAuctions();
        if (getAllAuctions() == 200) {
            JSONObject data = new JSONObject(json);
            JSONArray dataA = new JSONArray(json.getJSONArray("data"));
            Iterator<Object> i1 = dataA.iterator();
            db.beginTransaction();
            for (int i = 0; i < dataA.size(); i++) {

                JSONObject jsonObj = dataA.getJSONObject(i);

                String id = jsonObj.getString("id");
                String name = jsonObj.getString("name");
                int lowestBin = jsonObj.getInteger("lowestBin");
                db.replace("ah",null,new ContentValuesBuilder()
                        .put("name",id)
                        .put("displayname",name)
                        .put("lowestBin", String.valueOf(lowestBin))
                        .put("time", new Timestamp(System.currentTimeMillis()).toString())
                        .build()
                );
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        return 200;
    }
}
