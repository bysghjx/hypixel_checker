package com.example.myapplication.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.rvAdapter;
import com.example.myapplication.Bean.Bean;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class BlankFragment2 extends Fragment {

    private View root;
    Button ucanuup;
    RecyclerView recyclerView;
    List<Bean> data = new ArrayList<>();
    List<Bean> BuyPrice = new ArrayList<>();
    List<Bean> SellPrice = new ArrayList<>();
    List<Bean> BuyVolume = new ArrayList<>();
    List<Bean> SellVolume = new ArrayList<>();
    boolean U_Can_U_Do_This = false;
    private String TAG,sql;
    private Cursor cursor;
    public static Handler mHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_blank, container, false);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ucanuup = view.findViewById(R.id.u_can_u_up);
        recyclerView = view.findViewById(R.id.rv);


/*        LinearLayoutManager linearLayoutManager = new LinearLayoutManager();
        recyclerView.setLayoutManager(linearLayoutManager);

        rvAdapter rvAdapter= new rvAdapter(data,BuyPrice,SellPrice,BuyVolume,SellVolume,this);
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnRecyclerViewItemClickListener(position ->{

        });
        */
    }
}