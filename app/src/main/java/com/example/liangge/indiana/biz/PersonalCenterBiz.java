package com.example.liangge.indiana.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.user.LogSignInBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.NetworkUtils;
import com.example.liangge.indiana.comm.SharedPrefUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.ResponseUserInfoEntitiy;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.UserInfoEntity;
import com.google.gson.Gson;

/**
 * Created by baoxing on 2015/12/23.
 */
public class PersonalCenterBiz extends BaseFragmentBiz{

    private static final String TAG = PersonalCenterBiz.class.getSimpleName();

    private static PersonalCenterBiz mInstance;

    private static Context mContext;


    private static LogSignInBiz mLogSignInBiz;


    private SlaveRequestUserInfo mSlaveRequestUserInfo;

    private MessageManager mMessageManager;

    private PersonalCenterBiz(Context context) {
        this.mContext = context;
        initManager();
        initRes();
    }

    private void initRes() {
        mSlaveRequestUserInfo = new SlaveRequestUserInfo();
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

    private static final int HANDLE_ACTION_REQUEST_USER_INFO = -100;
    private static final int HANDLE_ACTION_LOGOUT = -99;


    private static Handler mHandle = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int action = msg.arg1;

            if (action== HANDLE_ACTION_REQUEST_USER_INFO) {
                int status = msg.arg2;
                if (status !=  200) {
                    String hint = (String) msg.obj;
                    LogUtils.toastShortMsg(mContext, hint);
                }

            } else if (action==HANDLE_ACTION_LOGOUT) {
                String exitHint = mContext.getResources().getString(R.string.setting_exitlog_hint);
                LogUtils.toastShortMsg(mContext, exitHint);

            }


        }

    };



    private static class DataInfo {

        public static UserInfoEntity userInfo = new UserInfoEntity();

    }


    private static class ResponseInfo {

    }

    private static class RequestInfo {

    }


    /**
     * 请求个人信息
     */
    public void updateUserInfo() {
        LogUtils.i(TAG, "updateUserInfo()");
        mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.PersonalCenterMessage.PERSONALCENTER_M_UPDATE_USER_INFO));
        //网络请求更新信息 TODO
        if (isLogin()) {
            if (!mSlaveRequestUserInfo.isWorking()) {
                mSlaveRequestUserInfo.cancelAll();
                mSlaveRequestUserInfo = new SlaveRequestUserInfo();
                mSlaveRequestUserInfo.start();
            }
        }
    }


    /**
     *
     * @param isAlreadyEnter 是否已经进入过
     */
    public void onResume(boolean isAlreadyEnter) {
        if (isAlreadyEnter) {
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.PersonalCenterMessage.PERSONALCENTER_M_UPDATE_USER_INFO));
        }
    }


    @Override
    public void onViewCreated() {

    }

    @Override
    public void onFirstEnter() {
        mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.PersonalCenterMessage.PERSONALCENTER_M_UPDATE_USER_INFO));
        updateUserInfo();
    }

    @Override
    public void onEnter() {
        updateUserInfo();
    }

    @Override
    public void onLeave() {

    }

    public void onDestory() {
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
     * 初始化登录标志. token/userId
     */
    public synchronized void initLoginFlag() {
        if (isLogin() ) {
            String token = SharedPrefUtils.getSharedPreferences().getString(Constant.SharedPerfer.KEY_TOKEN, "");
            long userID = SharedPrefUtils.getSharedPreferences().getLong(Constant.SharedPerfer.KEY_USERID, -1);
            getUserInfo().setToken(token);
            getUserInfo().setId(userID);
            updateUserInfo();
        }
    }

    /**
     * 用户登录()
     */
    public void logIn() {
        DataInfo.userInfo = Bizdto.changeToUserInfoEntity(DataInfo.userInfo, mLogSignInBiz.getResponseLogEntity());
        _setLogin(true);
    }

    /**
     * 退出登录
     */
    public void logOut() {
        LogUtils.w(TAG, "logOut()");
        _setLogin(false);
        DataInfo.userInfo = new UserInfoEntity();

        Message msg = Message.obtain();
        msg.arg1 = HANDLE_ACTION_LOGOUT;
        mHandle.sendMessage(msg);

        mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.PersonalCenterMessage.M_LOGOUT_SUCCESS));
    }


    private synchronized void _setLogin(boolean isLogin) {
        SharedPrefUtils.save(Constant.SharedPerfer.KEY_IS_LOGIN, isLogin);
        if (isLogin) {
            SharedPrefUtils.save(Constant.SharedPerfer.KEY_TOKEN, getUserInfo().getToken());
            SharedPrefUtils.save(Constant.SharedPerfer.KEY_USERID, getUserInfo().getId());

        } else {
            SharedPrefUtils.save(Constant.SharedPerfer.KEY_TOKEN, "");
            SharedPrefUtils.save(Constant.SharedPerfer.KEY_USERID, "");

        }

    }


    /**
     * 请求获取用户信息
     */
    private class SlaveRequestUserInfo extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveRequestUserInfo";

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.PersonalCenterMessage.PERSONALCENTER_M_UPDATE_USER_INFO));
        }

        @Override
        protected String getJsonBody() {
            long time = System.currentTimeMillis();
            String token2 = NetworkUtils.getToken2(time, getUserInfo().getId(), getUserInfo().getToken());
            String raw = NetworkUtils.getRowStr(time, getUserInfo().getId(), getUserInfo().getToken());

            String jsonBody = String.format("{\"id\":%d, \"token\":\"%s\", \"time\":%d, \"token2\":\"%s\", \"raw\": \"%s\"}",
                                                getUserInfo().getId(), getUserInfo().getToken(), time, token2, raw);

            LogUtils.w(TAG, "SlaveRequestUserInfo#jsonBody=%s", jsonBody);
            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "SlaveRequestUserInfo#onResponse()=%s", s);
            Gson gson = new Gson();
            ResponseUserInfoEntitiy item = gson.fromJson(s, ResponseUserInfoEntitiy.class);
            if (item.getStatus()==200) {
                DataInfo.userInfo = Bizdto.changeToUserInfoEntity(DataInfo.userInfo, item);

            } else {
                Message msg = Message.obtain();
                msg.arg1 = HANDLE_ACTION_REQUEST_USER_INFO;
                msg.arg2 = item.getStatus();
                msg.obj = item.getMsg();
                mHandle.sendMessage(msg);

            }

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "VolleyError=%s", volleyError.getLocalizedMessage());

        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_USER_INFO;
        }
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
