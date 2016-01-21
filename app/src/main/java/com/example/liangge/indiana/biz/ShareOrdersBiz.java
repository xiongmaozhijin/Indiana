package com.example.liangge.indiana.biz;

import android.content.Context;
import com.android.volley.VolleyError;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.ShareOrdersEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 晒单Biz
 * Created by baoxing on 2016/1/21.
 */
public class ShareOrdersBiz extends BaseFragmentBiz{

    private static final String TAG = ShareOrdersBiz.class.getSimpleName();

    private static Context mContext;
    private static ShareOrdersBiz mInstance;

    private MessageManager mMessageManager;

    private SlaveLoadShareOrdersThread mSlaveLoadShareOrdersThread;

    private ShareOrdersBiz(Context context) {
        mContext = context;
        initManager(context);
        initRes(context);
    }

    private void initRes(Context context) {
        mSlaveLoadShareOrdersThread = new SlaveLoadShareOrdersThread();
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
    }


    public static ShareOrdersBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ShareOrdersBiz(context);
        }

        return mInstance;
    }


    private static class DataInfo {
        public static List<ShareOrdersEntity> shareOrdersEntities = new ArrayList<>();
    }

    private static class RequesetInfo {
        public final static int MODE_ENTER = 1;
        public final static int MODE_LOADMORE = 2;
        public final static int MODE_REFRESH = 3;

        public static int iCurStartPage = 1;
        public static int iLoadMode = MODE_ENTER;
    }


    public List<ShareOrdersEntity> getShareOrdersList() {
        return DataInfo.shareOrdersEntities;
    }


    private void loadShareOrdersData(int loadMode) {
        LogUtils.i(TAG, "loadShareOrdersData(). isLoadMode=%d", loadMode);
        RequesetInfo.iLoadMode = loadMode;
        if (loadMode==RequesetInfo.MODE_LOADMORE) {
           if (!mSlaveLoadShareOrdersThread.isWorking()) {
               RequesetInfo.iCurStartPage++;
               mSlaveLoadShareOrdersThread = new SlaveLoadShareOrdersThread();
               mSlaveLoadShareOrdersThread.start();
           }

        } else if (loadMode==RequesetInfo.MODE_ENTER){
            if (!mSlaveLoadShareOrdersThread.isWorking()) {
                RequesetInfo.iCurStartPage = 1;
                mSlaveLoadShareOrdersThread = new SlaveLoadShareOrdersThread();
                mSlaveLoadShareOrdersThread.start();
            }

        } else if (loadMode==RequesetInfo.MODE_REFRESH) {
            mSlaveLoadShareOrdersThread.cancelAll();
            RequesetInfo.iCurStartPage = 1;
            mSlaveLoadShareOrdersThread = new SlaveLoadShareOrdersThread();
            mSlaveLoadShareOrdersThread.start();

        }

    }


    /**
     * 重新加载
     */
    public void onReload() {
        loadShareOrdersData(RequesetInfo.MODE_ENTER);
    }

    /**
     * 下拉刷新
     */
    public void onRefreshLoadData() {
        loadShareOrdersData(RequesetInfo.MODE_REFRESH);
    }

    /**
     * 加载更多
     */
    public void onScrollBottomLoadData() {
        loadShareOrdersData(RequesetInfo.MODE_LOADMORE);
    }


    @Override
    public void onViewCreated() {

    }

    @Override
    public void onFirstEnter() {
        loadShareOrdersData(RequesetInfo.MODE_ENTER);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }




    private class SlaveLoadShareOrdersThread extends NetRequestThread {

        private static final String R_TAG = "SlaveLoadShareOrdersThread";

        private void sendUIMessage(String uiMessage) {
            mMessageManager.sendMessage(new UIMessageEntity(uiMessage));
        }

        @Override
        protected void notifyStart() {
            super.notifyStart();
            if (RequesetInfo.iLoadMode==RequesetInfo.MODE_LOADMORE) {
                sendUIMessage(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_START);

            } else if (RequesetInfo.iLoadMode==RequesetInfo.MODE_ENTER){
                sendUIMessage(UIMessageConts.ShareOrdersMessage.NET_START);

            } else if (RequesetInfo.iLoadMode==RequesetInfo.MODE_REFRESH) {

            }
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            if (RequesetInfo.iLoadMode==RequesetInfo.MODE_LOADMORE) {
                sendUIMessage(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_SUCCESS);

            } else if (RequesetInfo.iLoadMode==RequesetInfo.MODE_ENTER){
                sendUIMessage(UIMessageConts.ShareOrdersMessage.NET_SUCCESS);

            } else if (RequesetInfo.iLoadMode==RequesetInfo.MODE_REFRESH) {
                sendUIMessage(UIMessageConts.ShareOrdersMessage.REFRESH_SUCCESS);
            }

        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            if (RequesetInfo.iLoadMode==RequesetInfo.MODE_LOADMORE) {
                sendUIMessage(UIMessageConts.ShareOrdersMessage.LOAD_MORE_NET_FAILED);

            } else if (RequesetInfo.iLoadMode==RequesetInfo.MODE_ENTER){
                sendUIMessage(UIMessageConts.ShareOrdersMessage.NET_FAILED);

            } else if (RequesetInfo.iLoadMode==RequesetInfo.MODE_REFRESH) {
                sendUIMessage(UIMessageConts.ShareOrdersMessage.REFRESH_FAILED);
            }
        }

        @Override
        protected String getJsonBody() {
            String jsonBody = String.format("{\"type\":\"%s\", \"page\":%d}", "", RequesetInfo.iCurStartPage);

            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(R_TAG, "onResponse=%s", s);
            Gson gson = new Gson();
            List<ShareOrdersEntity> list = gson.fromJson(s, new TypeToken<List<ShareOrdersEntity>>(){}.getType());
            DataInfo.shareOrdersEntities = list;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(R_TAG, "volleyError=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_SHARE_ORDERS;
        }
    }




}
