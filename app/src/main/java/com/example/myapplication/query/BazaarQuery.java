package com.example.myapplication.query;

import android.os.Looper;

import com.example.myapplication.interfaces.Acceptable;
import com.example.myapplication.hypixel.BazaarInfo;
import com.example.myapplication.util.BazaarUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

public class BazaarQuery implements Callable<BazaarInfo> {

    public final String input_name;
    private final Acceptable<String> onToast;

    public BazaarQuery(String input_name, Acceptable<String> onToast) {
        this.input_name = input_name;
        this.onToast = onToast;
    }

    @Override
    public BazaarInfo call() throws IOException {
        Looper.prepare();
        BazaarInfo bazaarInfo = new BazaarInfo();
        int code = BazaarUtils.getBazaars(input_name, bazaarInfo);
        if (code == -199){
            onToast.accept("[×]不存在的物品");
            Looper.loop();
        }

        if (code == 200){
            return bazaarInfo;
        }

        onToast.accept("Http: " + code);
        Looper.loop();
        return null;
    }

}
