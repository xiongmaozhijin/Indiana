package com.example.liangge.indiana.biz;

import android.content.Context;
import android.content.Intent;

import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.ui.WebViewActivity;

/**
 * Created by baoxing on 2015/12/26.
 */
public class BannerInfoBiz {


    private static BannerInfoBiz mInstance;

    private static WebViewBiz mWebViewBiz;

    private static Context mContext;


    private interface _Contants {
        public static final int DETAIL = 0;
        public static final int LINK = 1;
        public static final int SEARCH = 2;

    }


    private BannerInfoBiz(Context context) {
        this.mContext = context;
        initManager();
        initRes();
    }

    private void initRes() {

    }

    private void initManager() {
        mWebViewBiz = WebViewBiz.getInstance(mContext);
    }


    public static BannerInfoBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BannerInfoBiz(context);
        }

        return mInstance;
    }




    public void onItemClick(BannerInfo item) {
        int iAction = item.getAction();
        if (iAction == _Contants.LINK) {
            mWebViewBiz.setWebViewRes(item.getTitle(), item.getLinkUrl());
            Intent intent = new Intent(mContext, WebViewActivity.class);
            mContext.startActivity(intent);
        }


    }






}
