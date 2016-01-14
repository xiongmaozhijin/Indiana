package com.example.liangge.indiana.comm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.liangge.indiana.R;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;

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

    /**
     * 计算返回认证的token2
     * @param time
     * @param userId
     * @param token
     * @return
     */
    public static String getToken2(long time, long userId, String token) {
        if (token == null) {
            token = "null";
        }
        StringBuilder sb = new StringBuilder(token);
        String tempToken = token;
        String strTime = String.valueOf(time);
        String s1 = strTime.substring(strTime.length() - 3, strTime.length());
        long A = Integer.parseInt(s1) * userId;
        int index = (int) (A % 29);
        sb.insert(index, String.valueOf(A));
        String token2 = new String(Hex.encodeHex(DigestUtils.sha(sb.toString()) ) );

        LogUtils.w(TAG, "time=%d, s1=%s, token=%s, token2=%s, index=%d, ss=%s, A=%d", time, s1, token, token2, index, sb.toString(), A);
        return token2;
    }


    public static String getRowStr(long time, long userId, String token) {
        if (token==null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(token);
        String tempToken = token;
        String strTime = String.valueOf(time);
        String s1 = strTime.substring(strTime.length() - 3, strTime.length());
        long A = Integer.parseInt(s1) * userId;
        int index = (int) (A % 29);
        sb.insert(index, A + "");

        return sb.toString();
    }

    /**
     * 返回自定义的web link地址
     * @param url
     * @return
     */
    public static String getFixWebLink(Context context, String url) {
        String linkUrl = "";
        final String schema1 = "http://";
        final String schema2 = "https://";
        if (url!=null) {
            if (url.startsWith(schema1)) {
                url = url.substring(schema1.length());

            } else if (url.startsWith(schema2)) {
                url = url.substring(schema2.length());

            }
            String schme = context.getResources().getString(R.string.custom_schema);
            linkUrl = schme + "://" + url;
        }

        return linkUrl;
    }






}
