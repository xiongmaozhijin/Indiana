package com.example.liangge.indiana.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.ImageViewBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * temp,...
 */
public class ImageViewActivity extends BaseActivity2 {

    private static final String TAG = ImageViewActivity.class.getSimpleName();

    private ImageView mImageView;

    private DisplayImageOptions mDisplayImageOptions;

    private ImageViewBiz mImageViewBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        initView();
        initManager();
        initRes();
    }

    private void initManager() {
        mImageViewBiz = ImageViewBiz.getInstance(this);

    }

    private void initRes() {
        initImageLoaderConf();

    }


    private void initImageLoaderConf() {
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.main_banner_img_load_empty_uri)
                .showImageOnFail(R.drawable.main_banner_img_load_fail)
                .showImageOnLoading(R.drawable.main_product_item_img_onloading)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
    }


    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume()");
        ImageLoader.getInstance().displayImage(mImageViewBiz.getDisplayImageView(), mImageView, mDisplayImageOptions);

    }

    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imgview);
    }





}
