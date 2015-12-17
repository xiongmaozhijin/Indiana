package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.NetworkUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.LastestBingoEntity;
import com.example.liangge.indiana.model.UIMessageEntity;

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

    /** 最新揭晓产品数据 */
    private List<LastestBingoEntity> mLatestDatas = new ArrayList<>();

    /**
     * 封装请求数据请求字段
     */
    private static class RequestFiled {
        /** 数据起始字段 */
        public int iLimitStart = 0;

    }

    private LatestBiz(Context context) {
        this.mContext = context;
        mMessageManager = MessageManager.getInstance(context);

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

        LastestBingoEntity item1 = new LastestBingoEntity(url1, "titledesc惠普电脑1irb1", "tom", "123212", 10, System.currentTimeMillis() + 50*1000);
        LastestBingoEntity item2 = new LastestBingoEntity(url2, "title乐视电视descirb2", "小李", "941212", 1, System.currentTimeMillis() + 10*1000);
        LastestBingoEntity item3 = new LastestBingoEntity(url3, "titledes小米手机irb2", "张李", "321212", 1, System.currentTimeMillis() + 100*1000);

        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);

        LastestBingoEntity entity;
        for (int i=0; i<10; i++) {
            int random = 30 * 1000; //TODO
            long time1 = System.currentTimeMillis() - random;
            long time2 = System.currentTimeMillis() + random;

            entity = new LastestBingoEntity(imgs[i%5], "titleDescribe"+i, "user_for"+i, random+"", i, time1);

            mListLatestDatas.add(entity);
        }

        item1 = new LastestBingoEntity(url4, "titledesc惠普电脑1irb1", "tom", "123212", 10, System.currentTimeMillis() - 50*1000);
        item2 = new LastestBingoEntity(url5, "title乐视电视descirb2", "小李", "941212", 1, System.currentTimeMillis() - 10*1000);
        item3 = new LastestBingoEntity(url4, "titledes小米手机irb2", "张李", "321212", 1, System.currentTimeMillis() - 100*1000);

        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);
        mListLatestDatas.add(item1);
        mListLatestDatas.add(item2);
        mListLatestDatas.add(item3);
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

    //1.判断网络连接
    //2.加载数据
    @Override
    public void onFirstEnter() {
        boolean bIsNetConn = NetworkUtils.isNetworkConnected(mContext);
        if (!bIsNetConn) {
            UIMessageEntity messageEntity = new UIMessageEntity(UIMessageConts.CommResponse.MESSAGE_COMM_NO_NETWORK);
            mMessageManager.sendMessage(messageEntity);

        } else {
            requestDatas();

        }

    }

    /**
     * 当进入到LatestFragment界面时
     */
    public void onEnter() {

    }

    @Override
    public void onLeave() {

    }




}
