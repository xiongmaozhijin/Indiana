package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.NetworkUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.ActivityProductItemEntity;
import com.example.liangge.indiana.model.BannerInfo;
import com.example.liangge.indiana.model.UIMessageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2015/12/15.
 */
public class IndianaBiz {

    private static final String TAG = IndianaBiz.class.getSimpleName();

    private Context mContext;

    private static IndianaBiz mInstance;

    /** 消息管理类*/
    private MessageManager mMessageManager;

    /** 图片轮播数集 */
    private List<BannerInfo> mListBanners;

    /** 产品数据 */
    private List<ActivityProductItemEntity> mListProducts;

    private IndianaBiz(Context context) {
        this.mContext = context;
        mMessageManager = MessageManager.getInstance(context);
    }

    public static synchronized IndianaBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new IndianaBiz(context);
        }

        return mInstance;
    }

    /**
     * 做一些初始化的工作，比如检查网络，或者从本地取数据
     */
    public void init() {
        LogUtils.i(TAG, "init()");
        boolean bIsNetOK = NetworkUtils.isNetworkConnected(mContext.getApplicationContext());

        if (!bIsNetOK) {
            String strAction = UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK;
            UIMessageEntity info = new UIMessageEntity();
            info.setMessageAction(strAction);
            mMessageManager.sendMessage(info);

        } else {
            loadBanner();
            loadProduct();
        }
    }

    /**
     * 加载产品
     */
    public void loadProduct() {
        LogUtils.i(TAG, "loadProduct()");
        String strAction = UIMessageConts.IndianaMessage.MESSAGE_LOADING_PRODUCT_DATA;
        UIMessageEntity info = new UIMessageEntity();
        info.setMessageAction(strAction);
        mMessageManager.sendMessage(info);
        loadProductTest();
    }

    /**
     * @deprecated
     */
    private void loadProductTest() {
      new Thread(){
          @Override
      public void run() {
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

              UIMessageEntity info = new UIMessageEntity(UIMessageConts.IndianaMessage.MESSAGE_LOAD_PRODUCT_DATA_SUCCESS);
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

}
