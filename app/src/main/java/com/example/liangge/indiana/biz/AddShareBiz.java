package com.example.liangge.indiana.biz;

import android.content.Context;
import android.content.Intent;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.example.liangge.indiana.ui.AddShareActivity;

/**
 * 晒单Biz
 * Created by baoxing on 2016/1/19.
 */
public class AddShareBiz extends BaseActivityBiz{

    private static final String TAG = AddShareBiz.class.getSimpleName();

    private static Context mContext;

    private static AddShareBiz mInstance;

    private AddShareBiz(Context context) {
        mContext = context;
    }


    public static AddShareBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AddShareBiz(context);
        }

        return mInstance;
    }


    private static class DataInfo {
        public static BingoRecordEntity mBingoRecordEntity = new BingoRecordEntity();
    }


    public BingoRecordEntity getBingoItem() {
        return DataInfo.mBingoRecordEntity;
    }

    /**
     * 提交晒单
     */
    public void onBtnSubmit() {
        LogUtils.i(TAG, "onBtnSubmit()");
    }


    public void startActivity(Context context, BingoRecordEntity item) {
        LogUtils.i(TAG, "startActivity()");
        DataInfo.mBingoRecordEntity = item;
        Intent intent = new Intent(context, AddShareActivity.class);
        context.startActivity(intent);
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
        mContext = null;
    }
}
