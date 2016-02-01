package com.example.liangge.indiana.biz.user;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.WebViewBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.ResponseActivityPlayRecordEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.example.liangge.indiana.ui.user.UserCenterActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户中心Biz
 * Created by baoxing on 2015/12/25.
 */
public class UserCenterBiz extends BaseActivityBiz {


    private static final String TAG = IndianaRecordBiz.class.getSimpleName();


    private static UserCenterBiz mInstance;

    private MessageManager mMessageManager;

    private SlaveLoadUserDataThread mSlaveLoadUserDataThread;


    public interface RequestType {
        int INDIAN_RECOREDS = 1;
        int BINGO_RECORDS = 2;
        int WISH_LISTS = 3;
    }

    private static class DataInfo {
        /** 夺宝记录 */
        public static List<IndianaRecordEntity> mIndianaList = new ArrayList<>();

        /** 中奖记录 */
        public static List<BingoRecordEntity> mBingoList = new ArrayList<>();

        public static ResponseActivityPlayRecordEntity mUserItem = new ResponseActivityPlayRecordEntity("", "", -1);
    }

    private static class RequestInfo {

        public static String tag = Constant.IndianaRecord.TAG_ALL;

        public static boolean bIsLoadMore = false;

        public static int iStartPage = 1;

        public static int iRequestType = RequestType.INDIAN_RECOREDS;

    }



    private UserCenterBiz(Context context) {
        initManager(context);
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
        mSlaveLoadUserDataThread = new SlaveLoadUserDataThread();

    }


    public static UserCenterBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserCenterBiz(context);

        }

        return mInstance;
    }


    /**
     * 返回夺宝记录
     * @return
     */
    public List<IndianaRecordEntity> getIndianListData() {
        return DataInfo.mIndianaList;
    }

    /**
     * 返回中奖记录
     * @return
     */
    public List<BingoRecordEntity> getBingoListData() {
        return DataInfo.mBingoList;
    }


    /**
     * 返回请求类型
     * @return
     */
    public int getCurRequestType() {
        return RequestInfo.iRequestType;
    }


    public ResponseActivityPlayRecordEntity getUserItem() {
        return DataInfo.mUserItem;
    }


    public void setUserItem(ResponseActivityPlayRecordEntity item) {
        DataInfo.mUserItem = item;
    }


    public void startActivity(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 请求加载数据
     * @param
     * @param bLoadMore
     */
    public void loadUserData(int requestType, boolean bLoadMore) {
        RequestInfo.iRequestType = requestType;

        if (!bLoadMore) {
            RequestInfo.bIsLoadMore = false;
            RequestInfo.iStartPage = 1;
            mSlaveLoadUserDataThread.cancelAll();
            mSlaveLoadUserDataThread = new SlaveLoadUserDataThread();
            mSlaveLoadUserDataThread.start();

        } else {
            if (!mSlaveLoadUserDataThread.isWorking()) {
                RequestInfo.iStartPage++;
                RequestInfo.bIsLoadMore = true;
                mSlaveLoadUserDataThread = new SlaveLoadUserDataThread();
                mSlaveLoadUserDataThread.start();

            }

        }
    }



    private class SlaveLoadUserDataThread extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveLoadUserDataThread";


        private void sendUIMessage(String uiMsg) {
            UIMessageEntity item = new UIMessageEntity(uiMsg);
            mMessageManager.sendMessage(item);
        }

        @Override
        protected void notifyStart() {
            super.notifyStart();
            String uiMsg;
            if (RequestInfo.bIsLoadMore) {
                uiMsg = UIMessageConts.IndianaRecord.M_RELOAD_START_MORE;
            } else {
                uiMsg = UIMessageConts.IndianaRecord.M_RELOAD_START;
            }

            sendUIMessage(uiMsg);
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            String uiMsg;
            if (RequestInfo.bIsLoadMore) {
                uiMsg = UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS_MORE;
            } else {
                uiMsg = UIMessageConts.IndianaRecord.M_RELOAD_SUCCESS;
            }

            sendUIMessage(uiMsg);

        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            String uiMsg;
            if (RequestInfo.bIsLoadMore) {
                uiMsg = UIMessageConts.IndianaRecord.M_RELOAD_FAIL_MORE;
            } else {
                uiMsg = UIMessageConts.IndianaRecord.M_RELOAD_FAIL;
            }

            sendUIMessage(uiMsg);

        }

        @Override
        protected String getJsonBody() {
            String jsonData = "";
            if (RequestInfo.iRequestType==RequestType.INDIAN_RECOREDS) {
                jsonData = String.format("{\"type\":\"%s\", \"account_id\":%d, \"page\":%d}", RequestInfo.tag, DataInfo.mUserItem.getUserID(), RequestInfo.iStartPage);

            } else if (RequestInfo.iRequestType==RequestType.BINGO_RECORDS) {
                jsonData = String.format("{\"account_id\":%d, \"page\":%d}", DataInfo.mUserItem.getUserID(), RequestInfo.iStartPage);

            } else if (RequestInfo.iRequestType==RequestType.WISH_LISTS) {
                //TODO
            }


            return jsonData;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(TAG, "onResponseListener=%s", s);
            Gson gson = new Gson();
            if (RequestInfo.iRequestType==RequestType.INDIAN_RECOREDS) {
                DataInfo.mIndianaList = gson.fromJson(s, new TypeToken<List<IndianaRecordEntity>>(){}.getType());

            } else if (RequestInfo.iRequestType==RequestType.BINGO_RECORDS) {
                DataInfo.mBingoList = gson.fromJson(s, new TypeToken<List<BingoRecordEntity>>(){}.getType());

            } else if (RequestInfo.iRequestType==RequestType.WISH_LISTS) {

            }

        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "volleyError=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            String api = "";
            if (RequestInfo.iRequestType==RequestType.INDIAN_RECOREDS) {
                api = Constant.WebServiceAPI.PLAY_INDIANA_RECORD;

            } else if (RequestInfo.iRequestType==RequestType.BINGO_RECORDS) {
                api = Constant.WebServiceAPI.BINGO_RECORED;

            } else if (RequestInfo.iRequestType==RequestType.WISH_LISTS) {

            }

            return api;
        }

    }





    public void onScrollBottomLoadMore() {
        LogUtils.i(TAG, "onScrollBottomLoadMore()");
        loadUserData(RequestInfo.iRequestType, true);
    }


    @Override
    public void onCreate() {
        loadUserData(RequestType.INDIAN_RECOREDS, false);

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mSlaveLoadUserDataThread != null) {
            mSlaveLoadUserDataThread.cancelAll();
        }
    }


}
