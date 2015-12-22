package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.ui.widget.BannerView;

/**
 * 商品详情页
 */
public class ProductDetailInfoActivity extends Activity {


    private static final String TAG = ProductDetailInfoActivity.class.getSimpleName();

    private DetailInfoBiz mDetailInfoBiz;

    private UIMsgReceive mUIMsgReceive;

    /** 返回键 */
    private ImageButton mBtnBack;

    /** 图片轮播 */
    private BannerView mBannerView;


    private TextView mTxvProductInfoTitleDescribe1;

    private TextView mTxvProductInfoTitleDescribe2;

    /** 进行中View的Wrapper */
    private View mViewProcessIngWrapper;

    /** 已揭晓 */
    private View mViewProcessDoneWrapper;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_info);

        initView();
        initManager();
    }

    private void initManager() {
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);

    }

    private void initView() {

    }



    private class UIMsgReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String intentAction = intent.getAction();
                if ( (intentAction != null) && (intentAction.equals(UIMessageConts.UI_MESSAGE_ACTION)) ) {
                    handleUIMsg(intent);

                }

            }

        }


    }




    private void handleUIMsg(Intent intent) {
        String uiAciton = intent.getStringExtra(UIMessageConts.UI_MESSAGE_KEY);

        if (uiAciton != null) {
            LogUtils.w(TAG, "uiAciton=%s", uiAciton);
            if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_START)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_FAILED)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_SUCCEED) ) {
                handleProductInfoUI(uiAciton);

            } else if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_WHETHER_PLAY_REQ_START)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_WHETHER_PLAY_REQ_FAILED)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_WHETHER_PLAY_REQ_SUCCESS)) {
                handleWhetherPlayUI(uiAciton);

            } else if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORD_REQ_START)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_FAILED)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED) ) {
                handlePlayRecord(uiAciton);

            }

        }



    }

    private void handlePlayRecord(String uiAciton) {

    }

    private void handleWhetherPlayUI(String uiAciton) {

    }

    private void handleProductInfoUI(String uiAciton) {

    }



    private void registerUIReceive() {
        if (mUIMsgReceive == null) {
            mUIMsgReceive = new UIMsgReceive();
            IntentFilter filter = new IntentFilter(UIMessageConts.UI_MESSAGE_ACTION);
            registerReceiver(mUIMsgReceive, filter);
        }

    }

    private void unregisterUIReceive() {
        if (mUIMsgReceive != null) {
           unregisterReceiver(mUIMsgReceive);
            mUIMsgReceive = null;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerUIReceive();

        mDetailInfoBiz.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterUIReceive();

        mDetailInfoBiz.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailInfoBiz.onDestroy();

    }





}
