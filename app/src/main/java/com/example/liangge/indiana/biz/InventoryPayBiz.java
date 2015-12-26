package com.example.liangge.indiana.biz;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.liangge.indiana.R;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.comm.net.NetRequestThread;
import com.example.liangge.indiana.model.InventoryEntity;
import com.example.liangge.indiana.model.PayRequestEntity;
import com.example.liangge.indiana.model.ResponsePayInventoryEntity;
import com.example.liangge.indiana.model.ResponsePayInventoryItemEntity;
import com.example.liangge.indiana.model.UIMessageEntity;
import com.google.gson.Gson;

import java.util.List;

/**
 * 订单支付类
 * Created by baoxing on 2015/12/24.
 */
public class InventoryPayBiz {


    private static final String TAG = InventoryPayBiz.class.getSimpleName();

    public static Context mContext;

    public static InventoryPayBiz mInstance;

    private static ShoppingCartBiz mShoppingCartBiz;

    private static PersonalCenterBiz mPersonalCenterBiz;

    private static MessageManager mMessageManager;

    private SlavePayOrdersThead mSlavePayOrdersThead;


    private InventoryPayBiz(Context context) {
        this.mContext = context;
        mShoppingCartBiz = ShoppingCartBiz.getInstance(context);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(context);
        mMessageManager = MessageManager.getInstance(context);

        initRes();
    }



    private static class DataInfo {

    }

    private static class ResponseInfo {
        public static ResponsePayInventoryEntity responsePayResult = new ResponsePayInventoryEntity();

    }

    private static class RequestInfo {

    }





    private void initRes() {
        mSlavePayOrdersThead = new SlavePayOrdersThead();
    }


    public static synchronized InventoryPayBiz getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InventoryPayBiz(context);
        }

        return mInstance;
    }


    /**
     * 当进入到这个activity时
     */
    public void onResume() {
        notifyInitInventoryInfo();

    }

    /**
     * 初始化订单信息
     */
    private void notifyInitInventoryInfo() {
        UIMessageEntity item = new UIMessageEntity(UIMessageConts.InventoryPay.M_INIT_INVENTORY_INFO);
        mMessageManager.sendMessage(item);

    }


    /** 是否登录 */
    public boolean isLogin() {
        return mPersonalCenterBiz.isLogin();
    }


    /**
     * 一键支付
     * @return
     */
    public void onBtnCommitPay() {
        if (!mSlavePayOrdersThead.isWorking()) {
            mSlavePayOrdersThead.cancelAll();
            mSlavePayOrdersThead = new SlavePayOrdersThead();
            mSlavePayOrdersThead.start();
        }
    }



    public ResponsePayInventoryEntity getResponsePayInventoryEntity() {
        return ResponseInfo.responsePayResult;
    }


    /**
     * 一共需要支付多少金币
     * @return
     */
    public int getTotalPayGolds() {
        return mShoppingCartBiz.getTotalCost();
    }

    /**
     * 得到可读的订单信息
     * @return
     */
    public String getHummanReadableOrdersInfo() {
        StringBuilder sb = new StringBuilder();
        List<InventoryEntity> listInventoryData = mShoppingCartBiz.getListInventoryData();
        InventoryEntity item;
        for (int i=0, len=listInventoryData.size(); i<len; i++) {
            item = listInventoryData.get(i);
            sb.append("\n");
            sb.append(item.getTitleName());
            sb.append("\t\t");
            sb.append(item.getBuyCounts());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 得到可读的支付清单信息
     * @return
     */
    public String getHumanReadablePayResultInfo() {
        StringBuilder sb = new StringBuilder();
        ResponsePayInventoryEntity payResult = ResponseInfo.responsePayResult;
        List<ResponsePayInventoryItemEntity> orderList = payResult.getShopList();
        String readFormat = mContext.getResources().getString(R.string.activity_inventory_pay_payresult);

        ResponsePayInventoryItemEntity item;
        String strReadItem;
        for (int i=0, len=orderList.size(); i<len; i++) {
            item = orderList.get(i);
            strReadItem = String.format(readFormat, item.getTitleDesc(), item.getAmount(), item.getAmount_bought());
            sb.append(strReadItem);
        }

        return sb.toString();
    }


    /**
     * 获取账户金额
     * @return
     */
    public int getAccoutGold() {
        return mPersonalCenterBiz.getUserInfo().getBalance();
    }


    /**
     * 订单支付
     */
    private class SlavePayOrdersThead extends NetRequestThread {

        private static final String REQUEST_TAG = "SlavePayOrdersThead";

        @Override
        protected void notifyStart() {
            super.notifyStart();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.InventoryPay.INVENTORY_PAY_START));
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS));
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED));
        }

        @Override
        protected String getJsonBody() {
            PayRequestEntity payItem = Bizdto.changeToPayRequestEntity(mPersonalCenterBiz.getUserInfo().getUserId(),
                                                mPersonalCenterBiz.getUserInfo().getToken(), mShoppingCartBiz.getListInventoryData() );
            Gson gson = new Gson();
            String jsonBody = gson.toJson(payItem);

            LogUtils.w(TAG, "getJsonBody=%s", jsonBody);

            return jsonBody;
        }

        @Override
        protected void onResponseListener(String s) {
            LogUtils.w(TAG, "SlavePayOrdersThead: onResposne=%s", s);
            Gson gson = new Gson();
            ResponsePayInventoryEntity responsePayResult = gson.fromJson(s, ResponsePayInventoryEntity.class);
            ResponseInfo.responsePayResult = responsePayResult;

            //TODO 模拟延时支付效果
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //TODO
            mShoppingCartBiz.clearShoppingCart();
        }

        @Override
        protected void onResponseErrorListener(VolleyError volleyError) {
            LogUtils.e(TAG, "SlavePayOrdersThead: VolleyError=%s", volleyError.getLocalizedMessage());
        }

        @Override
        protected String getRequestTag() {
            return REQUEST_TAG;
        }

        @Override
        protected String getWebServiceAPI() {
            //TODO
            return Constant.WebServiceAPI.REQUEST_PAY_ORDERS;
        }
    }








}
