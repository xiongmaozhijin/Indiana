package com.example.liangge.indiana.comm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by baoxing on 2015/12/8.
 */
public class SharedPrefUtils {

    private static final String PREF_CONFIG = "config";

    private static SharedPreferences mSharedPreferences;

    private static SharedPrefUtils mInstance;

    private SharedPrefUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_CONFIG, Context.MODE_PRIVATE);
    }


    public static synchronized void init(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefUtils(context);
        }

    }

    public synchronized static void save(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public synchronized static void save(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public synchronized static void save(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).commit();
    }


    public static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

}
