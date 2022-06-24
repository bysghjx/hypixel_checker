package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.hypixel.HypixelBedWarsInfo;
import com.example.myapplication.hypixel.HypixelPlayerInfo;
import com.example.myapplication.query.HypixelBedwarsQuery;
import com.example.myapplication.query.HypixelPlayerQuery;
import com.example.myapplication.util.HypixelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity7 extends AppCompatActivity {



    Button button, button_se;
    EditText editText;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String input_name;
    String uuid;
    String api_key;
    String api;
    public static HypixelPlayerInfo lastQueriedPlayer;
    public static HypixelBedWarsInfo lastQueriedBedwars;
    public static String select = "player";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> list = new ArrayList<>(Arrays.asList("1",
                "2", "3", "4"));

        setContentView(R.layout.activity_main7);

        button_se = findViewById(R.id.select);
        editText = findViewById(R.id.mc_name);
        button = findViewById(R.id.button6);

        sp = getSharedPreferences("api_data", MODE_PRIVATE);
        editor = sp.edit();

        uuid = sp.getString("uuid", null);
        api_key = sp.getString("api_key", null);
        api = api_key;
        HypixelUtils.setApiKey(api);


        button_se.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity7.this,MainActivity3.class);
            startActivity(intent);
        });


        button.setOnClickListener(v -> {
            String name = editText.getText().toString();
            input_name = name;

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(MainActivity7.this, "name不能为空", Toast.LENGTH_SHORT).show();
            } else {
                switch (select){

                    case "player":
                        FutureTask<HypixelPlayerInfo> var0 = new FutureTask<>(new HypixelPlayerQuery(input_name, s ->
                                Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show()
                        ));
                        new Thread(var0).start();

                        new Thread(() -> {
                            try {
                                HypixelPlayerInfo pi = var0.get();
                                MainActivity7.this.runOnUiThread(() -> {
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedPlayer = pi;
                                    startActivity(intent);
                                });
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    break;

                    case "bw":
                        FutureTask<HypixelBedWarsInfo> var1 = new FutureTask<>(new HypixelBedwarsQuery(input_name, s ->
                                Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show()
                        ));
                        new Thread(var1).start();

                        new Thread(() -> {
                            try {
                                HypixelBedWarsInfo bi = var1.get();
                                Log.i("bi",bi.toString());
                                MainActivity7.this.runOnUiThread(() -> {
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedBedwars = bi;
                                    startActivity(intent);
                                });
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        break;

                }

            }
        });

    }



}