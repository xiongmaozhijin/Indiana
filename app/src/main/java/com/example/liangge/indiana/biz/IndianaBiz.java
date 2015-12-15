package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.NetworkUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
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
    private void loadProduct() {
        LogUtils.i(TAG, "loadProduct()");
        String strAction = UIMessageConts.IndianaMessage.MESSAGE_LOADING_PRODUCT_DATA;
        UIMessageEntity info = new UIMessageEntity();
        info.setMessageAction(strAction);
        mMessageManager.sendMessage(info);

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


    public List<BannerInfo> getListBanners() {
        return this.mListBanners;
    }



    public void onStop() {

    }

    public void onDestroy() {

    }

}
