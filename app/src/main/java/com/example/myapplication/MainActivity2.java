package com.example.myapplication;

import static com.example.myapplication.MainActivity7.db;
import static com.example.myapplication.util.BazaarUtils.getBazaar;
import static com.example.myapplication.util.BazaarUtils.getBazaarsForDB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.rvAdapter;
import com.example.myapplication.Bean.Bean;
import com.example.myapplication.hypixel.BazaarInfo;
import com.example.myapplication.query.BazaarQuery;
import com.example.myapplication.query.BazaarsQuery;
import com.example.myapplication.util.BazaarUtils;
import com.example.myapplication.util.SqlUtils;
import com.example.myapplication.util.WaitDialog;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.SocketTimeoutException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity2 extends AppCompatActivity {

    Toolbar tb;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = findViewById(R.id.tb);
        tb.setNavigationOnClickListener(v -> finish());
        ucanuup = findViewById(R.id.u_can_u_up);
        recyclerView = findViewById(R.id.rv);
        SqlUtils sqlUtils = new SqlUtils(db);


        mHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };

        //getBz();
        ucanuup.setVisibility(View.GONE);
        ucanuup.setOnClickListener(v->{
            // 可输入文本的提示框
                final EditText edt = new EditText(this);
                edt.setMinLines(3);
                new AlertDialog.Builder(this)
                        .setTitle("请输入")
                        .setMessage("这是一个自定义sql语句的输入框，你可以在这里输入你自己写的sql命令来替换原有的sql命令"+"\n"+
                        "表名：emp,字段名：displayname,buyPrice,sellPrice,buyVolume,sellVolume")
                        .setView(edt)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                sql = edt.getText().toString();
                                U_Can_U_Do_This = true;
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
        });



        if(!U_Can_U_Do_This){
            cursor = sqlUtils.query("emp",new String[]{"displayname","buyPrice","sellPrice","buyVolume","sellVolume"},null,null,null,null,null);
        }else{
            cursor = db.rawQuery(sql,null);
        }

        int i = 0;

        while (cursor.moveToNext()){
            i++;

            Bean displayname = new Bean();
            displayname.setName(i+"."+cursor.getString(0));
            data.add(displayname);

            Bean buyPrice = new Bean();
            double var1 = cursor.getDouble(1);
            String s = formatDouble(var1);
            buyPrice.setName(s);
            BuyPrice.add(buyPrice);

            Bean sellPrice = new Bean();
            double var2 = cursor.getDouble(2);

            String s1 = formatDouble(var2);
            sellPrice.setName(s1);
            SellPrice.add(sellPrice);

            Bean buyVolume = new Bean();
            buyVolume.setName(cursor.getString(3));
            BuyVolume.add(buyVolume);

            Bean sellVolume = new Bean();
            sellVolume.setName(cursor.getString(4));
            SellVolume.add(sellVolume);
        }
        sqlUtils.closeCursor();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        rvAdapter rvAdapter= new rvAdapter(data,BuyPrice,SellPrice,BuyVolume,SellVolume,this);
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnRecyclerViewItemClickListener(position ->{

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

    void getBz(){
        FutureTask<BazaarInfo> var6 = new FutureTask<>(new BazaarsQuery(
                s -> Toast.makeText(MainActivity2.this, s, Toast.LENGTH_SHORT).show()));
        new Thread(var6).start();
        new Thread(()->{
            runOnUiThread(this::WaitingDialog);
            try {
                BazaarInfo bzi = var6.get();
                runOnUiThread(()->{
                    WaitDialog.Companion.WaitDialogDismiss();

                });
            } catch (ExecutionException e) {
                if (e.getCause() instanceof SocketTimeoutException){
                    netWorkErr(e);
                }
            } catch (InterruptedException e) {
                CreateErrorDialog(e);
            }
        }).start();


    }

    public void CreateErrorDialog(Exception e) {
        e.printStackTrace();
        runOnUiThread(() -> {
            WaitDialog.Companion.WaitDialogDismiss();
            String s = String.valueOf(e);
            AlertDialog dialog = new AlertDialog.Builder(MainActivity2.this)
                    .setTitle("出现异常！")
                    .setMessage(s)
                    .setPositiveButton("确定", (dialog1, which) -> {
                    })
                    .create();
            dialog.setOnCancelListener(dialog12 -> {
                Toast.makeText(this, "如多次遇到此问题可在github上提交issues反馈", Toast.LENGTH_LONG).show();
            });
            dialog.show();
        });
    }

    public void netWorkErr(Exception e) {
        e.printStackTrace();
        WaitDialog.Companion.WaitDialogDismiss();
        String s = String.valueOf(e);
        AlertDialog dialog = new AlertDialog.Builder(MainActivity2.this)
                .setTitle("出现异常！")
                .setMessage("遇到此问题时可以尝试重试，一般为网络原因引起" + "\n" + s)
                .setPositiveButton("确定", (dialog1, which) -> {
                })
                .create();
        dialog.show();
    }

    void WaitingDialog() {
        MainActivity2.this.runOnUiThread(() -> {
            WaitDialog.Companion.waitingDialog(this);
        });
    }
}