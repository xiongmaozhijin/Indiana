package com.example.liangge.indiana.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.user.UserInfoEntity;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人资料
 */
public class UserInfoActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = UserInfoActivity.class.getSimpleName();

    private PersonalCenterBiz mPersonalCenterBiz;

    private TextView mTxvId;

    private TextView mTxvUsername;

    private TextView mTxvPhone;

    private ImageView mImgUserPortrait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initManager();
        initRes();
    }

    private void initRes() {
    }

    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }

    private void initView() {
        mTxvId = (TextView) findViewById(R.id.personal_info_id);
        mTxvUsername = (TextView) findViewById(R.id.personal_info_name);
        mTxvPhone = (TextView) findViewById(R.id.personal_info_phone);
        mImgUserPortrait = (ImageView) findViewById(R.id.user_info_portrait);

    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mPersonalCenterBiz.isLogin()) {
            initUserInfo();

        } else {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * 初始化个人信息
     */
    private void initUserInfo() {
        if (mPersonalCenterBiz.isLogin()) {
            UserInfoEntity user = mPersonalCenterBiz.getUserInfo();
            LogUtils.w(TAG, "user=%s", user.toString());

            mTxvId.setText(user.getId() + "");
            mTxvUsername.setText(user.getNickname());
            mTxvPhone.setText(user.getPhone_number());
            ImageLoader.getInstance().displayImage(user.getPhoto(), mImgUserPortrait, getUserPortraitImageConfig());

        }

    }


    public void onBtnEditUserInfo(View view) {
        LogUtils.i(TAG, "onBtnEditUserInfo()");
        Intent intent = new Intent(this, EditUserInfoActivity.class);
        startActivity(intent);
    }



    public void onBtnBack(View view) {
        finish();
    }


}
