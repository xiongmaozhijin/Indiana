package com.example.liangge.indiana.biz.inner;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.biz.BaseActivityBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.example.liangge.indiana.model.inner.CategoryDetailEntitiy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 类别详细
 * Created by baoxing on 2016/1/4.
 */
public class CategroyDetailBiz extends BaseActivityBiz{


    private static final String TAG = CategroyDetailBiz.class.getSimpleName();

    private static CategroyDetailBiz mInstance;

    private static Context mContext;

    private static MessageManager mMessageManager;


    private SlaveLoadDataThread mSlaveLoadDataThread;

    private CategroyDetailBiz(Context context) {
        this.mContext = context;
        initManager(context);
        initRes(context);
    }

    private void initRes(Context context) {
        mSlaveLoadDataThread = new SlaveLoadDataThread();
    }

    private void initManager(Context context) {
        mMessageManager = MessageManager.getInstance(context);
    }


    public static synchronized CategroyDetailBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CategroyDetailBiz(context);

        }

        return mInstance;
    }


    public interface IRequestCategory {
        /** 普通类别 */
        int NORMAL_CATEGORY = 1;

        /** 10元专区类别 */
        int TEN_YUAN_CATEGORY = 2;

        /** 搜索商品 */
        int SEARCH_PRODUCT = 3;
    }


    private static class DataInfo {
        public static String strTitle = "";
    }

    private static class RequestInfo {
        public static long categoryId;
        public static int iCurPage;
        public static int iLoadMode = Constant.Comm.MODE_ENTER;
        public static String keyword = "unknow";

        public static int iRequestCategory = IRequestCategory.NORMAL_CATEGORY;
    }

    private static class ResponseInfo {
        /** 类别数据 */
        public static List<CategoryDetailEntitiy> listData = new ArrayList<>();

    }



    public String getTitle() {
        return DataInfo.strTitle;
    }

    public void setTitle(String title) {
        DataInfo.strTitle = title;
    }


    public int getCurLoadMode() {
        return RequestInfo.iLoadMode;
    }


    public List<CategoryDetailEntitiy> getListData() {
        return ResponseInfo.listData;
    }

    public void setRequestData(long categoryId, int iRequestCategory) {
        RequestInfo.iRequestCategory = iRequestCategory;
        RequestInfo.categoryId = categoryId;

    }

    public void setRequestData(int iRequestCategory, String keyword) {
        RequestInfo.iRequestCategory = iRequestCategory;
        RequestInfo.keyword = keyword;
    }


    public void onRefreshLoadData() {
        RequestInfo.iLoadMode = Constant.Comm.MODE_REFRESH;
        loadData(false);
    }


    public void onScrollBottomLoadData() {
        RequestInfo.iLoadMode = Constant.Comm.MODE_LOAD_MORE;
        loadData(true);
    }


    /**
     * 加载数据
     * @param isLoadMore
     */
    private void loadData(boolean isLoadMore) {
        if (!isLoadMore) {
            if (mSlaveLoadDataThread != null) {
                mSlaveLoadDataThread.cancelAll();
                mSlaveLoadDataThread = null;
            }
            RequestInfo.iCurPage = 1;
            mSlaveLoadDataThread = new SlaveLoadDataThread();
            mSlaveLoadDataThread.start();

        } else {
            if (!mSlaveLoadDataThread.isWorking()) {
                RequestInfo.iCurPage++;
                mSlaveLoadDataThread.cancelAll();
                mSlaveLoadDataThread = new SlaveLoadDataThread();
                mSlaveLoadDataThread.start();
            }

        }
    }


    @Override
    public void onCreate() {
        RequestInfo.iLoadMode = Constant.Comm.MODE_ENTER;
        loadData(false);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (mSlaveLoadDataThread !=  null) {
            mSlaveLoadDataThread.cancelAll();
            mSlaveLoadDataThread = null;
        }
    }



    /**
     * 加载数据类别
     */
    private class SlaveLoadDataThread extends NetRequestThread {

        private final String R_TAG = "CategoryDetail_SlaveLoadDataThread";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.CategoryDetailMessage.LOAD_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.CategoryDetailMessage.LOAD_SUCCESS));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.CategoryDetailMessage.LOAD_FAILED));
        }

        @Override
        protected String getJsonBody() {
            String jsonBody = R_TAG + "unkown. error";

            if (RequestInfo.iRequestCategory==IRequestCategory.NORMAL_CATEGORY) {
                jsonBody = String.format("{\"category_id\":%d, \"page\":%d}", RequestInfo.categoryId, RequestInfo.iCurPage);

            } else if (RequestInfo.iRequestCategory==IRequestCategory.TEN_YUAN_CATEGORY) {
                jsonBody = String.format("{\"category_id\":%d, \"page\":%d}", RequestInfo.categoryId, RequestInfo.iCurPage);

            } else if (RequestInfo.iRequestCategory==IRequestCategory.SEARCH_PRODUCT) {
                jsonBody = String.format("{\"keyWord\":\"%s\", \"page\":%d}", RequestInfo.keyword, RequestInfo.iCurPage);

            }

            LogUtils.w(TAG, "jsonBody=%s", jsonBody);
            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "onResponse(). s=%s", s);
            Gson gson  = new Gson();
            List<CategoryDetailEntitiy> list = gson.fromJson(s, new TypeToken<List<CategoryDetailEntitiy>>(){}.getType());
            ResponseInfo.listData = list;
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "volleyError=%s", volleyError.getLocalizedMessage());

        }

        @Override
        protected String getRequestTag() {
            return R_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            String api = "";
            if (RequestInfo.iRequestCategory==IRequestCategory.NORMAL_CATEGORY) {
                api = Constant.WebServiceAPI.REQUEST_CATEGORY_DETAIL;

            } else if (RequestInfo.iRequestCategory==IRequestCategory.TEN_YUAN_CATEGORY){
                api = Constant.WebServiceAPI.REQUEST_CATEGORY_TEN_YUAN_DETAIL;

            } else if (RequestInfo.iRequestCategory == IRequestCategory.SEARCH_PRODUCT) {
                api = Constant.WebServiceAPI.REQUEST_SEARCH_PRODUCT;
            }

            LogUtils.i(R_TAG, "web_api=%s", api);
            return api;
        }

    }





}
