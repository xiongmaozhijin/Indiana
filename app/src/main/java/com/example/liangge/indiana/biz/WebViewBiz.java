package com.example.liangge.indiana.biz;

import android.content.Context;

/**
 * Created by baoxing on 2015/12/24.
 */
public class WebViewBiz {

    private static WebViewBiz mInstance;

    private static Context mContext;

    private DataInfo mDataInfo;

    public class DataInfo {
        /** 标题 */
        public  String title;

        /** 打开的url */
        public  String url;


        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }


    private WebViewBiz(Context context) {
        this.mContext = context;
        mDataInfo = new DataInfo();
    }

    public synchronized static WebViewBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WebViewBiz(context);
        }

        return mInstance;
    }


    public DataInfo getDataInfo() {
        return this.mDataInfo;
    }

    public void setWebViewRes(String title, String url) {
        this.mDataInfo.title = title;
        this.mDataInfo.url = url;
    }


}
