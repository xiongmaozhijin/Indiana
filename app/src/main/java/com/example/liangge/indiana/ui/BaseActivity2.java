package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;

public abstract class BaseActivity2 extends Activity {

    private static String TAG = "undefine";

    /** 消息接收广播 */
    private UIMessageReceive mUIMessageReceive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initResource();
    }

    private void initResource() {
        TAG = getDebugTag();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerUIReceive();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterUIReceive();

    }


    private class UIMessageReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String intentAction = intent.getAction();
                if (intentAction!=null && intentAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                    String uiAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    LogUtils.w(TAG, "uiAction=%s",uiAction);

                    if (uiAction != null) {
                        handleUIMessage(uiAction);
                    }
                }
            }
        }

    }



    protected void registerUIReceive() {
        if (mUIMessageReceive == null) {
            mUIMessageReceive = new UIMessageReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            registerReceiver(mUIMessageReceive, filter);
        }
    }

    protected void unRegisterUIReceive() {
        if (mUIMessageReceive != null) {
            unregisterReceiver(mUIMessageReceive);
            mUIMessageReceive = null;
        }
    }


    /**
     * 处理UI消息接收处理
     * @param strUIAction
     */
    protected abstract void handleUIMessage(String strUIAction);

    protected abstract String getDebugTag();
}
