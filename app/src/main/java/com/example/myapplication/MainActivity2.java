package com.example.myapplication;

import static com.example.myapplication.MainActivity7.db;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Bean.Bean;
import com.example.myapplication.fragment.BlankFragment2;
import com.example.myapplication.fragment.BlankFragment3;
import com.example.myapplication.interfaces.UCanUUPCallBack;
import com.example.myapplication.DAO.SqlUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    Toolbar tb;
    Button ucanuup;
    RecyclerView recyclerView;
    List<Bean> data = new ArrayList<>();
    List<Bean> BuyPrice = new ArrayList<>();
    List<Bean> SellPrice = new ArrayList<>();
    List<Bean> BuyVolume = new ArrayList<>();
    List<Bean> SellVolume = new ArrayList<>();
    public static boolean search = false;
    public static String TAG, sql;
    private Cursor cursor;
    public static Handler mHandler;
    private FragmentManager fragmentManager;
    UCanUUPCallBack uCanUUPCallBack;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static boolean AUTO_REFRESH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("api_data", MODE_PRIVATE);
        editor = sp.edit();

        AUTO_REFRESH = sp.getBoolean("AUTO_REFRESH",false);

        toolbarInit();

        //ucanuup = findViewById(R.id.u_can_u_up);
        SqlUtils sqlUtils = new SqlUtils(db);


        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    String s = String.valueOf(msg);
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity2.this)
                            .setTitle("出现异常！")
                            .setMessage("遇到此问题时可以尝试重试，一般为网络原因引起" + "\n" + s)
                            .setPositiveButton("确定", (dialog1, which) -> {
                    }).create();
                    dialog.show();
                }
            }
        };

        //ucanuup.setVisibility(View.GONE);
/*        ucanuup.setOnClickListener(v -> {
            // 可输入文本的提示框
            final EditText edt = new EditText(this);
            edt.setMinLines(3);
            new AlertDialog.Builder(this).setTitle("请输入").setMessage("这是一个自定义sql语句的输入框，你可以在这里输入你自己写的sql命令来替换原有的sql命令" + "\n" + "表名：emp,字段名：displayname,buyPrice,sellPrice,buyVolume,sellVolume").setView(edt).setPositiveButton("确定", (arg0, arg1) -> {
                sql = edt.getText().toString();
                U_Can_U_Do_This = true;
            }).setNegativeButton("取消", null).show();
        });*/

    if(!MainActivity7.select.equals("ah")){
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, BlankFragment2.class, null)
/*                    .setReorderingAllowed(true)
                    .addToBackStack("1")*/
                    .commit();
        }
    }else {
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, BlankFragment3.class, null)
/*                    .setReorderingAllowed(true)
                    .addToBackStack("1")*/
                    .commit();
        }
    }


    }

    @Override
    protected void onPause() {
        super.onPause();
        search = false;
        MainActivity7.select = "player";
    }

    private void toolbarInit() {
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(v->{
            fragmentManager.popBackStack();
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.rec_view_menu,menu);
/*        CheckBox checkBox = (CheckBox) menu.findItem(R.id.reFresh).getActionView();
        checkBox.setText(String.format("%s:开",R.string.autoRefresh));*/
        MenuItem item = menu.findItem(R.id.reFresh);
        if(!MainActivity7.select.equals("ah")){
            if(AUTO_REFRESH){
                item.setTitle(String.format("%s:开",this.getResources().getString(R.string.autoRefresh)));
            }else{
                item.setTitle(String.format("%s:关",this.getResources().getString(R.string.autoRefresh)));
            }
        }else{
            item.setVisible(false);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.reFresh:{
                if(AUTO_REFRESH){
                    AUTO_REFRESH = false;
                    item.setTitle(String.format("%s:关",this.getResources().getString(R.string.autoRefresh)));
                    editor.putBoolean("AUTO_REFRESH", false);
                }else{
                    AUTO_REFRESH = true;
                    item.setTitle(String.format("%s:开",this.getResources().getString(R.string.autoRefresh)));
                    editor.putBoolean("AUTO_REFRESH", true);
                }
                editor.commit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {

            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {

                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}