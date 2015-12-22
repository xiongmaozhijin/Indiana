package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    private TextView mTxvProductDetailInfoProcessIngDesc1;
    private ProgressBar mProgressBarProcessIng;
    private TextView mTxvProductDetailInfoProcessIngDesc2;


    /** 已揭晓 */
    private View mViewProcessDoneWrapper;
    private ImageView mImgBingoUserPortain;
    private TextView mTxvBingoInfo;
    private TextView mTxvLunckNumber;
    private Button mBtnCalcDetail;

    /** 揭晓中 */
    private View mViewBingoIngWrapper;

    /** 是否参与到本次夺宝 */
    private TextView mTxvHasJoinHint;

    /** 图文详细 */
    private TextView mTxvMoreDetailInfo;

    /** 所有参与记录 */
    private ListView mPlayRecordListView;


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
        mBtnBack = (ImageButton) findViewById(R.id.activity_productdetailinfo_btn_back);
        mBannerView = (BannerView) findViewById(R.id.activity_productdetailinfo_bannerview);
        mTxvProductInfoTitleDescribe1 = (TextView) findViewById(R.id.activity_productdetailinfo_title_describe1);
        mTxvProductInfoTitleDescribe2 = (TextView) findViewById(R.id.activity_productdetailinfo_title_describe2);

        mViewProcessIngWrapper = findViewById(R.id.activity_productdetailinfo_process_ing_wrapper);
        mTxvProductDetailInfoProcessIngDesc1 = (TextView) findViewById(R.id.activity_productdetailinfo_process_ing_desc1);
        mProgressBarProcessIng = (ProgressBar) findViewById(R.id.activity_productdetailinfo_process_ing_process);
        mTxvProductDetailInfoProcessIngDesc2 = (TextView) findViewById(R.id.activity_productdetailinfo_process_ing_desc2);

        mViewProcessDoneWrapper = findViewById(R.id.activity_productdetailinfo_process_done_wrapper);
        mImgBingoUserPortain = (ImageView) findViewById(R.id.activity_productdetailinfo_bingo_user_portain);
        mTxvBingoInfo = (TextView) findViewById(R.id.activity_productdetailinfo_bingo_info);
        mTxvLunckNumber = (TextView) findViewById(R.id.activity_productdetailinfo_luncky_number);
        mBtnCalcDetail = (Button) findViewById(R.id.activity_productdetailinfo_btn_calc_detail);


        mViewBingoIngWrapper = findViewById(R.id.activity_productdetailinfo_bingo_ing_wrapper);

        mTxvMoreDetailInfo = (TextView) findViewById(R.id.activity_productdetailinfo_more_info);

        mPlayRecordListView = (ListView) findViewById(R.id.activity_productdetailinfo_listview_records);

    }

    private void initViewRes() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTxvMoreDetailInfo.setClickable(true);
        mTxvMoreDetailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.w(TAG, "图文详细");
            }
        });




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
