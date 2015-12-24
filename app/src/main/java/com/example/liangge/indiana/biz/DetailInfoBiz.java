package com.example.liangge.indiana.biz;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.JsonStringRequest;
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

    }


}
