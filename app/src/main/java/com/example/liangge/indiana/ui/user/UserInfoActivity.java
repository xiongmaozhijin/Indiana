package com.example.liangge.indiana.ui.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.ImageViewBiz;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.ui.BaseActivity2;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 个人信息
 */
public class UserInfoActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = UserInfoActivity.class.getSimpleName();

    /** 用户头像 */
    private ImageView mImgUserPortain;

    /** 用户信息 */
    private TextView mTxvUserInfo;

    private DisplayImageOptions mDisplayImageOptions;

    private PersonalCenterBiz mPersonalCenterBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initManager();
        initRes();
    }

    private void initRes() {
        initImageLoaderConf();
    }

    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }

    private void initView() {
        mImgUserPortain = (ImageView) findViewById(R.id.imgview_user_portain);
        mTxvUserInfo = (TextView) findViewById(R.id.txv_user_info);
    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mPersonalCenterBiz.isLogin()) {
            String userPortainUrl = mPersonalCenterBiz.getUserInfo().getPhoto();
            long userID = mPersonalCenterBiz.getUserInfo().getUserId();
            int balance = mPersonalCenterBiz.getUserInfo().getBalance();


        } else {
            Intent intent = new Intent(this, LogSignInActivity.class);
            startActivity(intent);
            finish();
        }

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



}
