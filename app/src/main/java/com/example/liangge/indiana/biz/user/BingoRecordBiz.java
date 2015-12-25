package com.example.liangge.indiana.biz.user;

import android.content.Context;

import com.example.liangge.indiana.biz.BaseActivityBiz;

/**
 * 中奖记录Biz
 * Created by baoxing on 2015/12/25.
 */
public class BingoRecordBiz extends BaseActivityBiz{


    private static Context mContext;

    private static BingoRecordBiz mInstance;

    private BingoRecordBiz(Context context) {
        this.mContext = context;
        initManager();
    }

    private void initManager() {

    }

    public static synchronized BingoRecordBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BingoRecordBiz(context);
        }

        return mInstance;
    }


    







    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
