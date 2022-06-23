package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.hypixel.GetPlayerName;
import com.example.myapplication.hypixel.HypixelPlayerInfo;
import com.example.myapplication.query.HypixelPlayerQuery;
import com.example.myapplication.util.HypixelUtils;
import com.example.myapplication.util.MojangUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity7 extends AppCompatActivity {

    Button button;
    EditText editText;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String input_name;
    TextView tv;
    String uuid;
    String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        tv = findViewById(R.id.textView2);
        editText = findViewById(R.id.mc_name);
        button = findViewById(R.id.button6);

        sp = getSharedPreferences("api_data",MODE_PRIVATE);
        editor = sp.edit();

        uuid = sp.getString("uuid",null);
        api_key = sp.getString("api_key",null);
        editor.putString("p_data","");
        editor.commit();

        button.setOnClickListener(v -> {
            String name = editText.getText().toString();
            input_name = name;

            if(TextUtils.isEmpty(name)){
                Toast.makeText(MainActivity7.this, "name不能为空", Toast.LENGTH_SHORT).show();
            }else {
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
                            intent.putExtra("data", pi.toString());
                            startActivity(intent);
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

    }
}