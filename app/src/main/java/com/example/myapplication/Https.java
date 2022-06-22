package com.example.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

import javax.net.ssl.HttpsURLConnection;

public class Https extends Thread {

    Activity activity;
    String name;
    InputStreamReader isr;
    BufferedReader br;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public Https(Activity activity, String name) {
        this.activity = activity;
        this.name = name;
    }

    public String GetName() {

        String input_name = name;
        name = String.format("https://api.mojang.com/users/profiles/minecraft/%s", input_name);
        return name;

    }


    @Override
    public void run() {
        super.run();
        GetName();
        sp = activity.getSharedPreferences("api_data", 0);
        editor = sp.edit();

        try {
            URL url = new URL(name);
            HttpsURLConnection mojang = (HttpsURLConnection) url.openConnection();
            mojang.addRequestProperty("User-Agent", "Mozilla/4.0");
            mojang.setRequestMethod("GET");
            mojang.setReadTimeout(5000);

            if (mojang.getResponseCode() == 200) {
                InputStream inputStream = mojang.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(mojang.getInputStream()));
                String inputLine;
                StringBuilder sb = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                in.close();
            }
            br.close();
            mojang.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
