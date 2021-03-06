package com.example.liangge.indiana.ui.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.toolbox.Volley;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.AppBiz;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.WebViewBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.FileOperateUtils;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class SettingActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = SettingActivity.class.getSimpleName();

    private WebViewBiz mWebViewBiz;

    private PersonalCenterBiz mPersonalCenterBiz;

    private TextView mTxvCacheSize;

    private TextView mTxvVersionName;

    private SlaveGetCacheSizeThread mSlaveGetCacheSizeThread;

    private AppBiz mAppBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initManager();
        initRes();
        initState();
    }

    private void initState() {
        mTxvVersionName.setText(AppBiz.getCurVersionName(this) );

    }

    private void initRes() {
        mSlaveGetCacheSizeThread = new SlaveGetCacheSizeThread();
        mSlaveGetCacheSizeThread.start();
    }

    private void initView() {
        mTxvCacheSize = (TextView) findViewById(R.id.txv_cache_size);
        mTxvVersionName = (TextView) findViewById(R.id.txv_version_name);
    }

    private void initManager() {
        mWebViewBiz = WebViewBiz.getInstance(this);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
        mAppBiz = AppBiz.getInstance(this);
    }

    /**
     * 常见问题
     * @param view
     */
    public void onBtnCommQaq(View view) {
        LogUtils.i(TAG, "onBtnCommQaq()");
        String title = getResources().getString(R.string.setting_common_question);
        mWebViewBiz.setWebViewRes(title, Constant.WebServiceAPI.REQUEST_QAQ);
        mWebViewBiz.startActivity(this);
    }

    /**
     * 检查更新
     * @param view
     */
    public void onBtnCheckUpdate(View view) {
        LogUtils.i(TAG, "onBtnCheckUpdate()");
        String hint = getResources().getString(R.string.checking);
        LogUtils.toastShortMsg(this, hint);
        mAppBiz.checkAppUpdate();
    }

    /**
     * 关于一元夺宝
     * @param view
     */
    public void onBtnAboutIndiana(View view) {
        LogUtils.i(TAG, "onBtnAboutIndiana()");
        String hint = getResources().getString(R.string.setting_about_indiana);
        mWebViewBiz.setWebViewRes(hint, Constant.WebServiceAPI.REQUEST_ABOUT_INDAINA);
        mWebViewBiz.startActivity(this);
    }

    /**
     * 清除缓存
     * @param view
     */
    public void onBtnClearCache(View view) {
        LogUtils.i(TAG, "onBtnClearCache()");
        new SlaveClearCacheThread().start();

    }


    /**
     * 退出登录
     * @param view
     */
    public void onBtnExitLog(View view) {
        LogUtils.i(TAG, "onBtnExitLog()");
        mPersonalCenterBiz.logOut();
    }

    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.Comm_Activity.COMM_CHECK_APP_UPDATE_SUCCESS)) {
            handleUIAppUpdate();
        }
    }

    /**
     * app更新
     */
    private void handleUIAppUpdate() {
        LogUtils.i(TAG, "handleUIAppUpdate()");
        if (mAppBiz.isShouldUpdate()) {
            final Dialog dialog = mAppBiz.showUpdateDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAppBiz.downloadApp();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            String hint = getResources().getString(R.string.update_hint);
            LogUtils.toastShortMsg(this, hint);

        }

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }


    /**
     * 获取缓存大小
     */
    private class SlaveGetCacheSizeThread extends Thread {

        @Override
        public void run() {
            super.run();
            File uilCacheDir = ImageLoader.getInstance().getDiskCache().getDirectory();
            File volleyCacheDir = VolleyBiz.getInstance().getCacheFileDir();
            long size1 = FileOperateUtils.getFolderSize(uilCacheDir);
            long size2 = FileOperateUtils.getFolderSize(volleyCacheDir);
            LogUtils.i(TAG, "size1=%d, size2=%d", size1, size2);
            final String cacheSizeStr = FileOperateUtils.getFormatSize(size1 + size2);
            Context context = SettingActivity.this;
            if (context != null) {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mTxvCacheSize!=null) {
                            mTxvCacheSize.setText(cacheSizeStr);
                        }

                    }
                });
            }
        }


    }

    /**
     * 清除缓存
     */
    private class SlaveClearCacheThread extends Thread {

        @Override
        public void run() {
            super.run();
            ImageLoader.getInstance().getDiskCache().clear();
            VolleyBiz.getInstance().clearCache();
            mSlaveGetCacheSizeThread = new SlaveGetCacheSizeThread();
            mSlaveGetCacheSizeThread.start();
        }


    }



}
