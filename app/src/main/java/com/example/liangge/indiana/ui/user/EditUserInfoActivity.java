package com.example.liangge.indiana.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.model.user.UserInfoEntity;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 编辑个人信息
 */
public class EditUserInfoActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = EditUserInfoActivity.class.getSimpleName();

    private TextView mTxvId;

    private ImageView mImgUserPortrait;

    private EditText mEdtUsername;

    private EditText mEdtPhone;

    private EditText mEdtGoodName;
    private EditText mEdtGoodPhone;
    private EditText mEdtGoodAddress;


    /** 收货地址Wrapper */
    private View mViewAddressInnerWrapper;
    private View mViewSplit;

    private ImageView mViewArrow;

    private PersonalCenterBiz mPersonalCenterBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        initView();
        initManager();
    }

    private void initManager() {
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
    }

    private void initView() {
        mTxvId = (TextView) findViewById(R.id.personal_info_id);
        mImgUserPortrait = (ImageView) findViewById(R.id.user_info_portrait);
        mEdtUsername = (EditText) findViewById(R.id.personal_info_username);
        mEdtPhone = (EditText) findViewById(R.id.personal_info_phone);

        mViewAddressInnerWrapper = findViewById(R.id.edit_address_inner_wrapper);
        mViewSplit = findViewById(R.id.line_temp1);

        mEdtGoodName = (EditText) findViewById(R.id.personal_info_good_name);
        mEdtGoodPhone = (EditText) findViewById(R.id.personal_info_good_phone);
        mEdtGoodAddress = (EditText) findViewById(R.id.personal_info_good_address);

        mViewArrow = (ImageView) findViewById(R.id.edit_address_arrow);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mPersonalCenterBiz.isLogin()) {
            initUserInfo();
        } else {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        if (mPersonalCenterBiz.isLogin()) {
            UserInfoEntity user = mPersonalCenterBiz.getUserInfo();
            mTxvId.setText(user.getId()+"");
            ImageLoader.getInstance().displayImage(user.getPhoto(), mImgUserPortrait, getUserPortraitImageConfig() );
            mEdtUsername.setText(user.getNickname());
            mEdtPhone.setText(user.getPhone_number());
            mEdtGoodName.setText(user.getNickname());
            mEdtGoodPhone.setText(user.getPhone_number());
            mEdtGoodAddress.setText(user.getHumanReadableAddress1(this));
        }
    }

    /**
     * 点击了地址管理
     * @param view
     */
    public void onBtnEditAddress(View view) {
        LogUtils.w(TAG, "onBtnEditAddress()");
        Boolean isExpand = view.getTag()==null ? false : (Boolean) view.getTag();
        LogUtils.w(TAG, "isExpand=%b", isExpand);

        if (isExpand) {
            mViewAddressInnerWrapper.setVisibility(View.GONE);
            mViewSplit.setVisibility(View.INVISIBLE);
            mViewArrow.setImageResource(R.drawable.ic_more);

        } else {
            mViewAddressInnerWrapper.setVisibility(View.VISIBLE);
            mViewSplit.setVisibility(View.VISIBLE);
            mViewArrow.setImageResource(R.drawable.ic_more_down);

        }

        isExpand = !isExpand;
        view.setTag(isExpand);
    }

    public void onBtnEditPortrait(View view) {
        LogUtils.i(TAG, "编辑头像信息");

    }


    /**
     * 保存个人信息
     * @param view
     */
    public void onBtnSave(View view) {

    }


    public void onBtnBack(View view) {
        finish();
    }


    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }
}
