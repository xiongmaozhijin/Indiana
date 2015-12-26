package com.example.liangge.indiana.biz.user;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.IndianaBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.user.IndianaRecordEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 夺宝记录Biz
 * Created by baoxing on 2015/12/25.
 */
public class IndianaRecordBiz extends BaseActivityBiz {


    private static final String TAG = IndianaRecordBiz.class.getSimpleName();

    private static Context mContext;

    private static IndianaRecordBiz mInstance;

    private MessageManager mMessageManager;

    private PersonalCenterBiz mPersonalCenterBiz;

    private SlaveLoadIndianaRecordInfThread mSlaveLoadIndianaRecordInfThread;


    private static class DataInfo {
        public static List<IndianaRecordEntity> mListData = new ArrayList<>();

    }

    private static class RequestInfo {

        public static String tag = Constant.IndianaRecord.TAG_ALL;

        public static boolean bIsLoadMore = false;

        public static int iStartPage = 1;

    }



    private IndianaRecordBiz(Context context) {
        this.mContext = context;
        initManager(context);
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(context);
        mSlaveLoadIndianaRecordInfThread = new SlaveLoadIndianaRecordInfThread();

    }


    public static IndianaRecordBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new IndianaRecordBiz(context);

        }

        return mInstance;
    }


    /**
     * 返回参与记录数据
     * @return
     */
    public List<IndianaRecordEntity> getData() {
        return DataInfo.mListData;
    }

    /**
     * 请求加载数据 {当加载更多时，参数tag无效}
     * @param tag
     * @param bLoadMore
     */
    public void loadIndianaRecord(String tag, boolean bLoadMore) {
        if (!bLoadMore) {
            RequestInfo.bIsLoadMore = false;
            RequestInfo.iStartPage = 1;
            RequestInfo.tag = tag;
            mSlaveLoadIndianaRecordInfThread.cancelAll();
            mSlaveLoadIndianaRecordInfThread = new SlaveLoadIndianaRecordInfThread();
            mSlaveLoadIndianaRecordInfThread.start();

        } else {
            if (!mSlaveLoadIndianaRecordInfThread.isWorking()) {
                RequestInfo.iStartPage++;
                RequestInfo.bIsLoadMore = true;
                mSlaveLoadIndianaRecordInfThread = new SlaveLoadIndianaRecordInfThread();
                mSlaveLoadIndianaRecordInfThread.start();

            }

        }
    }



    private class SlaveLoadIndianaRecordInfThread extends NetRequestThread {

        private static final String REQUEST_TAG = "SlaveLoadIndianaRecordInfThread";


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
            String jsonData = String.format("{\"type\":\"%s\", \"account_id\":%d, \"page\":%d}", RequestInfo.tag, mPersonalCenterBiz.getUserInfo().getUserId(), RequestInfo.iStartPage);
            return jsonData;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.i(TAG, "onResponseListener=%s", s);
            Gson gson = new Gson();
            DataInfo.mListData = gson.fromJson(s, new TypeToken<List<IndianaRecordEntity>>(){}.getType());
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
            return Constant.WebServiceAPI.PLAY_INDIANA_RECORD;
        }

    }








    @Override
    public void onCreate() {
        loadIndianaRecord(Constant.IndianaRecord.TAG_ALL, false);

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mSlaveLoadIndianaRecordInfThread != null) {
            mSlaveLoadIndianaRecordInfThread.cancelAll();
        }
    }


}
