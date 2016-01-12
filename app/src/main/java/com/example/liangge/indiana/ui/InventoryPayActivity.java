package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.InventoryPayBiz;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.comm.Constant;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.ResponsePayInventoryEntity;
import com.example.liangge.indiana.ui.user.LogInActivity;
import com.example.liangge.indiana.ui.user.LogSignInActivity;

/**
 * 订单支付界面
 */
public class InventoryPayActivity extends BaseActivity2 {

    private static final String TAG = InventoryPayActivity.class.getSimpleName();

    /** 共多少夺宝币 */
    private TextView mTxvTotalGold;


    private View mViewInventoryDetailWrapper;

    private View mViewPayResultWrapper;

    private View mViewNetWrapper;

    /** 订单信息 */
    private TextView mTxvInventoryInfo;

    /** 账户余额信息 */
    private TextView mTxvAccountInfo;

    /** 充值 */
    private TextView mTxvReCharge;

    private InventoryPayBiz mInventoryPayBiz;

    /** 一键支付 */
    private Button mViewPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_pay);
        LogUtils.w(TAG, "onCreate()");
        initView();
        initManager();
    }

    private void initManager() {
        mInventoryPayBiz = InventoryPayBiz.getInstance(this);
    }

    private void initView() {
        mViewInventoryDetailWrapper = findViewById(R.id.activity_inventory_detail_wrapper);
        mViewPayResultWrapper = findViewById(R.id.activity_inventory_pay_result_wrapper);
        mViewNetWrapper = findViewById(R.id.net_hint_wrapper);

        mTxvTotalGold = (TextView) findViewById(R.id.activity_inventorypay_txv_total_gold);
        mTxvInventoryInfo = (TextView) findViewById(R.id.activity_inventorypay_inventoryinfo);
        mTxvAccountInfo = (TextView) findViewById(R.id.activity_inventorypay_txv_gold_account_info);
        mTxvReCharge = (TextView) findViewById(R.id.activity_inventory_txv_recharge);

        mViewPay = (Button) findViewById(R.id.activity_inventorypay_btn_requestpay);

        mTxvReCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.w(TAG, "ReCharge");

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mInventoryPayBiz.isLogin()) {
            mInventoryPayBiz.onResume();

        } else {    //没有登录
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();

        }

    }


    @Override
    protected void handleUIMessage(String strUIAction) {
        handleUIAction(strUIAction);
    }


    private void handleUIAction(String uiAciton) {
        if (uiAciton.equals(UIMessageConts.InventoryPay.M_INIT_INVENTORY_INFO) || uiAciton.equals(UIMessageConts.InventoryPay.M_INIT_INVETORY_STATE)) {
            handleUIInit(uiAciton);

        } else if (uiAciton.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_START)
                || uiAciton.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED)
                || uiAciton.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS)) {

            handlePayResult(uiAciton);
        }

    }

    /**
     * 处理订单支付结果
     */
    private void handlePayResult(String uiAction) {
        handleBtnPay(uiAction);

        if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_START)) {
            handleNetUI(Constant.Comm.NET_LOADING, mViewNetWrapper, mViewPayResultWrapper);
            mViewInventoryDetailWrapper.setVisibility(View.VISIBLE);
            mViewPayResultWrapper.setVisibility(View.GONE);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED)) {
            handleNetUI(Constant.Comm.NET_FAILED_NO_NET, mViewNetWrapper, mViewInventoryDetailWrapper);
            mViewInventoryDetailWrapper.setVisibility(View.GONE);
            mViewPayResultWrapper.setVisibility(View.GONE);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS)) {
            LogUtils.e(TAG, "网络返回成功");

            handlePayResultCode();

        }

    }


    private void handleBtnPay(String uiAction) {
        LogUtils.i(TAG, "handleBtnPay(). uiAction=%s", uiAction);
        if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_START)) {
            mViewPay.setEnabled(false);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED)) {
            mViewPay.setEnabled(true);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS)) {
            mViewPay.setEnabled(true);

        } else {
            mViewPay.setEnabled(true);

        }
    }


    /**
     * 处理订单网络请求成功返回信息
     */
    private void handlePayResultCode() {
        ResponsePayInventoryEntity payItem = mInventoryPayBiz.getResponsePayInventoryEntity();
        int iStatus = payItem.getStatus();
        if (iStatus == Constant.InventoryPay.ORDER_PAY_RESULT_CODE_SUCCESS) {
            LogUtils.e(TAG, "订单支付成功");

            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNetWrapper, mViewPayResultWrapper);
            mViewPayResultWrapper.setVisibility(View.VISIBLE);
            mViewInventoryDetailWrapper.setVisibility(View.INVISIBLE);
            TextView txvPayResult = (TextView) findViewById(R.id.pay_result);
            txvPayResult.setText(Html.fromHtml(mInventoryPayBiz.getHumanReadablePayResultInfo()));
        } else {
            //TODO
            mViewNetWrapper.setVisibility(View.GONE);
            mViewPayResultWrapper.setVisibility(View.GONE);
            mViewInventoryDetailWrapper.setVisibility(View.VISIBLE);
            String hint = getResources().getString(R.string.activity_inventory_pay_failed);
            LogUtils.e(TAG, hint);
        }

    }

    /**
     * 处理ui初始显示
     */
    private void handleUIInit(String uiAction) {
        LogUtils.i(TAG, "handleUIInit(). uiAction=%s", uiAction);

        if (uiAction.equals(UIMessageConts.InventoryPay.M_INIT_INVENTORY_INFO)) {
            handleNetUI(Constant.Comm.NET_SUCCESS, mViewNetWrapper, mViewInventoryDetailWrapper);
            mViewPayResultWrapper.setVisibility(View.GONE);

            mTxvTotalGold.setText(mInventoryPayBiz.getTotalPayGolds() + "");
            mTxvInventoryInfo.setText("");
            mTxvInventoryInfo.setText(Html.fromHtml(mInventoryPayBiz.getHummanReadableOrdersInfo()));
            String strBalanceFormat = getResources().getString(R.string.activity_inventory_pay_balance);
            String strBalanceHint = String.format(strBalanceFormat, mInventoryPayBiz.getAccoutGold());
            mTxvAccountInfo.setText(strBalanceHint);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.M_INIT_INVETORY_STATE)) {
            handlePayResult(mInventoryPayBiz.getPayState());

        }


    }


    public void onBtnBack(View view) {
        exitActivityDeal();
        finish();
    }

    @Override
    public void onBackPressed() {
        exitActivityDeal();
        super.onBackPressed();

    }


    private void exitActivityDeal() {
        if (mInventoryPayBiz.isPaying()) {
            String hint = getResources().getString(R.string.activity_inventory_exit_hint);
            LogUtils.toastShortMsg(this, hint);

        }

    }


    //一键支付
    public void onBtnCommitPay(View view) {
        LogUtils.w(TAG, "onBtnCommitPay()");
        mInventoryPayBiz.onBtnCommitPay();
    }


    @Override
    protected void onBtnReload() {
        LogUtils.i(TAG, "onBtnReload()");
        mInventoryPayBiz.onResume();
    }



    @Override
    protected String getDebugTag() {
        return TAG;
    }

    @Override
    protected View getScrollViewWrapper() {
        return null;
    }

    @Override
    protected void onRefreshLoadData() {

    }

    @Override
    protected View getLayoutViewWrapper() {
        return null;
    }
}
