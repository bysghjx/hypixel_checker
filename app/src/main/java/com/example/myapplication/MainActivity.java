package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.hypixel.HypixelPlayerInfo;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sp = getSharedPreferences("api_data",MODE_PRIVATE);
        tv = findViewById(R.id.textView);

        HypixelPlayerInfo pi = MainActivity7.lastQueried;
        String str = "玩家名: " + pi.name + "\n" +
                "玩家UUID: " + pi.uuid + "\n" +
                "玩家最后一次上线时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pi.lastLogin) + "\n" +
                "玩家语言: " + pi.final_kills_bedwars + "\n" ;
        tv.setText(str);

        }


    }