package com.example.myapplication.query;

import android.os.Looper;

import com.example.myapplication.interfaces.Acceptable;
import com.example.myapplication.hypixel.BazaarInfo;

import java.util.concurrent.Callable;

public class BazaarsQuery implements Callable<BazaarInfo> {

    private final Acceptable<String> onToast;

    public BazaarsQuery(Acceptable<String> onToast) {
        this.onToast = onToast;
    }

    @Override
    public BazaarInfo call() throws Exception {
        Looper.prepare();

        return null;
    }
}
