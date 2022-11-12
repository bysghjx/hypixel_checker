package com.example.myapplication.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public final class MojangUtils {
    public static String getUUIDByName(String name) throws IOException {
        HttpResult result = HttpUtils.get("https://api.mojang.com/users/profiles/minecraft/" + name);
        if (result.responseCode == 200) {
            result.read();
            JSONObject json = JSON.parseObject(result.getContent());
            return json.getString("id");
        }

        result.close();
        return null;
    }

    private MojangUtils() {
    }
}
