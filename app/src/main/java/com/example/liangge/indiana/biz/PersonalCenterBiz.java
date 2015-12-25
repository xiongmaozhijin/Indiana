package com.example.liangge.indiana.biz;

import android.content.Context;

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


    private static class DataInfo {
        /** 账户金额 */
        public static int accountGold;

        /** 用户ID */
        public static long userID;
    }


    /**
     * 获取账号的金额
     * @return
     */
    public int getAccountGold() {
        return DataInfo.accountGold;
    }


    /**
     * 获取用户ID
     * @return
     */
    public long getUserID() {
        return DataInfo.userID;
    }

    /**
     * 用户是否登录了
     * @return
     */
    public synchronized boolean isLogin() {
//        return SharedPrefUtils.getSharedPreferences().getBoolean(Constant.SharedPerfer.KEY_IS_LOGIN, false);
        return true;
    }

    /**
     * 初始化登录信息
     */
    public void initLogInInfo() {

    }
















}
