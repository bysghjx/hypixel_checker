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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                input_name = name;

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity7.this, "name不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Thread thread = new MojangHttps(MainActivity7.this,input_name);
                    thread.start();
                    if(!thread.isAlive()){
                        if(sp.getString("uuid",null) != null){
                            Thread thread1 = new HypixelHttps(uuid,api_key);
                            Log.e("join hyp","join hyp");
                            thread1.start();

                            GetPlayerName gpn =  new GetPlayerName(MainActivity7.this);

                            String Pdata = gpn.getData();

                            editor.putString("Pdata",Pdata);
                            editor.commit();
/*                            if(!thread1.isAlive()){
                                String Pname = tv.getText().toString();
                                if(Pname.equals("PlayerName")){

                                }
                            }*/
                        }
                    }

                }
            }
        });

    }
}