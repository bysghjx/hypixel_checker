package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity7 extends AppCompatActivity {

    Button button;
    EditText editText;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String input_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        editText = findViewById(R.id.mc_name);
        button = findViewById(R.id.button6);

        sp = getSharedPreferences("api_data",MODE_PRIVATE);
        editor = sp.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                input_name = name;

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity7.this, "name不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Thread thread = new Https(MainActivity7.this,input_name);
                    thread.start();

                }
            }
        });

    }
}