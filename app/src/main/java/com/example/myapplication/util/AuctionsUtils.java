package com.example.myapplication.util;

import static com.example.myapplication.MainActivity7.db;
import static com.example.myapplication.MainActivity7.ran;

import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.MainActivity7;
import com.example.myapplication.hypixel.AuctionsInfo;
import com.example.myapplication.hypixel.HypixelPlayerInfo;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

public class AuctionsUtils {

    private static String apiKey;
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

    @Deprecated
    public static int getAuctionsFromPlayer(String uuid, HypixelPlayerInfo info) throws IOException {
        String url = String.format("https://api.hypixel.net/player?key=%s&uuid=%s", apiKey, uuid);
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

    @Deprecated
    public static int getAuction(String item, AuctionsInfo info) throws IOException {
        getAllAuctions();
        if (getAllAuctions() == 200) {
            Message message = new Message();
            message.what = ran;
            message.obj = message.what;
            MainActivity7.mHandler.sendMessage(message);

            JSONArray dataA = new JSONArray(json.getJSONArray("data"));
            db.beginTransaction();
            for (int i = 0; i < dataA.size(); i++) {

                JSONObject jsonObj = dataA.getJSONObject(i);

                String id = jsonObj.getString("id");
                String name = jsonObj.getString("name");
                int lowestBin = jsonObj.getInteger("lowestBin");
                db.replace("ah", null, new ContentValuesBuilder()
                        .put("name", id)
                        .put("displayname", name)
                        .put("lowestBin", String.valueOf(lowestBin))
                        .put("time", new Timestamp(System.currentTimeMillis()).toString())
                        .build()
                );
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }


/*        if (itemData == null) {
            return -199;
        } else {
            info.name = itemData.getString("name");
            double var1 = itemData.getDouble("lowestBin");
            info.lowestBin = new BigDecimal(var1).setScale(2, RoundingMode.HALF_UP).doubleValue(); // 保留两位小数后并存储
            return 200;

        }*/
        return 200;
    }

    @Deprecated
    public static int getAuctionsForDB() throws IOException {
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
                db.replace("ah", null, new ContentValuesBuilder()
                        .put("name", id)
                        .put("displayname", name)
                        .put("lowestBin", String.valueOf(lowestBin))
                        .put("time", new Timestamp(System.currentTimeMillis()).toString())
                        .build()
                );
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.e(TAG, "getAuctions: 1111");
        }
        return 200;
    }

    public static void setApiKey(String apiKey) {
        AuctionsUtils.apiKey = apiKey;
    }

    private static void checkAPIKey() {
        if (apiKey == null) {
            throw new IllegalStateException("Api key not set.");
        }
    }

}
