package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button button;
    String id;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Toolbar tb;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main3);
        com.example.myapplication.databinding.ActivityMain3Binding binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        radioGroup = binding.RGSelect;
        button = binding.back;
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        sp = getSharedPreferences("api_data",0);
        editor = sp.edit();
        tb = binding.tb;
        tb.setNavigationOnClickListener(v -> finish());


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.Rb_Bed_Info:{
                     id ="bw";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                case R.id.Rb_Mm_Info:{
                     id = "Mm";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                case R.id.Rb_SkyWars_Info:{
                     id = "SkyWars";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                case R.id.Rb_Duels_Info:{
                     id = "Duel";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                case R.id.Rb_UHC_Info:{
                    id = "UHC";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                case R.id.Rb_BZ_Info:{
                    id = "Bz";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                case R.id.Rb_AH_Info:{
                    id = "ah";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
                default:{
                     id = "player";
                    MainActivity7.select = id;
                    finish();
                    break;
                }
            }
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this,MainActivity7.class);
            startActivity(intent);
            finish();
        });


    }
}
