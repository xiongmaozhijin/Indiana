package com.example.liangge.indiana.biz;

import android.content.Context;
import android.widget.ImageButton;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.ui.BaseActivity2;

/**
 * ImageView Biz
 * Created by baoxing on 2015/12/26.
 */
public class ImageViewBiz extends BaseActivityBiz{

    private static final String TAG = ImageViewBiz.class.getSimpleName();

    private static ImageViewBiz mInstance;

    private static Context mContext;

    private ImageViewBiz(Context context) {
        this.mContext = context;
    }


    public static synchronized ImageViewBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ImageViewBiz(context);

        }

        return mInstance;
    }


    private static class DataInfo {
        private static String imgUrl;

    }

    public String getDisplayImageView() {
        LogUtils.w(TAG, "getDisplayImageView(). imgUrl=%s", DataInfo.imgUrl);

        return DataInfo.imgUrl;
    }


    public void setDisplayImageView(String imgUrl) {
        DataInfo.imgUrl = imgUrl;
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
