package com.example.liangge.indiana.biz;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.SharedPrefUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.UserInfoEntity;

/**
 * Created by baoxing on 2015/12/23.
 */
public class PersonalCenterBiz extends BaseFragmentBiz{

    private static final String TAG = PersonalCenterBiz.class.getSimpleName();

    private static PersonalCenterBiz mInstance;

    private static Context mContext;


    private static LogSignInBiz mLogSignInBiz;


    private MessageManager mMessageManager;

    private PersonalCenterBiz(Context context) {
        this.mContext = context;
        initManager();
    }

    private void initManager() {
        mLogSignInBiz = LogSignInBiz.getInstance(mContext);
        mMessageManager = MessageManager.getInstance(mContext);
    }


    public static PersonalCenterBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PersonalCenterBiz(context);
        }

        return mInstance;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onFirstEnter() {

    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }

    public void onDestory() {
        logOut();
    }


    private static class DataInfo {

        public static UserInfoEntity userInfo = new UserInfoEntity();

    }


    public UserInfoEntity getUserInfo() {
        return DataInfo.userInfo;
    }

    /**
     * 用户是否登录了
     * @return
     */
    public synchronized boolean isLogin() {
        return SharedPrefUtils.getSharedPreferences().getBoolean(Constant.SharedPerfer.KEY_IS_LOGIN, false);
//        return false;
    }

    /**
     * 用户登录()
     */
    public void logIn() {
        _setLogin(true);
        DataInfo.userInfo = Bizdto.changeToUserInfoEntity(mLogSignInBiz.getResponseLogEntity());
    }

    /**
     * 退出登录
     */
    public void logOut() {
        LogUtils.w(TAG, "logOut()");
        //TODO 改到 LogSignInBiz ？？
        _setLogin(false);
        DataInfo.userInfo = new UserInfoEntity();

        mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.PersonalCenterMessage.M_LOGOUT_SUCCESS));
    }


    private synchronized void _setLogin(boolean isLogin) {
        SharedPrefUtils.save(Constant.SharedPerfer.KEY_IS_LOGIN, isLogin);
    }


    /**
     * 初始化登录信息
     */
    public void initLogInInfo() {

    }



    private class SlaveLogOutThread extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveLogOutThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
        }

        @Override
        protected String getJsonBody() {
            return null;

        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "SignOut:onResponse=%s", s);
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "SignOut:volleyError", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return null;
        }
    }












}
