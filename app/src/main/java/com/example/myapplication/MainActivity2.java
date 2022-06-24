package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity2 extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        radioGroup = findViewById(R.id.RG_select);
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String id = radioButton.getText().toString();
        setResult(RESULT_OK,new Intent().putExtra("s",id));
    }
}