package com.example.liangge.indiana.comm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * log工具类
 * Created by baoxing on 2015/12/2.
 */
public class LogUtils {


    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void i(String tag, String format, Object...args) {
        Log.i(tag, String.format(format, args));
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, String format, Object...args) {
        Log.e(tag, String.format(format, args));
    }

    public static void toastShortMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }



}
