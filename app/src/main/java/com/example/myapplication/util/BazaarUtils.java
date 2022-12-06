package com.example.myapplication.util;

import static com.example.myapplication.MainActivity7.db;
import static com.example.myapplication.MainActivity7.ran;

import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.interfaces.InsertDBCallBack;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.MainActivity7;
import com.example.myapplication.hypixel.BazaarInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Iterator;

public class BazaarUtils {

    static JSONObject json;
    private static String TAG;


    public static int getBazaar() throws IOException {
        String url = "https://sky.shiiyu.moe/api/v2/bazaar";
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

    public static int getBazaars(String item, BazaarInfo info) throws IOException {
        getBazaar();
        String itemName = BazaarItemName.Companion.parseItemName(item);
        if (getBazaar() == 200) {
            Message message = new Message();
            message.what = ran;
            message.obj = message.what;
            MainActivity7.mHandler.sendMessage(message);

            JSONObject data = new JSONObject(json);
            Iterator<String> items = data.keySet().iterator();
            String key;

            db.beginTransaction();
            while (items.hasNext()) {
                key = items.next();
                JSONObject itemData = data.getJSONObject(key);
                String displayName = itemData.getString("name");
                String buyPrice = itemData.getString("buyPrice");
                String sellPrice = itemData.getString("sellPrice");
                String buyVolume = itemData.getString("buyVolume");
                String sellVolume = itemData.getString("sellVolume");
                db.replace("emp", null, new ContentValuesBuilder()
                        .put("name", key)
                        .put("displayName", displayName)
                        .put("buyPrice", buyPrice)
                        .put("sellPrice", sellPrice)
                        .put("buyVolume", buyVolume)
                        .put("sellVolume", sellVolume)
                        .put("time", new Timestamp(System.currentTimeMillis()).toString())
                        .build()
                );
            }
            db.setTransactionSuccessful();
            db.endTransaction();

            JSONObject itemData = data.getJSONObject(itemName);
            if (itemData == null) {
                return -199;
            } else {
                info.name = itemData.getString("name");
                double var1 = itemData.getDouble("buyPrice");
                info.buyPrice = new BigDecimal(var1).setScale(2, RoundingMode.HALF_UP).doubleValue(); // 保留两位小数后并存储
                double var2 = itemData.getDouble("sellPrice");
                info.sellPrice = new BigDecimal(var2).setScale(2, RoundingMode.HALF_UP).doubleValue(); // 保留两位小数后并存储
                info.buyVolume = itemData.getInteger("buyVolume");
                info.sellVolume = itemData.getInteger("sellVolume");
                return 200;
            }
        }
        return 200;
    }

    public static void getBazaarsForDB(InsertDBCallBack callBack) throws IOException,NullPointerException {
        getBazaar();
        Message message = new Message();

        JSONObject data = new JSONObject(json);
        Iterator<String> items = json.keySet().iterator();
        String key;

        db.beginTransaction();
        while (items.hasNext()) {
            key = items.next();
            JSONObject itemData = data.getJSONObject(key);
            String displayName = itemData.getString("name");
            String buyPrice = itemData.getString("buyPrice");
            String sellPrice = itemData.getString("sellPrice");
            String buyVolume = itemData.getString("buyVolume");
            String sellVolume = itemData.getString("sellVolume");
            db.replace("emp", null, new ContentValuesBuilder()
                    .put("name", key)
                    .put("displayName", displayName)
                    .put("buyPrice", buyPrice)
                    .put("sellPrice", sellPrice)
                    .put("buyVolume", buyVolume)
                    .put("sellVolume", sellVolume)
                    .put("time", new Timestamp(System.currentTimeMillis()).toString())
                    .build()
            );
        }
        db.setTransactionSuccessful();
        db.endTransaction();

        message.what = 0;
        message.obj = message.what;
        MainActivity2.mHandler.sendMessage(message);
        callBack.Success("Success");

    }

    @Deprecated
    public static JSONObject getBazaarJson(String item) throws IOException {
        String itemName = BazaarItemName.Companion.parseItemName(item);
        if (getBazaar() == 200) {
            return json.getJSONObject(itemName);
        }
        return null;
    }

    @Deprecated
    public static int getBazaarDeprecated() throws IOException {
        String url = "https://api.hypixel.net/skyblock/bazaar";
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



}
