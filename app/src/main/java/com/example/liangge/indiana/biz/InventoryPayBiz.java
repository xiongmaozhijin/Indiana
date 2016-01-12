package com.example.liangge.indiana.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

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



    private static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String actionMsg = (String) msg.obj;
            if (actionMsg == null) {
                return;
            }
            LogUtils.w(TAG, "handleMessage=%s", actionMsg);
            if (actionMsg.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED)) {
                String hint = mContext.getResources().getString(R.string.activity_inventory_pay_net_failed);
                LogUtils.toastShortMsg(mContext, hint);

            } else if (actionMsg.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_START)) {
                String hint = mContext.getResources().getString(R.string.activity_inventory_pay_ing);
                LogUtils.toastShortMsg(mContext, hint);

            } else if (actionMsg.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS)) {
                int iStatus = ResponseInfo.responsePayResult.getStatus();
                if (iStatus == Constant.InventoryPay.ORDER_PAY_RESULT_CODE_SUCCESS) {
                    //支付成功
                    String hint = mContext.getResources().getString(R.string.activity_inventory_pay_success);
                    LogUtils.toastShortMsg(mContext, hint);

                } else {
                    //支付失败
                    String hint;
                    if (ResponseInfo.responsePayResult.getMsg()==null || ResponseInfo.responsePayResult.getMsg().equals("")) {
                        hint = mContext.getResources().getString(R.string.activity_inventory_pay_failed);
                    } else {
                        hint = ResponseInfo.responsePayResult.getMsg();
                    }

                    LogUtils.toastShortMsg(mContext, hint);
                }
            }

        }
    };



    private InventoryPayBiz(Context context) {
        this.mContext = context;
        mShoppingCartBiz = ShoppingCartBiz.getInstance(context);
        mPersonalCenterBiz = PersonalCenterBiz.getInstance(context);
        mMessageManager = MessageManager.getInstance(context);

        initRes();
    }



    private static class DataInfo {
        /** 支付状态 */
        public static String payState = "";

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
     * 得到支付状态
     * @return
     */
    public String getPayState() {
        return DataInfo.payState;
    }

    /**
     * 是否正在支付
     * @return
     */
    public boolean isPaying() {
        boolean r = false;
        if (mSlavePayOrdersThead != null) {
            r = mSlavePayOrdersThead.isWorking();
        }

        LogUtils.i(TAG, "isPaying=%b", r);
        return r;
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
        UIMessageEntity item2 = new UIMessageEntity(UIMessageConts.InventoryPay.M_INIT_INVETORY_STATE);
        mMessageManager.sendMessage(item2);

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
        String format = mContext.getResources().getString(R.string.activity_inventory_pay_orders_show);
        String strOrdersItem;
        InventoryEntity item;
        for (int i=0, len=listInventoryData.size(); i<len; i++) {
            item = listInventoryData.get(i);
            strOrdersItem = String.format(format, item.getTitleName(), item.getBuyCounts() );
            sb.append(strOrdersItem);
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

        private void sendHandleUIMessage(String uiMessage) {
            LogUtils.w(TAG, "sendHandleUIMessage=%s", uiMessage);
            Message msg = Message.obtain();
            msg.obj = uiMessage;
            mHandler.sendMessage(msg);
        }

        @Override
        public void cancelAll() {
            super.cancelAll();
            DataInfo.payState = "";
        }

        @Override
        protected void notifyStart() {
            super.notifyStart();
            DataInfo.payState = UIMessageConts.InventoryPay.INVENTORY_PAY_START;
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.InventoryPay.INVENTORY_PAY_START));
            sendHandleUIMessage(UIMessageConts.InventoryPay.INVENTORY_PAY_START);
        }

        @Override
        protected void notifySuccess() {
            super.notifySuccess();
            DataInfo.payState = "";
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS));
            sendHandleUIMessage(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS);
        }

        @Override
        protected void notifyFail() {
            super.notifyFail();
            DataInfo.payState = "";
            mMessageManager.sendMessage(new UIMessageEntity(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED));
            sendHandleUIMessage(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED);
        }

        @Override
        protected String getJsonBody() {
            PayRequestEntity payItem = Bizdto.changeToPayRequestEntity(mPersonalCenterBiz.getUserInfo().getId(),
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //TODO
            if (responsePayResult.getStatus() == Constant.InventoryPay.ORDER_PAY_RESULT_CODE_SUCCESS) {
                mShoppingCartBiz.clearShoppingCart();

            }

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
