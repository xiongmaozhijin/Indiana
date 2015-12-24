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
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/15.
 */
public class IndianaBiz extends BaseFragmentBiz{

    private static final String TAG = IndianaBiz.class.getSimpleName();

    private Context mContext;

    private static IndianaBiz mInstance;

    /** 消息管理类*/
    private MessageManager mMessageManager;

    /** 图片轮播数集 */
    private List<BannerInfo> mListBanners;

    /** 产品数据 */
    private List<ActivityProductItemEntity> mListProducts;

    private VolleyBiz mVolleyBiz;

    /** 加载活动信息线程 */
    private SlaveLoadActivityProductInfoThread mSlaveLoadActivityProductInfoThread;

    private IndianaBiz(Context context) {
        this.mContext = context;
        mMessageManager = MessageManager.getInstance(context);
        mVolleyBiz = VolleyBiz.getInstance();
        mSlaveLoadActivityProductInfoThread = new SlaveLoadActivityProductInfoThread();
    }

    /**
     * 要保存的一些数据
     */
    private static class DataInfo {

    }

    /**
     * 进行请求的相关信息
     */
    private static class RequestInfo {

        /** 当前标签页 */
        public static String iCurTag = Constant.IndianaFragment.TAG_HOTS;

        /** 当前的页码 */
        public static int iCurPage = 1;

        public static boolean bIsLoadMore = false;

    }






