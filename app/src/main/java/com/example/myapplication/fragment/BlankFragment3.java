package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AHP_Adapter;
import com.example.myapplication.Bean.Bean;
import com.example.myapplication.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class BlankFragment3 extends Fragment {
    private View root;
    static RecyclerView recyclerView;
    public static List<Bean> bin = new ArrayList<>();
    public static List<Bean> starting_bid = new ArrayList<>();
    public static List<Bean> highest_bid_amount = new ArrayList<>();
    public static List<Bean> end = new ArrayList<>();
    public static List<Bean> item_name = new ArrayList<>();
    String TAG;
    private AHP_Adapter ahp_adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_blank3, container, false);
        }

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());


        AHP_Adapter ahp_adapter = new AHP_Adapter(bin, starting_bid, highest_bid_amount, end, item_name, getActivity());
        recyclerView.setAdapter(ahp_adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        ahp_adapter.setOnRecyclerViewItemClickListener(position -> {

        });
    }


    private static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(2);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        clearArr();
    }

    private void clearArr() {
        bin.clear();
        starting_bid.clear();
        highest_bid_amount.clear();
        end.clear();
        item_name.clear();
    }
}