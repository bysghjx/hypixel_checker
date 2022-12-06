package com.example.myapplication.query;

import static com.example.myapplication.MainActivity7.mHandler;

import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.os.Message;

import com.example.myapplication.hypixel.AuctionsInfo;
import com.example.myapplication.interfaces.Acceptable;
import com.example.myapplication.util.AuctionsItemName;
import com.example.myapplication.util.HypixelUtils;
import com.example.myapplication.util.MojangUtils;

import java.util.concurrent.Callable;

public class AuctionsQuery implements Callable<AuctionsInfo> {
    public final String input_name;
    private final Acceptable<String> onToast;
    public String true_name;
    SQLiteDatabase db;

    public AuctionsQuery(String input_name, Acceptable<String> onToast, SQLiteDatabase db) {
        this.input_name = input_name;
        this.onToast = onToast;
        this.db = db;
    }

    @Deprecated
    public String getTrue_name(){
        int i = AuctionsItemName.Companion.ahSql(db, input_name, sql -> {
            Message message = new Message();
            message.what = 5;
            message.obj = sql;
            mHandler.sendMessage(message);
            return null;
        });
        if (i == 1){
            AuctionsItemName.Companion.getData();
        }else {
            true_name = "error";
        }
        return true_name;
    }


    @Override
    public AuctionsInfo call() throws Exception {
        Looper.prepare();
        String uuid = MojangUtils.getUUIDByName(input_name);
        AuctionsInfo auctionsInfo = new AuctionsInfo();
        int code = HypixelUtils.getAh(uuid);
        if (code == -199){
            onToast.accept("[×]不存在的物品");
            Looper.loop();
        }

        if (code == 200){
            return auctionsInfo;
        }

        onToast.accept("Http: " + code);
        Looper.loop();
        return null;
    }
}
