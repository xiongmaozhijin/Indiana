package com.example.liangge.indiana.ui.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;

public class SettingActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = SettingActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    /**
     * 常见问题
     * @param view
     */
    public void onBtnCommQaq(View view) {
        LogUtils.i(TAG, "onBtnCommQaq()");
    }

    /**
     * 检查更新
     * @param view
     */
    public void onBtnCheckUpdate(View view) {
        LogUtils.i(TAG, "onBtnCheckUpdate()");
    }

    /**
     * 关于一元夺宝
     * @param view
     */
    public void onBtnAboutIndiana(View view) {
        LogUtils.i(TAG, "onBtnAboutIndiana()");
    }

    /**
     * 清除缓存
     * @param view
     */
    public void onBtnClearCache(View view) {
        LogUtils.i(TAG, "onBtnClearCache()");
    }

    /**
     * 退出登录
     * @param view
     */
    public void onBtnExitLog(View view) {
        LogUtils.i(TAG, "onBtnExitLog()");

    }

    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }
}
