package com.example.liangge.indiana.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.ResponseLogEntity;
import com.example.liangge.indiana.model.user.ResponseSignInEntity;
import com.example.liangge.indiana.ui.BaseActivity2;

/**
 * 登录/注册Activity
 */
public class LogSignInActivity extends BaseActivity2 {

    private static final String TAG = LogSignInActivity.class.getSimpleName();

    /** 登录的 View Wrapper */
    private View mViewLogWrapper;

    /** 注册的 View Wrapper */
    private View mViewSignInWrapper;

    private ImageButton mBtnSwitch;

    /** 一键注册 */
    private Button mBtnSignIn;


    //注册
    /** 电话号码 */
    private EditText mEdtPhoneNumber;

    private EditText mEdtPassword1;

    private EditText mEdtPassword2;

    /** 登录手机号*/
    private EditText mEdtLogPhoneNumber;
    /** 登录密码 */
    private EditText mEdtLogPassword;

    /** 手机验证码 */
    private EditText mEdtVerticationCode;


    private CheckBox mCheckBoxReadProcotal;



    private LogSignInBiz mLogSignInBiz;

    private PersonalCenterBiz mPersonalCenterBiz;



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
        initManager();
    }

    private void initManager() {
        mLogSignInBiz = LogSignInBiz.getInstance(this);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(this);
    }


    private void initView() {
        mViewLogWrapper = findViewById(R.id.activity_logsignin_log_wrapper);
        mViewSignInWrapper = findViewById(R.id.activity_logsingin_signin_wrapper);

        mBtnSwitch = (ImageButton) findViewById(R.id.activity_logsignin_btn_switch);

        mEdtPhoneNumber = (EditText) findViewById(R.id.edt_phone_number);
        mEdtPassword1 = (EditText) findViewById(R.id.edt_password1);
        mEdtPassword2 = (EditText) findViewById(R.id.edt_password2);
        mEdtVerticationCode = (EditText) findViewById(R.id.edt_vertication_code);
        mCheckBoxReadProcotal = (CheckBox) findViewById(R.id.activity_logsignin_checkbox_readprocotol);

        mEdtLogPhoneNumber = (EditText) findViewById(R.id.edt_log_phone_number);
        mEdtLogPassword = (EditText) findViewById(R.id.edt_log_password);

        mBtnSignIn = (Button) findViewById(R.id.btn_signin);

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
        String phoneNumber = mEdtLogPhoneNumber.getText().toString();
        String password = mEdtLogPassword.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)) {
            String hint = getResources().getString(R.string.activity_logsignin_log_not_complete);
            LogUtils.toastShortMsg(this, hint);

        } else {
            mLogSignInBiz.log(phoneNumber, password);

        }

    }

    //重发验证码
    public void onBtnRetryVeticationCode(View view) {
        String phoneNumebr = mEdtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumebr)) {
            String hint = getResources().getString(R.string.activity_logsignin_sign_vertication_code_hint1);
            LogUtils.toastShortMsg(this, hint);

        } else {
            mLogSignInBiz.onSendVeticationCode();

        }


    }

    //是否阅读夺宝协议
    public void onCheckBoxAgressProcotol(View view) {

    }

    //一键注册
    public void onBtnSignIn(View view) {
        if (mCheckBoxReadProcotal.isChecked()) {
            String phoneNumber = mEdtPhoneNumber.getText().toString();
            String password1 = mEdtPassword1.getText().toString();
            String password2 = mEdtPassword2.getText().toString();
            String verticatonCode = mEdtVerticationCode.getText().toString();

            if (password1.equals(password2)) {
                mLogSignInBiz.onSignIn(phoneNumber,password1, verticatonCode);

            } else {
                String hintMsg = getResources().getString(R.string.activity_logsignin_singin_password_not_same);
                LogUtils.toastShortMsg(this, hintMsg);

            }


        } else {
            String hintMsg = getResources().getString(R.string.activity_logsignin_signin_read_protocal_hint);
            LogUtils.toastShortMsg(this, hintMsg);

        }


    }

    //夺宝协议链接
    public void onTextClickProcotol(View view) {

    }


    @Override
    protected void handleUIMessage(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_START)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_FAILED)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_SUCCESS) ) {

            handleUISignIn(strUIAction);

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_START)
                        || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_FAILED)
                        || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_SUCCESS) ) {

            handleUILog(strUIAction);

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_START)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_FAILED)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_SUCCESS)) {

            handleUIVerticationCode(strUIAction);

        }



    }

    //验证码
    private void handleUIVerticationCode(String strUIAction) {
        if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_START)) {
            LogUtils.toastShortMsg(this, "开始发送验证码");

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_FAILED)) {
            LogUtils.toastShortMsg(this, "网络错误");

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_SUCCESS)) {
            mEdtVerticationCode.setText(mLogSignInBiz.getVeticationCode());
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

    //注册
    private void handleUISignIn(String strUIAction) {
        LogUtils.i(TAG, "handleUISignIn()");
        if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_START)) {
            LogUtils.toastShortMsg(this, "注册中");

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_FAILED)) {
            LogUtils.toastShortMsg(this, "网络错误");

        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_SUCCESS)) {
            handleUISignInCode();

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
            finish();
        }


    }

    /**
     * 处理注册的相关情况
     */
    private void handleUISignInCode() {
        ResponseSignInEntity item = mLogSignInBiz.getResponseSignInEntity();
        int iStatus = item.getStatus();
        if (iStatus == Constant.LogSignIn.SIGNIN_HAVE_BEEN_SIGNIN) {
            LogUtils.toastShortMsg(this, item.getMsg() );

        } else if (iStatus == Constant.LogSignIn.SIGNIN_VERTIATIONCODE_ERROR) {
            LogUtils.toastShortMsg(this, item.getMsg());

        } else if (iStatus == Constant.LogSignIn.SIGNIN_SUCCESS) {
            LogUtils.toastShortMsg(this, item.getMsg());
            LogUtils.e(TAG, "注册成功");
            onBtnLogSigninSwitch(null);
        }

    }


    @Override
    protected String getDebugTag() {
        return TAG;
    }



}
