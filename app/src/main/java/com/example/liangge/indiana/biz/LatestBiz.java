package com.example.liangge.indiana.biz;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.NetworkUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.JsonStringRequest;
import com.example.liangge.indiana.comm.net.VolleyBiz;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.LastestBingoEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 最新揭晓管理业务类
 * Created by baoxing on 2015/12/17.
 */
public class LatestBiz extends BaseFragmentBiz{

    private static final String TAG = LatestBiz.class.getSimpleName();

    private static LatestBiz mInstance;

    private static Context mContext;

    /** 消息管理实例 */
    private MessageManager mMessageManager;

    private VolleyBiz mVolleyBiz;

    private SlaveLoadLatestInfoThread mSlaveLoadLatestInfoThread;

    /** 最新揭晓产品数据 */
    private List<LastestBingoEntity> mLatestDatas = new ArrayList<>();

    /**
     * 封装请求数据请求字段
     */
    private static class RequestInfo {
        /** 数据起始字段 */
        public static int startPage = 1;

        /** 是否加载更多 */
        public static boolean bIsLoadMore = false;

    }

    private LatestBiz(Context context) {
        this.mContext = context;
        mMessageManager = MessageManager.getInstance(context);
        mVolleyBiz = VolleyBiz.getInstance();
        mSlaveLoadLatestInfoThread = new SlaveLoadLatestInfoThread();

    }

