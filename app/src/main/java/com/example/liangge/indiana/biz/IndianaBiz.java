package com.example.liangge.indiana.biz;

import android.content.Context;

import com.example.liangge.indiana.comm.NetworkUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.UIMessageEntity;

/**
 * Created by baoxing on 2015/12/15.
 */
public class IndianaBiz {

    private Context mContext;

    private static IndianaBiz mInstance;

    private MessageManager mMessageManager;

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
        boolean bIsNetOK = NetworkUtils.isNetworkConnected(mContext);

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
        String strAction = UIMessageConts.IndianaMessage.MESSAGE_LOADING_PRODUCT_DATA;
        UIMessageEntity info = new UIMessageEntity();
        info.setMessageAction(strAction);
        mMessageManager.sendMessage(info);

    }

    /**
     * 加载广告轮播
     */
    private void loadBanner() {
        String strAction = UIMessageConts.IndianaMessage.MESSAGE_LOADING_BANNER;
        //TODO
    }


    public void onStop() {

    }

    public void onDestroy() {

    }

}
