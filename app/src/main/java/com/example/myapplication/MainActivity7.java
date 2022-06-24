package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.hypixel.HypixelPlayerInfo;
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
    public static HypixelPlayerInfo lastQueried;
    String select;





    ActivityResultLauncher launcher = registerForActivityResult(new ResultContract(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            select = result;
        }
    });


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
        editor.putString("p_data", "");
        editor.commit();
        api = api_key;
        HypixelUtils.setApiKey(api);


        button_se.setOnClickListener(v -> {
            launcher.launch(true);
        });


        button.setOnClickListener(v -> {
            String name = editText.getText().toString();
            input_name = name;

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(MainActivity7.this, "name不能为空", Toast.LENGTH_SHORT).show();
            } else {
                /*
                FutureTask<Void> task = new FutureTask<>(new MojangHttps(MainActivity7.this, input_name, result -> {
                    if(sp.getString("uuid",null) != null){
                        Thread thread1 = new HypixelHttps(uuid,api_key);
                        Log.e("join hyp","join hyp");
                        thread1.start();

                        GetPlayerName gpn =  new GetPlayerName(MainActivity7.this);

                        String Pdata = gpn.getData();

                        editor.putString("Pdata",Pdata);
                        editor.commit();
                    }
                }));
                new Thread(task).start();
                */

//                    String uuid = MojangUtils.getUUIDByName(input_name);
//                    if (uuid == null) {
//                        Toast.makeText(MainActivity7.this, "找不到对应uuid!请检查用户名是否输入正确" , Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    HypixelPlayerInfo pi = new HypixelPlayerInfo();
//                    int code = HypixelUtils.getPlayer(uuid, pi);
//                    if (code == -1) {
//                        Toast.makeText(MainActivity7.this, "查询失败", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    if (code == 200) {
//                        Intent intent = new Intent(MainActivity7.this,MainActivity.class);
//                        intent.putExtra("data",pi.toString());
//                        startActivity(intent);
//                        return;
//                    }
//
//                    Toast.makeText(MainActivity7.this, "Http: " + code, Toast.LENGTH_SHORT).show();

                FutureTask<HypixelPlayerInfo> task = new FutureTask<>(new HypixelPlayerQuery(input_name, s ->
                        Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show()
                ));
                new Thread(task).start();

                new Thread(() -> {
                    try {
                        HypixelPlayerInfo pi = task.get();
                        MainActivity7.this.runOnUiThread(() -> {
                            Intent intent = new Intent(MainActivity7.this, MainActivity.class);
//                            intent.putExtra("data", pi.toString());
                            lastQueried = pi;
                            startActivity(intent);
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

    }


    class ResultContract extends ActivityResultContract<Boolean, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Boolean input) {
            Intent intent = new Intent(MainActivity7.this, MainActivity2.class);
            intent.putExtra("b", input);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            return intent.getStringExtra("s");
        }

    }
}