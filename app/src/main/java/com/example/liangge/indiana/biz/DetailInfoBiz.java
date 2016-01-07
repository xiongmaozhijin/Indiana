package com.example.liangge.indiana.biz;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.JsonStringRequest;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.example.liangge.indiana.model.ActivityProductDetailInfoEntity;
import com.example.liangge.indiana.model.ResponseActivityPlayRecordEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详细Biz
 * Created by baoxing on 2015/12/22.
 */
public class DetailInfoBiz {


    private static final String TAG = DetailInfoBiz.class.getSimpleName();

    private static DetailInfoBiz mInstance;

    private static PersonalCenterBiz mPersonalCenterBiz;

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

        /** 是否加载更多参与记录 */
        public static boolean bLoadPlayRecordMore = false;
        /** 参与记录起始页 */
        public static int iPlayRecordStartPage = 1;

        /** 是否请求最新一期 */
        public static boolean bIsNewestActivity = false;

        public static int iLoadMode = Constant.Comm.MODE_ENTER;

    }


    private static class ResponseInfo {
        /** 某期用户的参与记录 */
        public static List<ResponseActivityPlayRecordEntity> listAllPlayRecords = new ArrayList<>();


    }


    private static class DataInfo {
        public static ActivityProductDetailInfoEntity activityProductItemEntity;
    }




    private DetailInfoBiz(Context context) {
        this.mContext = context;
        initManager(context);
        initRes();
    }

    private void initRes() {
        mSlaveLoadDetailInfoThread = new SlaveLoadDetailInfoThread();
        mSlaveLoadAllPalyRecordsThread = new SlaveLoadAllPalyRecordsThread();
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
        mVolleyBiz = VolleyBiz.getInstance();
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(mContext);
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
     * 返回加载的模式
     * @return
     */
    public int getLoadMode() {
        return RequestInfo.iLoadMode;
    }


    /**
     * 设置活动期次
     */
    public void setActivityId(long activityId) {
        LogUtils.i(TAG,"activityId=%d", activityId);
        RequestInfo.lActivityId = activityId;

    }

    /**
     * 加载商品活动详情
     * @param isNewestActivity  是否请求最新一期，原有的已经结束了
     */
    private void loadDetailInfo(boolean isNewestActivity) {
        if (!mSlaveLoadDetailInfoThread.isWorking()) {
            RequestInfo.bIsNewestActivity = isNewestActivity;
            mSlaveLoadDetailInfoThread = new SlaveLoadDetailInfoThread();
            mSlaveLoadDetailInfoThread.start();
        }

    }

    /**
     * 返回该期的参与记录
     * @return
     */
    public List<ResponseActivityPlayRecordEntity> getRecordListData() {
        List<ResponseActivityPlayRecordEntity> list = ResponseInfo.listAllPlayRecords;
        LogUtils.w(TAG, "getRecordListData().size=%d, info=%s", list.size(), list.toString());

        return list;
    }


    /**
     * 得到可读的所有参与记录
     * @return
     */
    public String getHumanReadablePlayRecords() {
        //TODO<string name="activity_productdetailinfo_all_join_record_format">\n%1$s\t参与记录:%2$d\n参与时间:%2$s</string>
        String recordFormat = mContext.getResources().getString(R.string.activity_productdetailinfo_all_join_record_format);
        String records = "";

        List<ResponseActivityPlayRecordEntity> list = ResponseInfo.listAllPlayRecords;
        ResponseActivityPlayRecordEntity item;
        String strItem;
        for (int i=0, len=list.size(); i<len; i++) {
            item = list.get(i);
            strItem = String.format(recordFormat, item.getNickname(), item.getOwn_share(), item.getRecord_time());
            records += strItem;
        }

        return records;
    }

    /**
     * 前往下一期
     */
    public void onBtnGoNextHotActivity() {
        LogUtils.w(TAG, "onBtnGoNextHotActivity()");
        RequestInfo.bLoadPlayRecordMore = false;
        loadDetailInfo(true);

    }



    /**
     * 请求所有参与记录
     */
    private void loadAllPlayRecord(boolean bIsLoadMore) {
        RequestInfo.bLoadPlayRecordMore = bIsLoadMore;

        if (RequestInfo.bLoadPlayRecordMore) {
            if (!mSlaveLoadAllPalyRecordsThread.isWorking()) {
                RequestInfo.iPlayRecordStartPage++;
                mSlaveLoadAllPalyRecordsThread = new SlaveLoadAllPalyRecordsThread();
                mSlaveLoadAllPalyRecordsThread.start();
            }

        } else {
            RequestInfo.iPlayRecordStartPage = 1;
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
            if (RequestInfo.bLoadPlayRecordMore) {
                mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORD_REQ_START_MORE));

            } else {
                mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORD_REQ_START));

            }

        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            if (RequestInfo.bLoadPlayRecordMore) {
                mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED_MORE));

            } else {
                mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED));

            }

        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            if (RequestInfo.bLoadPlayRecordMore) {
                mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_FAILED_MORE));

            } else {
                mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_FAILED));

            }

        }

        @Override
        protected String getJsonBody() {
            String jsonBody = String.format("{\"issue_id\":%d, \"page\":%d}", RequestInfo.lActivityId, RequestInfo.iPlayRecordStartPage);

            LogUtils.w(TAG, "SlaveLoadAllPalyRecordsThread#jsonBody=%s", jsonBody);
            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "SlaveLoadAllPalyRecordsThread onResponse=%s", s);
            Gson gson = new Gson();
            List<ResponseActivityPlayRecordEntity> list = gson.fromJson(s, new TypeToken<List<ResponseActivityPlayRecordEntity>>(){}.getType());
            ResponseInfo.listAllPlayRecords = list;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "SlaveLoadAllPalyRecordsThread. volleyError=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUERY_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            return Constant.WebServiceAPI.REQUEST_ACTIVITY_PLAY_RECORDS;
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
                    LogUtils.i(TAG, "SlaveLoadDetailInfoThread#onResponse=%s", s);
                    Gson gson = new Gson();
                    DataInfo.activityProductItemEntity = gson.fromJson(s, ActivityProductDetailInfoEntity.class);
                    RequestInfo.lActivityId = DataInfo.activityProductItemEntity.getActivityId();
                    notifySuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.e(TAG, "SlaveLoadDetailInfoThread#volleryError=%s", volleyError.getLocalizedMessage());
                    notifyFail();
                }
            }, jsonData);

            request.setTag(REQUEST_TAG);
            mVolleyBiz.addRequest(request);
        }

        private String getJsonBody() {
            String jsonBody = String.format("{\"issue_id\":%d, \"id\":%d, \"token\":\"%s\", \"new\":%b}", RequestInfo.lActivityId,
                                                mPersonalCenterBiz.getUserInfo().getUserId(), mPersonalCenterBiz.getUserInfo().getToken(), RequestInfo.bIsNewestActivity );

            LogUtils.i(TAG, "SlaveLoadDetailInfoThread#jsonBody=%s", jsonBody);

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
            loadAllPlayRecord(RequestInfo.bLoadPlayRecordMore);
        }

        /** 是否还在工作 */
        public boolean isWorking() {
            return this.bIsWorking;
        }


    }



    public void onCreate() {
        RequestInfo.iLoadMode = Constant.Comm.MODE_ENTER;
        loadDetailInfo(false);
        RequestInfo.bLoadPlayRecordMore = false;
    }

    public void onResume() {
    }


    public void onRestart() {
        RequestInfo.iLoadMode = Constant.Comm.MODE_REFRESH;
        loadDetailInfo(false);
        RequestInfo.bLoadPlayRecordMore = false;
    }

    public void onRefresh() {
        RequestInfo.iLoadMode = Constant.Comm.MODE_REFRESH;
        loadDetailInfo(false);
        RequestInfo.bLoadPlayRecordMore = false;
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
