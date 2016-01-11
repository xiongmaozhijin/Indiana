package com.example.liangge.indiana.biz.inner;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.biz.WebViewBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.inner.HistoryRecordEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2016/1/11.
 */
public class HistoryRecordBiz extends BaseActivityBiz {

    private static Context mContext;

    private static HistoryRecordBiz mInstance;

    private MessageManager mMessageManager;

    private SlaveLoadHistoryDataThread mSlaveLoadHistoryDataThread;

    private HistoryRecordBiz(Context context) {
        mContext = context;
        initManager(context);
        initRes(context);
    }

    private void initRes(Context context) {
        mSlaveLoadHistoryDataThread = new SlaveLoadHistoryDataThread();
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
    }

    public static HistoryRecordBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HistoryRecordBiz(context);
        }

        return mInstance;
    }

    private static class DataInfo {
        public static List<HistoryRecordEntity> mListData = new ArrayList<>();
    }

    private static class RequestInfo {
        public static int iLoadMode = Constant.Comm.MODE_ENTER;
        public static int iCurPage = 1;
        public static long iGoodId;
    }

    /**
     *  加载数据
     * @param iLoadMode
     */
    private void loadData(int iLoadMode) {
        if (iLoadMode== Constant.Comm.MODE_LOAD_MORE) {
            if (!mSlaveLoadHistoryDataThread.isWorking()) {
                RequestInfo.iCurPage++;
                mSlaveLoadHistoryDataThread = new SlaveLoadHistoryDataThread();
                mSlaveLoadHistoryDataThread.start();
            }

        } else {
            mSlaveLoadHistoryDataThread.cancelAll();
            RequestInfo.iCurPage = 1;
            mSlaveLoadHistoryDataThread = new SlaveLoadHistoryDataThread();
            mSlaveLoadHistoryDataThread.start();

        }
    }

    public List<HistoryRecordEntity> getData() {
        return DataInfo.mListData;
    }

    public int getLoadMode() {
        return RequestInfo.iLoadMode;
    }

    public void onScrollBottomLoadMore() {
        loadData(Constant.Comm.MODE_LOAD_MORE);
    }

    @Override
    public void onCreate() {
        loadData(Constant.Comm.MODE_ENTER);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mSlaveLoadHistoryDataThread != null) {
            mSlaveLoadHistoryDataThread.cancelAll();
        }
    }




    private class SlaveLoadHistoryDataThread extends NetRequestThread {

        private static final String R_TAG = "SlaveLoadHistoryDataThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_NET_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_NET_SUCCESS));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.Comm_Activity.COMM_NET_FAILED));
        }

        @Override
        protected String getJsonBody() {
            String json = String.format("{\"commodity_id\":%d, \"page\":%d}", RequestInfo.iGoodId, RequestInfo.iCurPage);

            LogUtils.i(R_TAG, "jsonBody=%s", json);
            return json;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(R_TAG, "onResponse=%s", s);
            Gson gson = new Gson();
            List<HistoryRecordEntity> list = gson.fromJson(s, new TypeToken<List<HistoryRecordEntity>>(){}.getType());
            DataInfo.mListData = list;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.i(R_TAG, "vollery=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_HISTORY_RECORD;
        }

    }








}
