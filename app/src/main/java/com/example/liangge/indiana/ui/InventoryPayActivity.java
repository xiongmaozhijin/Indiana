package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
public class InventoryPayActivity extends BaseActivity {

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


    private UIMsgReceive mUIMsgReceive;

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
    protected void registerUIReceive() {
        if (mUIMsgReceive == null) {
            mUIMsgReceive = new UIMsgReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            registerReceiver(mUIMsgReceive, filter);
        }
    }

    @Override
    protected void unRegisterUIReceive() {
        if (mUIMsgReceive != null) {
            unregisterReceiver(mUIMsgReceive);
            mUIMsgReceive = null;
        }
    }




    private class UIMsgReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action!=null && action.equals(UIMessageConts.UI_MESSAGE_ACTION)) {
                    String uiAciton = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);
                    if (uiAciton != null) {
                        handleUIAction(uiAciton);
                    }
                }
            }

        }


    }

    private void handleUIAction(String uiAciton) {
        if (uiAciton.equals(UIMessageConts.InventoryPay.M_INIT_INVENTORY_INFO)) {
            handleUIInit();
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
        if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_START)) {
//            LogUtils.toastShortMsg(this, "正在支付中...");
            mViewNetWrapper.setVisibility(View.VISIBLE);
            mViewNetWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.GONE);
//            mViewNetWrapper.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            mViewInventoryDetailWrapper.setVisibility(View.VISIBLE);
            mViewPayResultWrapper.setVisibility(View.GONE);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_FAILED)) {
            LogUtils.toastShortMsg(this, "网络出错，支付失败");
            mViewNetWrapper.setVisibility(View.VISIBLE);
            mViewNetWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.GONE);
            mViewNetWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.VISIBLE);
            mViewInventoryDetailWrapper.setVisibility(View.GONE);
            mViewPayResultWrapper.setVisibility(View.GONE);

        } else if (uiAction.equals(UIMessageConts.InventoryPay.INVENTORY_PAY_SUCCESS)) {
            LogUtils.e(TAG, "网络返回成功");

            handlePayResultCode();

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

            mViewNetWrapper.setVisibility(View.GONE);
            mViewPayResultWrapper.setVisibility(View.VISIBLE);
            mViewInventoryDetailWrapper.setVisibility(View.INVISIBLE);
            TextView txvPayResult = (TextView) findViewById(R.id.pay_result);
            txvPayResult.setText(mInventoryPayBiz.getHumanReadablePayResultInfo());
        } else {
            //TODO
            mViewNetWrapper.setVisibility(View.GONE);
            mViewPayResultWrapper.setVisibility(View.GONE);
            mViewInventoryDetailWrapper.setVisibility(View.VISIBLE);
            LogUtils.toastShortMsg(this, "其他错误");
        }

    }

    /**
     * 处理ui初始显示
     */
    private void handleUIInit() {
        mTxvTotalGold.setText(mInventoryPayBiz.getTotalPayGolds()+"");
        mTxvInventoryInfo.setText(mInventoryPayBiz.getHummanReadableOrdersInfo());
        String strBalanceFormat = getResources().getString(R.string.activity_inventory_pay_balance);
        String strBalanceHint = String.format(strBalanceFormat, mInventoryPayBiz.getAccoutGold());
        mTxvAccountInfo.setText(strBalanceHint);

    }


    public void onBtnBack(View view) {
        finish();

    }

    //一键支付
    public void onBtnCommitPay(View view) {
        LogUtils.w(TAG, "onBtnCommitPay()");
        mInventoryPayBiz.onBtnCommitPay();
    }


}