    public static synchronized IndianaBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new IndianaBiz(context);
        }

        return mInstance;
    }


    /**
     * @deprecated
     * 加载活动产品信息
     */
    public void loadActivityProductInfo() {
        LogUtils.i(TAG, "loadActivityProductInfo()");
        loadProductTest();
    }


    //TODO

    /**
     * 加载活动标签信息/及加载更多
     * @param tag   标签页
     */
    public void loadActivityProductInfo(String tag, boolean isLoadMore) {
        LogUtils.w(TAG, "loadActivityProductInfo().tag=%s, isLoadMore=%b", tag, isLoadMore);
//        loadProductTest();

        RequestInfo.bIsLoadMore = isLoadMore;

        if (isLoadMore) {
            if (!mSlaveLoadActivityProductInfoThread.isWorking()) {
                RequestInfo.iCurPage++;
                mSlaveLoadActivityProductInfoThread = new SlaveLoadActivityProductInfoThread();
                mSlaveLoadActivityProductInfoThread.start();
            }

        } else {
            RequestInfo.iCurTag = tag;
            RequestInfo.iCurPage = 1;
            mSlaveLoadActivityProductInfoThread.cancelNetRequest();
            mSlaveLoadActivityProductInfoThread = new SlaveLoadActivityProductInfoThread();
            mSlaveLoadActivityProductInfoThread.start();

        }

    }

    /**
     * 加载活动产品信息
     */
    private class SlaveLoadActivityProductInfoThread extends Thread {

        /** 请求TAG，用于取消 */
        private static final String REQUEST_TAG = "SlaveLoadActivityProductInfoThread";

        private boolean bIsWorking = false;

        @Override
        public void run() {
            super.run();
            LogUtils.w(TAG, "SlaveLoadActivityProductInfoThread#run()");
            notifyStart();

            String jsonBody = getJsonBody();
            JsonStringRequest request = new JsonStringRequest(Request.Method.POST, Constant.WebServiceAPI.INDIANA_GOODS_API, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    LogUtils.i(TAG, "onResposne().s=%s", s);

                    Gson gson = new Gson();
                    mListProducts = gson.fromJson(s, new TypeToken<List<ActivityProductItemEntity>>(){}.getType());

                    notifySuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.e(TAG, "volleyError=%s", volleyError.toString());

                    notifyFail();
                }
            }, jsonBody);

            request.setTag(REQUEST_TAG);
            mVolleyBiz.addRequest(request);

        }   //end run

        private String getJsonBody() {
           return String.format("{\"type\":\"%s\", \"page\":%d}", RequestInfo.iCurTag, RequestInfo.iCurPage);
        }


        /**
         * 取消网络请求
         */
        public void cancelNetRequest() {
            mVolleyBiz.cancelAll(REQUEST_TAG);

        }

        private void notifyStart() {
            this.bIsWorking = true;

            UIMessageEntity item = new UIMessageEntity();
            if (RequestInfo.bIsLoadMore) {
                item.setMessageAction(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_START);

            } else {
                item.setMessageAction(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_START);
            }
            mMessageManager.sendMessage(item);
        }

        private void notifyFail() {
            this.bIsWorking = false;

            UIMessageEntity item =  new UIMessageEntity();
            if (RequestInfo.bIsLoadMore) {
                item.setMessageAction(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_FAIL);

            } else {
                item.setMessageAction(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_FAIL);
            }

            mMessageManager.sendMessage(item);
        }

        private void notifySuccess() {
            this.bIsWorking = false;

            UIMessageEntity item = new UIMessageEntity();
            if (RequestInfo.bIsLoadMore) {
                item.setMessageAction(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_SUCCESS);

            } else {
                item.setMessageAction(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_SUCCESS);

            }

            mMessageManager.sendMessage(item);
        }

        /**
         * 是否还在工作
         * @return
         */
        public boolean isWorking() {
            return this.bIsWorking;
        }

    }





    /**
     * @deprecated
     */
    private void loadProductTest() {
      new Thread(){
          @Override
      public void run() {
              UIMessageEntity msg1 = new UIMessageEntity(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_START);
              mMessageManager.sendMessage(msg1);

              try {
                  Thread.sleep(5000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              List<ActivityProductItemEntity> list = new ArrayList<>();
              String url1 = "http://www.bz55.com/uploads/allimg/150309/139-150309101F7.jpg";
              String url2 = "http://pic33.nipic.com/20131008/13661616_190558208000_2.jpg";
              String url3 = "http://pic13.nipic.com/20110419/2290512_182044467100_2.jpg";
              String url4 = "http://img3.3lian.com/2013/s1/17/d/15aa.jpg";

              ActivityProductItemEntity item1 = new ActivityProductItemEntity(url1, "name1", 23, "23%");
              ActivityProductItemEntity item2 = new ActivityProductItemEntity(url2, "name2", 100, "100%");
              ActivityProductItemEntity item3 = new ActivityProductItemEntity(url3, "name3", 45, "45%");
              list.add(item1);
              list.add(item2);
              list.add(item3);
              for (int i=0; i<9; i++) {
                  ActivityProductItemEntity item = new ActivityProductItemEntity(url4, "产品名"+i, i, i+"%");
                  list.add(item);
              }
              list.add(item3);

              mListProducts = list;

              UIMessageEntity info = new UIMessageEntity(UIMessageConts.IndianaMessage.MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_SUCCESS);
              mMessageManager.sendMessage(info);
          }
      }.start();
    }

    /**
     * 加载广告轮播
     */
    private void loadBanner() {
        LogUtils.i(TAG, "loadBanner()");
        String strAction = UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER;
        UIMessageEntity info = new UIMessageEntity();
        info.setMessageAction(strAction);
        mMessageManager.sendMessage(info);
        loadBannerTest();
    }

    /**
     * @deprecated
     */
    private void loadBannerTest() {
      new Thread(){
          @Override
      public void run() {
              try {
                  Thread.sleep(4000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              List<BannerInfo> lists = new ArrayList<>();
              BannerInfo item1 = new BannerInfo();
              item1.setImgUrl("http://f.hiphotos.baidu.com/image/pic/item/3bf33a87e950352a230666de5743fbf2b3118b85.jpg");
              lists.add(item1);

              BannerInfo item2 = new BannerInfo();
              item2.setImgUrl("http://b.hiphotos.baidu.com/image/pic/item/0823dd54564e925838c205c89982d158ccbf4e26.jpg");
              lists.add(item2);

              mListBanners = lists;


              mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.IndianaMessage.MESSAGE_LOAD_BANNER_SUCCESS));
          }
      }.start();

    }

    /**
     * 获取图片轮播的数据
     * @return
     */
    public List<BannerInfo> getListBanners() {
        return this.mListBanners;
    }

    /**
     * 获取产品数据
     * @return
     */
    public List<ActivityProductItemEntity> getListProducts() {
        return this.mListProducts;
    }


    public void onStop() {

    }

    public void onDestroy() {

    }

    @Override
    public void onViewCreated() {

    }

    //做一些初始化的工作，比如检查网络，或者取数据
    @Override
    public void onFirstEnter() {
        LogUtils.i(TAG, "onFirstEnter()");
        boolean bIsNetOK = NetworkUtils.isNetworkConnected(mContext.getApplicationContext());

        if (!bIsNetOK) {
            String strAction = UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK;
            UIMessageEntity info = new UIMessageEntity();
            info.setMessageAction(strAction);
            mMessageManager.sendMessage(info);

        } else {
            loadBanner();
//            loadActivityProductInfo();

        }

    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }
}
