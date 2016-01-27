package com.example.liangge.indiana.comm;

import android.os.Build;
import android.util.Log;

/**
 * Created by baoxing on 2015/12/9.
 */
public class PhoneUtils {


    private static final String TAG = PhoneUtils.class.getSimpleName();

    /**
     * 获取手机的相关信息
     * @return
     */
    public static String getPhoneInfo() {
        Log.e(TAG, "getPhoneInfo()");
        StringBuilder sbPhoneInfo = new StringBuilder("\n---phone info---\n");
        sbPhoneInfo.append("Product:" + Build.PRODUCT + "\n");
        sbPhoneInfo.append("CPU_ABI:" + Build.CPU_ABI + "\n");
        sbPhoneInfo.append("TAGS:" + Build.TAGS + "\n");
        sbPhoneInfo.append("VERSION_CODES.BASE" + Build.VERSION_CODES.BASE + "\n");
        sbPhoneInfo.append("MODEL:" + Build.MODEL + "\n");
        sbPhoneInfo.append("SDK:" + Build.VERSION.SDK_INT + "\n");
        sbPhoneInfo.append("VERSION.RELEASE:" + Build.VERSION.RELEASE + "\n");
        sbPhoneInfo.append("DEVIC:" + Build.DEVICE + "\n");
        sbPhoneInfo.append("DISPLAY:" + Build.DISPLAY + "\n");
        sbPhoneInfo.append("BRAND:" + Build.BRAND + "\n");
        sbPhoneInfo.append("BOARD:" + Build.BOARD + "\n");
        sbPhoneInfo.append("FINGERPRINT:" + Build.FINGERPRINT + "\n");
        sbPhoneInfo.append("ID:" + Build.ID + "\n");
        sbPhoneInfo.append("MANUFACTURER:" + Build.MANUFACTURER + "\n");
        sbPhoneInfo.append("USER:" + Build.USER + "\n");

        Log.e(TAG, sbPhoneInfo.toString());

        return sbPhoneInfo.toString();
    }
}
