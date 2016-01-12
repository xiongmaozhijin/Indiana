package com.example.liangge.indiana.comm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by baoxing on 2015/12/15.
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * 网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public static String getToken2(long time, long userId, String token) {
        StringBuilder sb = new StringBuilder(token);
        String tempToken = token;
        String strTime = String.valueOf(time);
        String s1 = strTime.substring(strTime.length() - 3, strTime.length());
        long A = Integer.parseInt(s1) * userId;
        int index = (int) (A % 29);
        sb.insert(index, tempToken);
        String token2 = DigestUtils.sha1(sb.toString()).toString();

        LogUtils.w(TAG, "time=%d, userId=%d, token=%s, A=%d, index=%d, token2=%s", time, userId, token, A, index, token2);
        return token2;
    }

}
