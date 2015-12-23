package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.liangge.indiana.R;

/**
 * 登录/注册Activity
 */
public class LogSignInActivity extends Activity {

    /** 登录的 View Wrapper */
    private View mViewLogWrapper;

    /** 注册的 View Wrapper */
    private View mViewSignInWrapper;

    /** 登录界面 */
    private static final int I_LOGIN = 1;

    /** 注册界面 */
    private static final int I_SIGNIN = 2;

    private int iCurState = I_LOGIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_in);

        initView();

    }

    private void initView() {
        mViewLogWrapper = findViewById(R.id.activity_logsignin_log_wrapper);
        mViewSignInWrapper = findViewById(R.id.activity_logsingin_signin_wrapper);

    }

    //返回键
    public void onBtnBack(View view) {
        finish();
    }

    //登录注册切换
    public void onBtnLogSigninSwitch(View view) {
        if (iCurState == I_LOGIN) {
            iCurState = I_SIGNIN;
            mViewLogWrapper.setVisibility(View.GONE);
            mViewSignInWrapper.setVisibility(View.VISIBLE);

        }  else {
            iCurState = I_LOGIN;
            mViewLogWrapper.setVisibility(View.VISIBLE);
            mViewSignInWrapper.setVisibility(View.GONE);

        }

    }

    //忘记密码
    public void onBtnForgetPassword(View view) {

    }

    //登录
    public void onBtnLogIn(View view) {

    }

    //重发验证码
    public void onBtnRetryVeticationCode(View view) {

    }

    //是否阅读夺宝协议
    public void onCheckBoxAgressProcotol(View view) {

    }

    //一键注册
    public void onBtnSignIn(View view) {

    }

    //夺宝协议链接
    public void onTextClickProcotol(View view) {

    }





}
