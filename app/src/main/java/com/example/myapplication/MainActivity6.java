package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.util.HypixelUtils;

public class MainActivity6 extends AppCompatActivity {

    Button button;
    EditText editText;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);


        sp = getSharedPreferences("api_data",MODE_PRIVATE);
        editor = sp.edit();

        button = findViewById(R.id.accept);
        editText = findViewById(R.id.api_key);
        button.setOnClickListener(v -> {
            String api =editText.getText().toString();
            if(TextUtils.isEmpty(api)){
                Toast.makeText(MainActivity6.this, "api key不能为空", Toast.LENGTH_SHORT).show();
            }
            else{
                editor.putString("api_key",api);
                editor.commit();
                HypixelUtils.setApiKey(api);
            }
            Intent intent = new Intent(MainActivity6.this,MainActivity7.class);
            startActivity(intent);
        });
    }
}