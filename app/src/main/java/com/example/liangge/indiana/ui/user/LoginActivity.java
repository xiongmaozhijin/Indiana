package com.example.liangge.indiana.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.ResponseLogEntity;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;

/**
 * 登录界面
 */
public class LogInActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = LogInActivity.class.getSimpleName();
    /** 登录手机号*/
    private EditText mEdtLogPhoneNumber;

    /** 登录密码 */
    private EditText mEdtLogPassword;


    private LogSignInBiz mLogSignInBiz;

    private PersonalCenterBiz mPersonalCenterBiz;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initManager();

    }

    private void initView() {
        mEdtLogPhoneNumber = (EditText) findViewById(R.id.edt_phone_number);
        mEdtLogPassword = (EditText) findViewById(R.id.edt_log_password);
    }

    private void initManager() {
        mLogSignInBiz = LogSignInBiz.getInstance(this);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
    }


    /**
     * 忘记密码
     * @param view
     */
    public void onBtnForgetPassword(View view) {
        LogUtils.w(TAG,"onBtnForgetPassword()");

    }

    /**
     * 登录
     * @param view
     */
    public void onBtnLogIn(View view) {
        String phoneNumber = mEdtLogPhoneNumber.getText().toString();
        String password = mEdtLogPassword.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)) {
            String hint = getResources().getString(R.string.activity_logsignin_log_not_complete);
            LogUtils.toastShortMsg(this, hint);

        } else {
            mLogSignInBiz.log(phoneNumber, password);

        }

    }

    /**
     * 通过手机号注册
     * @param view
     */
    public void onBtnSignInPhone(View view) {
        LogUtils.i(TAG, "onBtnSignInPhone()");
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }


    public void onBtnBack(View view) {
        finish();
    }



    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_START)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_FAILED)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_SUCCESS) ) {


        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_START)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_FAILED)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_SUCCESS) ) {

            handleUILog(strUIAction);

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_START)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_FAILED)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_SUCCESS)) {


        }



    }

    //登录
    private void handleUILog(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_START)) {
            LogUtils.toastShortMsg(this, "登录中");

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_FAILED)) {
            LogUtils.toastShortMsg(this, "网络错误");

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_SUCCESS)) {
            handleUILogCode();

        }

    }


    /**
     * 处理登录的相关情况
     */
    private void handleUILogCode() {
        ResponseLogEntity item = mLogSignInBiz.getResponseLogEntity();
        int iStatus = item.getStatus();

        if (iStatus == Constant.LogSignIn.LOG_FAILED) {
            LogUtils.e(TAG, item.getMsg());
            LogUtils.toastShortMsg(this, item.getMsg());

        } else if (iStatus == Constant.LogSignIn.LOG_SUCCESS) {
            LogUtils.e(TAG, "登录成功");
            mPersonalCenterBiz.logIn();
            mPersonalCenterBiz.onEnter();
            finish();
        }


    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }


}
