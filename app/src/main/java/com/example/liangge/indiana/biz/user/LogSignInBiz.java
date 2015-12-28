package com.example.liangge.indiana.biz.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.ResponseLogEntity;
import com.example.liangge.indiana.model.user.ResponseSignInEntity;
import com.example.liangge.indiana.model.user.VerticationCodeEntity;
import com.google.gson.Gson;

/**
 * 登录注册Biz
 * Created by baoxing on 2015/12/26.
 */
public class LogSignInBiz {

    private static final String TAG = LogSignInBiz.class.getSimpleName();

    private static LogSignInBiz mInstance;

    private static Context mContext;

    private SlaveSignInThread mSlaveSignInThread;

    private SlaveVeticationCodeRequestThead mSlaveVeticationCodeRequestThead;

    private SlaveLogThread mSlaveLogThread;


    private MessageManager mMessageManager;



    private LogSignInBiz(Context context) {
        this.mContext = context;
        initManager();
        initRes();
    }

    private void initRes() {
        mSlaveSignInThread = new SlaveSignInThread();
        mSlaveVeticationCodeRequestThead = new SlaveVeticationCodeRequestThead();
        mSlaveLogThread = new SlaveLogThread();

    }

    private void initManager() {
        mMessageManager = MessageManager.getInstance(mContext);
    }


    public static LogSignInBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LogSignInBiz(context);
        }

        return mInstance;
    }

    private static class DataInfo {

    }

    /**
     * 请求数据
     */
    private static class RequestInfo {

        /** 手机号码 */
        public static String phoneNumber = "";

        /** 用户名 */
        public static String username = "";

        /** 密码 */
        public static String password = "";

        /** 验证码 */
        public static String veticationCode = "";

    }


    /**
     * 响应数据
     */
    private static class ResposneInfo {

        /** 验证码 */
        public static String veticationCode = "1234";

        /** 注册响应实体 */
        public static ResponseSignInEntity responseSignInEntity;

        /** 登录响应实体 */
        public static ResponseLogEntity responseLogEntity;



    }




    public ResponseLogEntity getResponseLogEntity() {
        return ResposneInfo.responseLogEntity;
    }

    public ResponseSignInEntity getResponseSignInEntity() {
        return ResposneInfo.responseSignInEntity;
    }


    public String getVeticationCode() {
        return ResposneInfo.veticationCode;
    }







    /**
     * 一键注册
     */
    public void onSignIn(String phoneNumber, String username, String password, String verticationCode) {
        RequestInfo.phoneNumber = phoneNumber;
        RequestInfo.username = username;
        RequestInfo.password = password;
        RequestInfo.veticationCode = verticationCode;

        if (!mSlaveSignInThread.isWorking()) {
            mSlaveSignInThread.cancelAll();
            mSlaveSignInThread = new SlaveSignInThread();
            mSlaveSignInThread.start();
        }

    }

    /**
     * 一键登录
     */
    public void log(String phoneNumber, String password) {
        RequestInfo.phoneNumber = phoneNumber;
        RequestInfo.password = password;

        if (!mSlaveLogThread.isWorking()) {
            mSlaveLogThread.cancelAll();
            mSlaveLogThread = new SlaveLogThread();
            mSlaveLogThread.start();
        }
    }


    /**
     * 发送验证码
     */
    public void onSendVeticationCode() {
        if (!mSlaveVeticationCodeRequestThead.isWorking()) {
            mSlaveVeticationCodeRequestThead.cancelAll();
            mSlaveVeticationCodeRequestThead = new SlaveVeticationCodeRequestThead();
            mSlaveVeticationCodeRequestThead.start();
        }

    }



    /**
     * 一键注册
     */
    private class SlaveSignInThread extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveSignInThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_SUCCESS));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_SINGIN_FAILED));
        }

        @Override
        protected String getJsonBody() {

            String jsonBody = String.format("{\"phone_number\":\"%s\", \"password\":\"%s\", \"verificationCode\":\"%s\", \"type\":\"phone\", \"nickname\":\"%s\"}"
                                                , RequestInfo.phoneNumber, RequestInfo.password, RequestInfo.veticationCode, RequestInfo.username);

            LogUtils.w(TAG, "SignIn requestJson=%s", jsonBody);
            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.e(TAG, "SignIn onResponse=%s", s);
            Gson gson = new Gson();
            ResponseSignInEntity item = gson.fromJson(s, ResponseSignInEntity.class);
            ResposneInfo.responseSignInEntity = item;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "SignIn VolleyError", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_SIGININ;
        }
    }


    //请求验证码
    private class SlaveVeticationCodeRequestThead extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveVeticationCodeRequestThead";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_SUCCESS));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_VETICATIONCODE_FAILED));
        }

        @Override
        protected String getJsonBody() {
            String jsonBody = String.format("{\"phone_number\":\"%s\"}", RequestInfo.phoneNumber);

            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "Vertication ResponseListener=%s", s);
            Gson gson = new Gson();
            VerticationCodeEntity item = gson.fromJson(s, VerticationCodeEntity.class);
            ResposneInfo.veticationCode = item.getVerificationCode();

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "Vertication VolleyError=%s", volleyError);
        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_VETICATION_CODE;
        }
    }

    //登录
    private class SlaveLogThread extends NetRequestThread {

        private static final String REQUSET_TAG = "SlaveLogThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_SUCCESS));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.LogSignIn.M_LOGSINGIN_LOG_FAILED));
        }

        @Override
        protected String getJsonBody() {
            String jsonBody = String.format("{\"phone_number\":\"%s\", \"password\":\"%s\"}",
                                                RequestInfo.phoneNumber, RequestInfo.password);
            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "Log onResponse=%s", s);
            Gson gson = new Gson();
            ResponseLogEntity item = gson.fromJson(s, ResponseLogEntity.class);
            ResposneInfo.responseLogEntity = item;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "Log VolleyError=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUSET_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_LOG;
        }
    }


    //退出
    private class SlaveLogOutThread extends NetRequestThread {

        @Override
        protected String getJsonBody() {
            return "";
        }

        @Override
        protected void onResponseListener(String s) {

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {

        }

        @Override
        protected String getRequestTag() {
            return null;
        }

        @Override
        protected String getWebServiceAPI() {
            return null;
        }
    }



}
