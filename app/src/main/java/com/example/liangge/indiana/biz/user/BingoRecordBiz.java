package com.example.liangge.indiana.biz.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.WebViewBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.BingoRecordEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 中奖记录Biz<br/>
 * Created by baoxing on 2015/12/25.
 */
public class BingoRecordBiz extends BaseActivityBiz{


    private static final String TAG = BingoRecordBiz.class.getSimpleName();

    private static Context mContext;

    private static BingoRecordBiz mInstance;

    private static MessageManager mMessageManager;

    private SlaveLoadBingoRecordThread mSlaveLoadBingoRecordThread;

    private PersonalCenterBiz mPersonalCenterBiz;

    private BingoRecordBiz(Context context) {
        this.mContext = context;
        initManager();
        initRes();
    }

    private void initRes() {
        mSlaveLoadBingoRecordThread = new SlaveLoadBingoRecordThread();
    }

    private void initManager() {
        mMessageManager = MessageManager.getInstance(mContext);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(mContext);
    }

    public static synchronized BingoRecordBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BingoRecordBiz(context);
        }

        return mInstance;
    }


    private static class DataInfo {
        public static List<BingoRecordEntity> listDatas = new ArrayList<>();

    }

    public List<BingoRecordEntity> getBingoRecordList() {
        return DataInfo.listDatas;
    }


    private static class RequestInfo {

        /** 起始页码 */
        public static int iStartPage = 1;

        public static boolean bIsLoadMore = false;

    }

    /**
     * 加载中奖记录数据
     * @param isLoadMore
     */
    public void loadBingoRecord(boolean isLoadMore) {
        if (isLoadMore) {
            if (!mSlaveLoadBingoRecordThread.isWorking()) {
                RequestInfo.bIsLoadMore = true;
                RequestInfo.iStartPage++;
                mSlaveLoadBingoRecordThread = new SlaveLoadBingoRecordThread();
                mSlaveLoadBingoRecordThread.start();
            }

        } else {
            RequestInfo.bIsLoadMore = false;
            RequestInfo.iStartPage = 1;
            mSlaveLoadBingoRecordThread.cancelAll();
            mSlaveLoadBingoRecordThread = new SlaveLoadBingoRecordThread();
            mSlaveLoadBingoRecordThread.start();

        }

    }


    private class SlaveLoadBingoRecordThread extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveLoadBingoRecordThread";

        private void sendMessage(String uiMsg) {
            UIMessageEntity item = new UIMessageEntity(uiMsg);
            mMessageManager.sendMessage(item);
        }

        @Override
        protected void notifyStart() {
            super.notifyStart();
            String uiMsg;
            if (RequestInfo.bIsLoadMore) {
                uiMsg = UIMessageConts.BingoRecord.M_RELOAD_START_MORE;
            } else {
                uiMsg = UIMessageConts.BingoRecord.M_RELOAD_START;
            }
            sendMessage(uiMsg);
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            String uiMsg;
            if (RequestInfo.bIsLoadMore) {
                uiMsg = UIMessageConts.BingoRecord.M_RELOAD_SUCCESS_MORE;
            } else {
                uiMsg = UIMessageConts.BingoRecord.M_RELOAD_SUCCESS;
            }
            sendMessage(uiMsg);
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            String uiMsg;
            if (RequestInfo.bIsLoadMore) {
                uiMsg = UIMessageConts.BingoRecord.M_RELOAD_FAIL_MORE;
            } else {
                uiMsg = UIMessageConts.BingoRecord.M_RELOAD_FAIL;
            }
            sendMessage(uiMsg);
        }

        @Override
        protected String getJsonBody() {
            String jsonBody = String.format("{\"account_id\":%d, \"page\":%d}", mPersonalCenterBiz.getUserID(), RequestInfo.iStartPage);

            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(TAG, "onResponseListener=%s", s);
            Gson gson = new Gson();
            List<BingoRecordEntity> list = gson.fromJson(s, new TypeToken<List<BingoRecordEntity>>(){}.getType());
            DataInfo.listDatas = list;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "volleryError=%s", volleyError.getLocalizedMessage());

        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.BINGO_RECORED;
        }

    }





    @Override
    public void onCreate() {
        loadBingoRecord(false);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }


}
