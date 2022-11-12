package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.hypixel.BazaarInfo;
import com.example.myapplication.hypixel.HypixelBedWarsInfo;
import com.example.myapplication.hypixel.HypixelDuelInfo;
import com.example.myapplication.hypixel.HypixelMurderMysteryInfo;
import com.example.myapplication.hypixel.HypixelPlayerInfo;
import com.example.myapplication.hypixel.HypixelSkyWarsInfo;
import com.example.myapplication.hypixel.HypixelUHCInfo;
import com.example.myapplication.query.BazaarQuery;
import com.example.myapplication.query.HypixelBedwarsQuery;
import com.example.myapplication.query.HypixelDuelQuery;
import com.example.myapplication.query.HypixelMurderMysteryQuery;
import com.example.myapplication.query.HypixelPlayerQuery;
import com.example.myapplication.query.HypixelSkyWarsQuery;
import com.example.myapplication.query.HypixelUHCQuery;
import com.example.myapplication.util.DBHelper;
import com.example.myapplication.util.HypixelUtils;
import com.example.myapplication.util.IOUtils;
import com.example.myapplication.util.RanDomUtils;
import com.example.myapplication.util.WaitDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity7 extends AppCompatActivity {


    Button query, button_se, resetkey,bzTest;
    EditText editText;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String input_name;
    String uuid;
    String api_key;
    String api;
    public static HypixelPlayerInfo lastQueriedPlayer;
    public static HypixelBedWarsInfo lastQueriedBedwars;
    public static HypixelSkyWarsInfo lastQueriedSkywars;
    public static HypixelDuelInfo lastQueriedDuels;
    public static HypixelMurderMysteryInfo lastQueriedMm;
    public static HypixelUHCInfo lastQueriedUHC;
    public static BazaarInfo lastQueriedBazzar;
    public DBHelper dbHelper;
    public static SQLiteDatabase db;
    public static ContentValues values;

    public static String select = "player";
    FragmentManager fragmentManager;
    public static Handler mHandler;

    public static int ran = (int) RanDomUtils.INSTANCE.createRan(5000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbInit();

        List<String> list = new ArrayList<>(Arrays.asList("1",
                "2", "3", "4"));
        setContentView(R.layout.activity_main7);


/*        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, BlankFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("1")
                    .commit();
        }*/

        mHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                AlertDialog dialog;
                if(msg.what == 0){
                    WaitDialog.Companion.WaitDialogDismiss();
                    String s = String.valueOf(msg);
                    dialog = new AlertDialog.Builder(MainActivity7.this)
                            .setTitle("出现异常！")
                            .setMessage("遇到此问题时可以尝试重试，一般为网络原因引起" + "\n" + s)
                            .setPositiveButton("确定", (dialog1, which) -> {
                                setButtonEnabled();
                            })
                            .create();
                    dialog.show();
                }
                if(msg.what == ran){
                    ProgressDialog progressDialog = WaitDialog.Companion.proG();
                    progressDialog.setMessage("正在向数据库写入数据中");
                }


            }
        };

        resetkey = findViewById(R.id.btn_reset);
        button_se = findViewById(R.id.select);
        editText = findViewById(R.id.mc_name);
        query = findViewById(R.id.button6);
        bzTest = findViewById(R.id.bzListTest);


        sp = getSharedPreferences("api_data", MODE_PRIVATE);
        editor = sp.edit();

        uuid = sp.getString("uuid", null);
        api_key = sp.getString("api_key", null);
        api = api_key;
        HypixelUtils.setApiKey(api);

        bzTest.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity7.this,MainActivity2.class);
            startActivity(intent);
        });

        resetkey.setOnClickListener(v -> {
            editor.remove("api_key");
            editor.commit();
            Intent intent = new Intent(MainActivity7.this, MainActivity6.class);
            startActivity(intent);
        });

        button_se.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity7.this, MainActivity3.class);
            startActivity(intent);
        });


        query.setOnClickListener(v -> {
            setButtonDisabled();

            String name = editText.getText().toString();
            input_name = name;

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(MainActivity7.this, "name不能为空", Toast.LENGTH_SHORT).show();

                setButtonEnabled();

            } else {
                if (select == null) {
                    select = "player";
                }
                switch (select) {

                    case "player":
                        FutureTask<HypixelPlayerInfo> var0 = new FutureTask<>(new HypixelPlayerQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var0).start();

                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                HypixelPlayerInfo pi = var0.get();
                                MainActivity7.this.runOnUiThread(() -> {
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedPlayer = pi;
                                    startActivity(intent);
                                });
                            }  catch (InterruptedException e) {
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }).start();
                        break;

                    case "bw":
                        FutureTask<HypixelBedWarsInfo> var1 = new FutureTask<>(new HypixelBedwarsQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var1).start();

                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                HypixelBedWarsInfo bi = var1.get();
                                Log.i("bi", bi.toString());
                                MainActivity7.this.runOnUiThread(() -> {
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedBedwars = bi;
                                    startActivity(intent);
                                });
                            }  catch (InterruptedException e) {
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }).start();
                        break;

                    case "SkyWars": {
                        FutureTask<HypixelSkyWarsInfo> var2 = new FutureTask<>(new HypixelSkyWarsQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var2).start();

                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                HypixelSkyWarsInfo si = var2.get();
                                Log.i("si", si.toString());
                                MainActivity7.this.runOnUiThread(() -> {
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedSkywars = si;
                                    startActivity(intent);
                                });
                            }  catch (InterruptedException e) {
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }).start();
                        break;
                    }

                    case "Duel": {
                        FutureTask<HypixelDuelInfo> var3 = new FutureTask<>(new HypixelDuelQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var3).start();

                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                HypixelDuelInfo di = var3.get();
                                Log.i("di", di.toString());
                                MainActivity7.this.runOnUiThread(() -> {
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedDuels = di;
                                    startActivity(intent);
                                });
                            }  catch (InterruptedException e) {
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }).start();
                        break;
                    }

                    case "Mm": {
                        FutureTask<HypixelMurderMysteryInfo> var4 = new FutureTask<>(new HypixelMurderMysteryQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var4).start();

                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                HypixelMurderMysteryInfo mi = var4.get();
                                Log.i("mi", mi.toString());
                                MainActivity7.this.runOnUiThread(() -> {
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedMm = mi;
                                    startActivity(intent);
                                });
                            }  catch (InterruptedException e) {
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }).start();
                        break;
                    }

                    case "UHC": {
                        FutureTask<HypixelUHCInfo> var5 = new FutureTask<>(new HypixelUHCQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var5).start();

                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                HypixelUHCInfo ui = var5.get();
                                Log.i("ui", ui.toString());
                                MainActivity7.this.runOnUiThread(() -> {
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedUHC = ui;
                                    startActivity(intent);
                                });
                            }  catch (InterruptedException e) {
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }).start();
                        break;
                    }
                    case "Bz": {
                        FutureTask<BazaarInfo> var6 = new FutureTask<>(new BazaarQuery(input_name, s -> {
                            Toast.makeText(MainActivity7.this, s, Toast.LENGTH_SHORT).show();
                            DismissWaitingDialog();
                        }));
                        new Thread(var6).start();
                        new Thread(() -> {
                            try {
                                MainActivity7.this.runOnUiThread(this::WaitingDialog);
                                BazaarInfo bzi = var6.get();

                                runOnUiThread(()->{
                                    setButtonEnabled();
                                    WaitDialog.Companion.WaitDialogDismiss();
                                    Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                                    lastQueriedBazzar = bzi;
                                    startActivity(intent);
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                CreateErrorDialog(e);
                            } catch (ExecutionException e) {
                                if (e.getCause() instanceof SocketTimeoutException) {
                                    e.printStackTrace();
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = e;
                                    mHandler.sendMessage(message);
                                }
                            }

                        }).start();
                        break;
                    }

                }

            }
        });

    }

    void DismissWaitingDialog() {
        MainActivity7.this.runOnUiThread(() -> {
            WaitDialog.Companion.WaitDialogDismiss();
            setButtonEnabled();
        });
    }

    void WaitingDialog() {
        MainActivity7.this.runOnUiThread(() -> {
            WaitDialog.Companion.waitingDialog(this);
        });
    }

    public void CreateErrorDialog(Exception e) {
        e.printStackTrace();
        runOnUiThread(() -> {
            WaitDialog.Companion.WaitDialogDismiss();
            setButtonEnabled();
            String s = String.valueOf(e);
            AlertDialog dialog = new AlertDialog.Builder(MainActivity7.this)
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

    public void setButtonEnabled() {
        button_se.setEnabled(true);
        resetkey.setEnabled(true);
        query.setEnabled(true);
        editText.setEnabled(true);
    }

    void setButtonDisabled() {
        query.setEnabled(false);
        button_se.setEnabled(false);
        resetkey.setEnabled(false);
        editText.setEnabled(false);
    }
    void dbInit(){
        dbHelper = new DBHelper(MainActivity7.this);
        db = dbHelper.getReadableDatabase();
        values = new ContentValues();
    }

}