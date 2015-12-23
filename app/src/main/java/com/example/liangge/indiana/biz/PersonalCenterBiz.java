package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.SharedPrefUtils;

/**
 * Created by baoxing on 2015/12/23.
 */
public class PersonalCenterBiz {

    private static PersonalCenterBiz mInstance;

    private static Context mContext;


    private PersonalCenterBiz(Context context) {
        this.mContext = context;
    }


    public static PersonalCenterBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PersonalCenterBiz(context);
        }

        return mInstance;
    }

    /**
     * 用户是否登录了
     * @return
     */
    public synchronized boolean isLogin() {
        return SharedPrefUtils.getSharedPreferences().getBoolean(Constant.SharedPerfer.KEY_IS_LOGIN, false);
    }

    /**
     * 初始化登录信息
     */
    public void initLogInInfo() {

    }
















}
