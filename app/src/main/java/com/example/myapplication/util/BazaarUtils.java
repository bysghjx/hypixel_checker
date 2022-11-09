package com.example.myapplication.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.hypixel.BazaarInfo;

public class BazaarUtils {

    static JSONObject json;

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

    public static int getBazaars(String item, BazaarInfo info) {
        getBazaar();
        String itemName = BazaarItemName.Companion.parseItemName(item);
        if (getBazaar() == 200) {
            JSONObject jsonObject = JSON.parseObject(String.valueOf(json)).getJSONObject(itemName);
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
