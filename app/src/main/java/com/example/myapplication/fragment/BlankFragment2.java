package com.example.myapplication.fragment;

import static com.example.myapplication.MainActivity2.AUTO_REFRESH;
import static com.example.myapplication.MainActivity2.sql;
import static com.example.myapplication.MainActivity7.db;
import static com.example.myapplication.util.BazaarUtils.getBazaarsForDB;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.rvAdapter;
import com.example.myapplication.Bean.Bean;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.DAO.SqlUtils;
import com.example.myapplication.util.WaitDialog;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class BlankFragment2 extends Fragment {

    private View root;
    static RecyclerView recyclerView;
    public static List<Bean> data = new ArrayList<>();
    public static List<Bean> BuyPrice = new ArrayList<>();
    public static List<Bean> SellPrice = new ArrayList<>();
    public static List<Bean> BuyVolume = new ArrayList<>();
    public static List<Bean> SellVolume = new ArrayList<>();
    boolean U_Can_U_Do_This = false;
    private Cursor cursor;
    String TAG;
    public static Handler mHandler;
    private SqlUtils sqlUtils;
    private rvAdapter rvadapter;
    private Button button;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_blank2, container, false);
        }

        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv);
        button = view.findViewById(R.id.u_can_u_up);
        sqlUtils = new SqlUtils(db);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        getBzForAdapter(sqlUtils);
        setHandler();

        button.setOnClickListener(v->{
            // 可输入文本的提示框
            final EditText edt = new EditText(getActivity());
            edt.setMinLines(3);
            new AlertDialog.Builder(requireActivity()).setTitle("请输入")
                    .setMessage("启用搜索功能后自动刷新将被自动禁用(退出界面后将自动恢复)")
                    .setView(edt)
                    .setPositiveButton("确定", (arg0, arg1) -> {
                MainActivity2.search = true;
                sql = edt.getText().toString();
                extracted(sql);
            }).setNegativeButton("取消", null).show();

        });

        rvAdapter rvAdapter = new rvAdapter(data, BuyPrice, SellPrice, BuyVolume, SellVolume, getActivity());
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnRecyclerViewItemClickListener(position -> {

        });
    }


    private void extracted(String sql) {
        clearArr();
        String sql1 = String.format("select `displayname`,`buyPrice`,`sellPrice`,`buyVolume`,`sellVolume` from emp where `displayname` like '%s'","%"+sql+"%");
        //sqlUtils.query("emp",new String[]{"displayname","buyPrice","sellPrice","buyVolume","sellVolume"})
        Cursor cursor1 = sqlUtils.rawQuery(sql1,null);
        insertAdapter(cursor1);
    }


    void getBzForAdapter(SqlUtils sqlUtils) {

        new Thread(() -> {
            try {
                getBazaarsForDB(Success -> {
                    clearArr();
                        cursor = sqlUtils.query("emp", new String[]{"displayname", "buyPrice", "sellPrice", "buyVolume", "sellVolume"}, null, null, null, null, null);
                    insertAdapter(cursor);
                });


            } catch (IOException|NullPointerException e) {
                netWorkErr(e);
            }
        }).start();

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

    public void netWorkErr(Exception e) {
        e.printStackTrace();
        Message message = new Message();
        message.what = 1;
        message.obj = 1;
        MainActivity2.mHandler.sendMessage(message);
    }

    void WaitingDialog() {
        requireActivity().runOnUiThread(() -> WaitDialog.Companion.waitingDialog(requireActivity()));
    }

    void DismissWaitingDialog() {
        requireActivity().runOnUiThread(WaitDialog.Companion::WaitDialogDismiss);
    }

    void setHandler() {
        Handler handler = new Handler(Looper.myLooper());
        if (AUTO_REFRESH && !MainActivity2.search) {
            handler.postDelayed(() -> {
                Log.e(TAG, "setHandler1: "+MainActivity2.search);
                if(!MainActivity2.search){
                    getBzForAdapter(sqlUtils);
                }
                setHandler();
            }, 300000);
        }else{
            Log.e(TAG, "setHandler2: "+MainActivity2.search);
            handler.postDelayed(this::setHandler,5000);
        }
    }

    private void insertAdapter(Cursor cursor1) {
        int i = 0;

        while (cursor1.moveToNext()) {
            i++;

            Bean displayname = new Bean();
            displayname.setName(i + "." + cursor1.getString(0));
            data.add(displayname);

            Bean buyPrice = new Bean();
            double var1 = cursor1.getDouble(1);
            String s = formatDouble(var1);
            buyPrice.setName(s);
            BuyPrice.add(buyPrice);

            Bean sellPrice = new Bean();
            double var2 = cursor1.getDouble(2);

            String s1 = formatDouble(var2);
            sellPrice.setName(s1);
            SellPrice.add(sellPrice);

            Bean buyVolume = new Bean();
            buyVolume.setName(cursor1.getString(3));
            BuyVolume.add(buyVolume);

            Bean sellVolume = new Bean();
            sellVolume.setName(cursor1.getString(4));
            SellVolume.add(sellVolume);
        }
        sqlUtils.closeCursor();
        if(isAdded()){
            requireActivity().runOnUiThread(() -> {
                recyclerView.post(() -> {

                    rvadapter = new rvAdapter(data, BuyPrice, SellPrice, BuyVolume, SellVolume, getActivity());

                    recyclerView.setLayoutManager(linearLayoutManager);
                    if (recyclerView.getChildCount() > 0){
                        recyclerView.removeAllViews();
                        rvadapter.notifyItemRangeRemoved(0, rvadapter.getItemCount());
                        rvadapter.notifyItemRangeRemoved(0, rvadapter.getItemCount());
                        Log.e(TAG, "extracted: 1");
//                    recyclerView.notifyAll();
                        rvadapter.notifyDataSetChanged();
                    }
                    rvadapter.notifyDataSetChanged();
                    rvadapter.setOnRecyclerViewItemClickListener(position -> {
                    });
                });
            });
        }

    }

    private void clearArr() {
        data.clear();
        BuyPrice.clear();
        SellPrice.clear();
        BuyVolume.clear();
        SellVolume.clear();
    }

}