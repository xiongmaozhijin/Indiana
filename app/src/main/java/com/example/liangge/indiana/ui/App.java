package com.example.liangge.indiana.ui;

import android.app.Application;

import com.example.liangge.indiana.comm.LocalDisplay;
import com.example.liangge.indiana.comm.SharedPrefUtils;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by baoxing on 2015/12/14.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initRes();
    }

    private void initRes() {
        initImageLoaderConf();
        initSharedPref();
        initVolley();
        initOther();
    }

    private void initOther() {
        LocalDisplay.init(this);
    }

    private void initVolley() {
        VolleyBiz.init(getApplicationContext());
    }

    private void initSharedPref() {
        SharedPrefUtils.init(getApplicationContext());
    }

    /**
     * 初始化ImageLoader组件
     */
    private void initImageLoaderConf() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(configuration);

    }





}
