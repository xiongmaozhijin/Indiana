package com.example.liangge.indiana.biz;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.JsonStringRequest;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.example.liangge.indiana.model.ActivityProductDetailInfoEntity;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.google.gson.Gson;

/**
 * 商品详细Biz
 * Created by baoxing on 2015/12/22.
 */
public class DetailInfoBiz {


    private static final String TAG = DetailInfoBiz.class.getSimpleName();

    private static DetailInfoBiz mInstance;

    private static Context mContext;

    private MessageManager mMessageManager;

    private VolleyBiz mVolleyBiz;

    private SlaveLoadDetailInfoThread mSlaveLoadDetailInfoThread;

    private SlaveLoadAllPalyRecordsThread mSlaveLoadAllPalyRecordsThread;

    /**
     * 请求字段
     */
    private static class RequestInfo {
        /** 活动期次 */
        public static long lActivityId;


    }

    private static class DataInfo {
        public static ActivityProductDetailInfoEntity activityProductItemEntity;
    }




    private DetailInfoBiz(Context context) {
        this.mContext = context;
        mMessageManager = MessageManager.getInstance(context);
        mVolleyBiz = VolleyBiz.getInstance();
        mSlaveLoadDetailInfoThread = new SlaveLoadDetailInfoThread();
        mSlaveLoadAllPalyRecordsThread = new SlaveLoadAllPalyRecordsThread();
    }


    public static DetailInfoBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DetailInfoBiz(context);
        }

        return mInstance;
    }

    /**
     * 返回数据实体
     * @return
     */
    public ActivityProductDetailInfoEntity getDetailEntity() {
        return DataInfo.activityProductItemEntity;
    }


    /**
     * 设置活动期次
     */
    public void setActivityId(long activityId) {
        LogUtils.i(TAG,"activityId=%d", activityId);
        RequestInfo.lActivityId = activityId;

    }


    private void loadDetailInfo() {
        if (!mSlaveLoadDetailInfoThread.isWorking()) {
            mSlaveLoadDetailInfoThread = new SlaveLoadDetailInfoThread();
            mSlaveLoadDetailInfoThread.start();
        }

    }


    /**
     * 请求所有参与记录
     */
    private void loadAllPlayRecord() {
        if (!mSlaveLoadAllPalyRecordsThread.isWorking()) {
            mSlaveLoadAllPalyRecordsThread.cancelAll();
            mSlaveLoadAllPalyRecordsThread = new SlaveLoadAllPalyRecordsThread();
            mSlaveLoadAllPalyRecordsThread.start();
        }
    }

    /**
     * 请求所有的参与记录
     */
    private class SlaveLoadAllPalyRecordsThread extends NetRequestThread {

        private static final String REQUERY_TAG = "SlaveLoadAllPalyRecordsThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORD_REQ_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_FAILED));
        }

        @Override
        protected String getJsonBody() {
            return null;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "SlaveLoadAllPalyRecordsThread onResponse=%s", s);

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "SlaveLoadAllPalyRecordsThread. volleyError=%s",volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUERY_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return null;
        }
    }



    /**
     * 商品活动详情
     */
    private class SlaveLoadDetailInfoThread extends Thread {

        private static final String REQUEST_TAG = "SlaveLoadDetailInfoThread";

        private boolean bIsWorking = false;

        @Override
        public void run() {
            super.run();
            notifyStart();
            String jsonData = getJsonBody();
            JsonStringRequest request = new JsonStringRequest(Request.Method.POST, Constant.WebServiceAPI.INDIANA_ACTIVITY_DETAIL_INFO, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    LogUtils.i(TAG, "inResponse=%s", s);
                    Gson gson = new Gson();
                    DataInfo.activityProductItemEntity = gson.fromJson(s, ActivityProductDetailInfoEntity.class);
                    notifySuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.e(TAG, "volleryError=%s", volleyError.getLocalizedMessage());
                    notifyFail();
                }
            }, jsonData);

            request.setTag(REQUEST_TAG);
            mVolleyBiz.addRequest(request);
        }

        private String getJsonBody() {
            String jsonBody = String.format("{\"issue_id\":%d}", RequestInfo.lActivityId);
            return jsonBody;
        }

        private void notifyStart() {
            this.bIsWorking = true;
            UIMessageEntity item = new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_START);
            mMessageManager.sendMessage(item);
        }

        private void notifyFail() {
            this.bIsWorking = false;
            UIMessageEntity item = new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_FAILED);
            mMessageManager.sendMessage(item);
        }

        private void notifySuccess() {
            this.bIsWorking = false;
            UIMessageEntity item = new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_SUCCEED);
            mMessageManager.sendMessage(item);
        }

        /** 是否还在工作 */
        public boolean isWorking() {
            return this.bIsWorking;
        }


    }



    public void onCreate() {

    }

    public void onResume() {
        loadDetailInfo();
    }


    public void onStop() {

    }


    public void onDestroy() {
        if (mSlaveLoadDetailInfoThread != null) {
        }
        if (mSlaveLoadAllPalyRecordsThread != null) {
            mSlaveLoadAllPalyRecordsThread.cancelAll();

        }

    }


}