    public static synchronized LatestBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LatestBiz(context);
        }

        return mInstance;
    }

    /**
     * 请求需要的产品数据
     */
    public void requestDatas() {
        //这里模拟下先    TODO
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    getProductDataTest();
                    UIMessageEntity msgInfo = new UIMessageEntity(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCEED);
                    mMessageManager.sendMessage(msgInfo);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }

        }.start();
    }

    /**
     * @deprecated
     */
    private void getProductDataTest() {
        List<LastestBingoEntity> mListLatestDatas = mLatestDatas;

        String url1 = "http://f.hiphotos.baidu.com/image/pic/item/3bf33a87e950352a230666de5743fbf2b3118b85.jpg";
        String url2 = "http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg";
        String url3 = "http://pic13.nipic.com/20110419/2290512_182044467100_2.jpg";
        String url4 = "http://img3.3lian.com/2013/s1/17/d/15aa.jpg";
        String url5 = "http://pic33.nipic.com/20131008/13661616_190558208000_2.jpg";

        String[] imgs = {url1, url2, url3, url4, url5};

        LastestBingoEntity item1 = new LastestBingoEntity(12,url1, "titledesc惠普电脑1irb1", "tom", "123212", 10, System.currentTimeMillis() + 50*1000);
        LastestBingoEntity item2 = new LastestBingoEntity(144, url2, "title乐视电视descirb2", "小李", "941212", 1, System.currentTimeMillis() + 10*1000);
        LastestBingoEntity item3 = new LastestBingoEntity(3523432,url3, "titledes小米手机irb2", "张李", "321212", 1, System.currentTimeMillis() + 100*1000);

        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);

        LastestBingoEntity entity;
        for (int i=0; i<10; i++) {
            int random = 30 * 1000; //TODO
            long time1 = System.currentTimeMillis() - random;
            long time2 = System.currentTimeMillis() + random;

            entity = new LastestBingoEntity(342341,imgs[i%5], "titleDescribe"+i, "user_for"+i, random+"", i, time1);

            mListLatestDatas.add(entity);
        }

        item1 = new LastestBingoEntity(23213,url4, "titledesc惠普电脑1irb1", "tom", "123212", 10, System.currentTimeMillis() - 50*1000);
        item2 = new LastestBingoEntity(42341, url5, "title乐视电视descirb2", "小李", "941212", 1, System.currentTimeMillis() - 10*1000);
        item3 = new LastestBingoEntity(3141321, url4, "titledes小米手机irb2", "张李", "321212", 1, System.currentTimeMillis() - 100*1000);

        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);
        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);
    }




    //TODO

    /**
     * 加载最新揭晓信息
     */
    public void loadLastDataInfo(boolean isLoadMore) {
        RequestInfo.bIsLoadMore = isLoadMore;

        if (isLoadMore) {
            if (!mSlaveLoadLatestInfoThread.isWorking()) {
                RequestInfo.startPage++;
                mSlaveLoadLatestInfoThread = new SlaveLoadLatestInfoThread();
                mSlaveLoadLatestInfoThread.start();
            }

        } else {
            RequestInfo.startPage = 1;
            mSlaveLoadLatestInfoThread.cancelAll();
            mSlaveLoadLatestInfoThread = new SlaveLoadLatestInfoThread();
            mSlaveLoadLatestInfoThread.start();
        }

    }


    private class SlaveLoadLatestInfoThread extends Thread {

        private static final String REQUEST_TAG = "SlaveLoadLatestInfoThread";

        private boolean bIsWorking = false;

        @Override
        public void run() {
            super.run();
            notifyStart();
            String jsonData = getJsonBody();

            JsonStringRequest request = new JsonStringRequest(Request.Method.POST, Constant.WebServiceAPI.LATEST_PRODUCT_INFO, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    LogUtils.i(TAG, "onResponse=%s", s);
                    Gson gson = new Gson();
                    mLatestDatas = gson.fromJson(s, new TypeToken<List<LastestBingoEntity>>(){}.getType());
                    notifySuccess();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.e(TAG, "onErrorResponse=%s", volleyError.toString() );
                    notifyFail();

                }
            }, jsonData);

            request.setTag(REQUEST_TAG);
            mVolleyBiz.addRequest(request);
        }

        private String getJsonBody() {
            String jsonBody = String.format("{\"page\":%d}", RequestInfo.startPage);

            return jsonBody;
        }


        private void notifyStart() {
            this.bIsWorking = false;
            UIMessageEntity item = new UIMessageEntity();

            if (RequestInfo.bIsLoadMore) {
                item.setMessageAction(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOADING_PRODUCT_DATA_MORE);

            } else {
                item.setMessageAction(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOADING_PRODUCT_DATA);

            }

            mMessageManager.sendMessage(item);
        }

        private void notifySuccess() {
            this.bIsWorking = true;
            UIMessageEntity item = new UIMessageEntity();

            if (RequestInfo.bIsLoadMore) {
                item.setMessageAction(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCEED_MORE);

            } else {
                item.setMessageAction(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCEED);
            }

            mMessageManager.sendMessage(item);
        }

        private void notifyFail() {
            this.bIsWorking = true;
            UIMessageEntity item = new UIMessageEntity();

            if (RequestInfo.bIsLoadMore) {
                item.setMessageAction(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_FAILED_MORE);

            } else {
                item.setMessageAction(UIMessageConts.LatestAnnouncementMessage.MESSAGE_LOAD_PRODUCT_DATA_FAILED);
            }

            mMessageManager.sendMessage(item);
        }


        public boolean isWorking() {
            return this.bIsWorking;
        }

        public void cancelAll() {
            mVolleyBiz.cancelAll(REQUEST_TAG);
        }

    }


    private class SlaveSingleRequestLatestInfoThread extends Thread {


        private LastestBingoEntity mUpdateEntity;

        public SlaveSingleRequestLatestInfoThread(LastestBingoEntity updateEntity) {
            this.mUpdateEntity = updateEntity;
        }

        @Override
        public void run() {
            super.run();
            String jsonData = getJsonBody();

            JsonStringRequest request = new JsonStringRequest(Request.Method.POST, Constant.WebServiceAPI.LATEST_PRODUCT_INFO, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    LogUtils.i(TAG, "onRsponse=%s", s);
                    Gson gson = new Gson();
                    LastestBingoEntity item = gson.fromJson(s, LastestBingoEntity.class);
                    updateLatestBingoInfo(item);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.e(TAG, "volleyError=%s", volleyError.getLocalizedMessage());

                }
            }, jsonData);
        }

        private String getJsonBody() {
            //使用传进的实体期次id构造请求格式 TODO
            String jsonData = String.format("{\"activityId\":[%d], \"page\":%d}", this.mUpdateEntity.getActivityId(), 1);
            return jsonData;
        }

        /**
         * 更新中奖用户信息
         */
        private void updateLatestBingoInfo(LastestBingoEntity entity) {
            LogUtils.w(TAG, "updateLatestBingoInfo()");
            LastestBingoEntity item;
            for (int i=0, len=mLatestDatas.size(); i<len; i++) {
                item = mLatestDatas.get(i);
                if (item.getActivityId() == entity.getActivityId() ) {
                    item.copyfrom(entity);
                    break;
                }
            } //end for

            UIMessageEntity msgItem = new UIMessageEntity(UIMessageConts.LatestAnnouncementMessage.MSG_UPDATE_BINGO_INFO);
            mMessageManager.sendMessage(msgItem);
        }

    }


    /**
     * 返回产品数据
     * @return
     */
    public List<LastestBingoEntity> getProductsData() {
        LogUtils.w(TAG, "getProductsData(), size=%d", this.mLatestDatas.size());
        return this.mLatestDatas;
    }


    @Override
    public void onViewCreated() {

    }


    /**
     * 当当前页面的某个item项的时间到的时候
     * @param entity
     */
    public void onTimeUp(LastestBingoEntity entity) {
       new SlaveSingleRequestLatestInfoThread(entity).start();

    }


    //1.判断网络连接
    //2.加载数据
    @Override
    public void onFirstEnter() {
        loadLastDataInfo(false);
    }

    /**
     * 当进入到LatestFragment界面时
     */
    public void onEnter() {
        loadLastDataInfo(false);
    }

    @Override
    public void onLeave() {

    }




}
