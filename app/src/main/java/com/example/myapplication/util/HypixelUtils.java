package com.example.myapplication.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.hypixel.HypixelPlayerInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class HypixelUtils {
    private static String apiKey;

    public static int getPlayer(String uuid, HypixelPlayerInfo info) {
        checkAPIKey();
        String url = String.format("https://api.hypixel.net/player?key=%s&uuid=%s", apiKey, uuid);
        HttpResult result = HttpUtils.get(url);
        if (!result.isSuccess()) {
            return -1;
        }

        if (result.responseCode == 200) {
            result.read();
            JSONObject json = JSON.parseObject(result.getContent());
            JSONObject player = json.getJSONObject("player");
            info.uuid = player.getString("uuid");
            info.name = player.getString("playername");
            info.firstLogin = new Date(player.getLongValue("firstLogin"));
            info.lastLogin = new Date(player.getLongValue("lastLogin"));
            info.language = player.getString("userLanguage");
            List<String> list = new ArrayList<>();
            JSONArray knownAliases = player.getJSONArray("knownAliases");
            for (int i = 0; i < knownAliases.size(); i++) {
                list.add(knownAliases.getString(i));
            }
            info.knownAliases = list;
            info.data = player.getJSONObject("stats");
            return 200;
        }

        result.close();
        return result.responseCode;
    }

    public static void setApiKey(String apiKey) {
        HypixelUtils.apiKey = apiKey;
    }

    private static void checkAPIKey() {
        if (apiKey == null) {
            throw new IllegalStateException("Api key not set.");
        }
    }

    private HypixelUtils() {
    }
}
