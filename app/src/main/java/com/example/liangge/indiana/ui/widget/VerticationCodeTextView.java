package com.example.liangge.indiana.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.LogUtils;

/**
 * 限时不可发验证码控件
 * Created by baoxing on 2016/1/12.
 */
public class VerticationCodeTextView extends TextView {

    private static final String TAG = VerticationCodeTextView.class.getSimpleName();

    private static final int I_LIMIT_TIME = 7;

    private boolean mIsCountDowning = false;

    /** 限时时间 */
    private int mLimitTime = I_LIMIT_TIME;    //30s

    public VerticationCodeTextView(Context context) {
        this(context, null);
    }

    public VerticationCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }



    public VerticationCodeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {

    }

    /**
     * 开始验证码显示
     */
    public void startLimitSendVerticationCode() {
        LogUtils.i(TAG, "startSendVerticatonCode()");
        mLimitTime = I_LIMIT_TIME;
        countDown();
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!mIsCountDowning) {
            super.setEnabled(enabled);

        }

    }

    /**
     * 倒计时
     */
    private void countDown() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLimitTime <= 0) {
                    mIsCountDowning = false;
                    setEnabled(true);
                    String hint = getResources().getString(R.string.activity_obtain_verticationcoade);
                    setText(hint);

                } else {
                    setEnabled(false);
                    setText(mLimitTime+"");
                    mLimitTime--;
                    countDown();
                    mIsCountDowning = true;
                }
            }
        }, 1000);
    }






}
