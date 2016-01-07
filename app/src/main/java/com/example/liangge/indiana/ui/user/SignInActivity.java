package com.example.liangge.indiana.ui.user;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.user.ResponseSignInEntity;
import com.example.liangge.indiana.ui.SimpleAdapterBaseActivity2;

/**
 * 注册界面
 */
public class SignInActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = SignInActivity.class.getSimpleName();

    /** 电话号码 */
    private EditText mEdtPhoneNumber;

    /** 名字，昵称 */
    private EditText mEdtUsername;

    private EditText mEdtPassword1;

    private EditText mEdtPassword2;

    /** 手机验证码 */
    private EditText mEdtVerticationCode;

    private CheckBox mCheckBoxReadProcotal;

    private View mViewObtainVertication;

    private LogSignInBiz mLogSignInBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initManager();

    }

    private void initView() {
        mEdtPhoneNumber = (EditText) findViewById(R.id.edt_phone_number);
        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtPassword1 = (EditText) findViewById(R.id.edt_password1);
        mEdtPassword2 = (EditText) findViewById(R.id.edt_password2);
        mEdtVerticationCode = (EditText) findViewById(R.id.edt_vertication_code);
        mCheckBoxReadProcotal = (CheckBox) findViewById(R.id.activity_logsignin_checkbox_readprocotol);
        mViewObtainVertication = findViewById(R.id.obtain_vertication_code);

        mEdtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneNumber = s.toString();
                if (isPhoneNumber(phoneNumber) ) {
                    mViewObtainVertication.setEnabled(true);

                } else {
                    mViewObtainVertication.setEnabled(false);

                }

            }

            private boolean isPhoneNumber(String phoneStr) {
                boolean r = false;
                if (phoneStr!= null && !TextUtils.isEmpty(phoneStr) && phoneStr.length() >= 3) {
                    r = true;

                }

                return r;
            }

        });

    }

    private void initManager() {
        mLogSignInBiz = LogSignInBiz.getInstance(this);

    }


    /**
     * 发送验证码
     * @param view
     */
    public void onBtnRetryVeticationCode(View view) {
        String phoneNumebr = mEdtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumebr)) {
            String hint = getResources().getString(R.string.activity_logsignin_sign_vertication_code_hint1);
            LogUtils.toastShortMsg(this, hint);

        } else {
            mLogSignInBiz.onSendVeticationCode();

        }
    }

    /** 是否阅读夺宝协议 */
    public void onCheckBoxAgressProcotol(View view) {

    }

    /** 一键注册 */
    public void onBtnSignIn(View view) {
        if (mCheckBoxReadProcotal.isChecked()) {
            String phoneNumber = mEdtPhoneNumber.getText().toString();
            String username = mEdtUsername.getText().toString();
            String password1 = mEdtPassword1.getText().toString();
            String password2 = mEdtPassword2.getText().toString();
            String verticatonCode = mEdtVerticationCode.getText().toString();

            if (!password1.equals(password2) ) {
                String hintMsg = getResources().getString(R.string.activity_logsignin_singin_password_not_same);
                LogUtils.toastShortMsg(this, hintMsg);

            } else if (TextUtils.isEmpty(username) ) {
                String hintMsg = getResources().getString(R.string.activity_logsignin_signin_username_not_empty);
                LogUtils.toastShortMsg(this, hintMsg);

            } else if (password1!=null && (password1.length()>=6 && password1.length()<=12) ) {
                String hintMsg = getResources().getString(R.string.activity_login_pwd_length_hint);
                LogUtils.toastShortMsg(this, hintMsg);

            } else {
                mLogSignInBiz.onSignIn(phoneNumber,username, password1, verticatonCode);
            }


        } else {
            String hintMsg = getResources().getString(R.string.activity_logsignin_signin_read_protocal_hint);
            LogUtils.toastShortMsg(this, hintMsg);

        }


    }

    /** 夺宝协议链接 */
    public void onTextClickProcotol(View view) {
        LogUtils.i(TAG, "onTextClickProcotol()");
    }


    public void onBtnBack(View view) {
        finish();
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


        } else if (strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_START)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_FAILED)
                || strUIAction.equals(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_SUCCESS)) {

            handleUIVerticationCode(strUIAction);

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
            finish();
        }

    }


    @Override
    protected String getDebugTag() {
        return TAG;
    }
}
