package com.bluelable.bluelog.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by xinspace on 11/24/15.
 */
public class NetChangedReceiver extends BroadcastReceiver {

    /*
    * 常量
    * */
    //debug标签
    private static final String TAG = NetChangedReceiver.class.getSimpleName();
    //网络变化发送的action
    private static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    /*
    * 变量
    * */
    //Context
    private Context mContext;
    //网络状态监听器
    private NetworkStatusChanged mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context.getApplicationContext();
        if (ACTION.equals(intent.getAction())) {
            //是网络变化发送的action
            if(mListener != null) {
                mListener.networkStatus();
            }
        }
    }

    /**
     * @author xinspace
     * created at 01/12/16 16:10
     * 设置状态变化监听器
     */
    public void setNetworkStatusListener(NetworkStatusChanged listener) {
        mListener = listener;
    }

    /**
     * @author xinspace
     * created at 01/12/16 16:09
     * 网络变化接口
     */
    public interface NetworkStatusChanged {
        /**
         * @author xinspace
         * created at 01/12/16 16:09
         */
        void networkStatus();
    }
}
