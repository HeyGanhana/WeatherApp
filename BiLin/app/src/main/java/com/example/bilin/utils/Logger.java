package com.example.bilin.utils;

import android.util.Log;


/**
 * Created by zhangdi on 2018/4/5.
 */

public class Logger {

    private static final boolean BEBUG = true;
    private static final String TAG = "zhangdi";

    public static void e(String msg) {

        if (BEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void d(String msg) {

        if (BEBUG) {
            Log.d(TAG, msg);
        }
    }
}
