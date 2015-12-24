package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.InventoryPayBiz;
import com.example.liangge.indiana.biz.PersonalCenterBiz;
import com.example.liangge.indiana.biz.ShoppingCartBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;

/**
 * 订单支付界面
 */
public class InventoryPayActivity extends BaseActivity {

    private static final String TAG = InventoryPayActivity.class.getSimpleName();

    /** 共多少夺宝币 */
    private TextView mTxvTotalGold;

    /** 订单信息 */
    private TextView mTxvInventoryInfo;

    /** 账户余额信息 */
    private TextView mTxvAccountInfo;

    /** 充值 */
    private TextView mTxvReCharge;

    private InventoryPayBiz mInventoryPayBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_pay);
        initView();
        initManager();
    }

    private void initManager() {
        mInventoryPayBiz = InventoryPayBiz.getInstance(this);
    }

    private void initView() {
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
            finish();

        }

    }

    @Override
    protected void registerUIReceive() {

    }

    @Override
    protected void unRegisterUIReceive() {

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
        }

    }

    /**
     * 处理ui初始显示
     */
    private void handleUIInit() {
        mTxvTotalGold.setText(mInventoryPayBiz.getTotalPayGolds()+"");
        mTxvInventoryInfo.setText(mInventoryPayBiz.getHummanReadableOrdersInfo());
        mTxvAccountInfo.setText(mInventoryPayBiz.getAccoutGold()+"");

    }


    public void onBtnBack(View view) {
        finish();
    }

    //一键支付
    public void onBtnCommitPay(View view) {

    }


}
