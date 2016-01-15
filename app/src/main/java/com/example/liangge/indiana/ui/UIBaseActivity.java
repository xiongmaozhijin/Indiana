package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.SharedPrefUtils;
import com.example.liangge.indiana.comm.UIMessageConts;

/**
 * 仅可以用于HomeActivity
 */
public abstract class UIBaseActivity extends FragmentActivity {

    private PersonalCenterBiz mPersonalCenterBiz;

    private UIMegReceive mUIMegReceive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        registerUIReceive();
        initManager();
        initState();
    }

    /**
     * 初始化相关状态
     */
    private void initState() {
       mPersonalCenterBiz.initLoginFlag();

    }

    private void registerUIReceive() {
        if (mUIMegReceive == null) {
            mUIMegReceive = new UIMegReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            registerReceiver(mUIMegReceive, filter);
        }
    }


    private void unregisterUIReciver() {
        if (mUIMegReceive != null) {
            unregisterReceiver(mUIMegReceive);
            mUIMegReceive = null;
        }
    }


    //解决 getActivity() 返回 NULL 的问题
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);

    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
    }


    private class UIMegReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String intentAction = intent.getAction();
                if (intentAction!=null && intentAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                    String uiAction = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    if (uiAction != null) {
                        handleUIMessage(uiAction);
                    }
                }
            }
        }
    }

    protected abstract void handleUIMessage(String uiAction);



    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearRes();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exitApp();
    }

    private void clearRes() {
        unregisterUIReciver();
    }

    private static final long L_EXIT_APP_INTERVAL = 2000;
    private long mExitTime = 0;
    /**
     * 退出App
     */
    private void exitApp() {
        if ( (System.currentTimeMillis()-mExitTime) > L_EXIT_APP_INTERVAL)  {
            String exitHint = getResources().getString(R.string.app_exit_hint);
            mExitTime = System.currentTimeMillis();
            LogUtils.toastShortMsg(this, exitHint);
        } else {
            clearRes();
            super.onBackPressed();
            System.exit(0);
        }
    }





}
