package com.example.liangge.indiana.ui;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by baoxing on 2015/12/14.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoaderConf();

    }

    /**
     * 初始化ImageLoader组件
     */
    private void initImageLoaderConf() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(configuration);

    }





}
