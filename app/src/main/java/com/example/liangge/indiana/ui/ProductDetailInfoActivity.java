package com.example.liangge.indiana.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.Bizdto;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.ImageViewBiz;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.model.ActivityProductDetailInfoEntity;
import com.example.liangge.indiana.ui.widget.BannerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 商品详情页
 */
public class ProductDetailInfoActivity extends Activity {


    private static final String TAG = ProductDetailInfoActivity.class.getSimpleName();

    private DetailInfoBiz mDetailInfoBiz;

    private UIMsgReceive mUIMsgReceive;

    /** 加载或没有网络View Wrapper */
    private View mViewNetWrapper;

    /** 所有的内容View Wrapper */
    private View mViewAllContentWrapper;


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
    private Button mTxvMoreDetailInfo;

    /** 所有参与记录 */
    private ListView mPlayRecordListView;

    private DisplayImageOptions mDisplayImageOptions;


    private ImageViewBiz mImageViewBiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_info);

        initView();
        initManager();
        initImageLoaderConf();
    }

    /**
     * 配置开源组件ImageLoader的参数
     */
    private void initImageLoaderConf() {
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.main_banner_img_load_empty_uri)
                .showImageOnFail(R.drawable.main_banner_img_load_fail)
                .showImageOnLoading(R.drawable.main_product_item_img_onloading)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();

    }

    private void initManager() {
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);
        mImageViewBiz = ImageViewBiz.getInstance(this);

    }

    private void initView() {
        mViewNetWrapper = findViewById(R.id.activity_detailinfo_net_error_wrapper);
        mViewAllContentWrapper = findViewById(R.id.activity_detailinfo_allcontent_wrapper);

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

        mTxvHasJoinHint = (TextView) findViewById(R.id.activity_hasjoin_txv_hint);

        mViewBingoIngWrapper = findViewById(R.id.activity_productdetailinfo_bingo_ing_wrapper);

        mTxvMoreDetailInfo = (Button) findViewById(R.id.activity_productdetailinfo_more_info);

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
                handleActivityProductDetailInfoUI(uiAciton);

            } else if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_WHETHER_PLAY_REQ_START)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_WHETHER_PLAY_REQ_FAILED)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_WHETHER_PLAY_REQ_SUCCESS)) {

            } else if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORD_REQ_START)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_FAILED)
                    || uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED) ) {
                handlePlayRecord(uiAciton);

            }

        }



    }

    /**
     * 处理参与记录
     * @param uiAciton
     */
    private void handlePlayRecord(String uiAciton) {

    }


    /**
     * 处理活动及产品详情
     * @param uiAciton
     */
    private void handleActivityProductDetailInfoUI(String uiAciton) {
        if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_START)) {
            mViewAllContentWrapper.setVisibility(View.GONE);
            mViewNetWrapper.setVisibility(View.VISIBLE);
            mViewNetWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.VISIBLE);
            mViewNetWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.GONE);

        } else if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_FAILED)) {
            mViewAllContentWrapper.setVisibility(View.GONE);
            mViewNetWrapper.setVisibility(View.VISIBLE);
            mViewNetWrapper.findViewById(R.id.comm_loading_icon).setVisibility(View.GONE);
            mViewNetWrapper.findViewById(R.id.comm_not_network_hint).setVisibility(View.VISIBLE);

        } else if (uiAciton.equals(UIMessageConts.DetailInfo.M_DETAILINFO_PRODUCT_ACTIVITY_REQ_SUCCEED)) {
            handleUIActivityProductInfoLoadSuccess();

        }

    }

    /**
     * 加载成功，适配信息
     */
    private void handleUIActivityProductInfoLoadSuccess() {
        //TODO
        mViewAllContentWrapper.setVisibility(View.VISIBLE);
        mViewNetWrapper.setVisibility(View.GONE);

        ActivityProductDetailInfoEntity detailEntity = mDetailInfoBiz.getDetailEntity();

        mBannerView.setDatasAndNotify(Bizdto.changeToBannerInfo(detailEntity.getProductImgUrls()));
        mTxvProductInfoTitleDescribe2.setText(detailEntity.getTitleDescribe());

        int activityState = detailEntity.getActivityState();
        if (activityState == 0) {   //已揭晓
            mViewProcessIngWrapper.setVisibility(View.GONE);
            mViewProcessDoneWrapper.setVisibility(View.VISIBLE);
            mViewBingoIngWrapper.setVisibility(View.GONE);

            mTxvProductInfoTitleDescribe1.setText(getResources().getString(R.string.actiivty_state_runlottory_done));

//            LogUtils.e(TAG, "imgUrl=%s, view=%s", detailEntity.getBingoUserPortain(), mImgBingoUserPortain.toString());

            ImageLoader.getInstance().displayImage(detailEntity.getBingoUserPortain(), mImgBingoUserPortain, mDisplayImageOptions);


            String bingUserInfoFormat = getResources().getString(R.string.activity_detail_bingouser_info);
            String bingUserInfo = String.format(bingUserInfoFormat,
                    detailEntity.getBingoUserName(), detailEntity.getBingoUserAddress(),
                    detailEntity.getBingoBuyCnts(), detailEntity.getHumanAlreadyRunLotteryTime());
            mTxvBingoInfo.setText(bingUserInfo);
            String luckyNumFormat = getResources().getString(R.string.activity_productdetailinfo_lucky_number);
            String luckyNum = String.format(luckyNumFormat, detailEntity.getLuckyNumber());
            mTxvLunckNumber.setText(luckyNum);


        } else if (activityState == 1) {    //进行中
            mViewProcessIngWrapper.setVisibility(View.VISIBLE);
            mViewProcessDoneWrapper.setVisibility(View.GONE);
            mViewBingoIngWrapper.setVisibility(View.GONE);

            mTxvProductInfoTitleDescribe1.setText(getResources().getString(R.string.actiivty_state_runlottory_play_ing));
            String desc1Format = getResources().getString(R.string.activity_productdetailinfo_progressing_des1);
            String desc2Format = getResources().getString(R.string.activity_productdetailinfo_progressing_des2);
            String desc1 = String.format(desc1Format, detailEntity.getNeedPeople());
            String desc2 = String.format(desc2Format, detailEntity.getLackPeople());
            float progress1 = (float)(detailEntity.getNeedPeople()-detailEntity.getLackPeople()) / detailEntity.getNeedPeople();
            int progress = (int) (progress1 * 1000);
            LogUtils.e(TAG, "all=%d, lack=%d, pro1=%f, pro=%d", detailEntity.getNeedPeople(), detailEntity.getLackPeople(), progress1, progress);
            mTxvProductDetailInfoProcessIngDesc1.setText(desc1);
            mTxvProductDetailInfoProcessIngDesc2.setText(desc2);
            mProgressBarProcessIng.setProgress(progress);

        } else if (activityState == 2) {    //揭晓中
            mViewProcessIngWrapper.setVisibility(View.GONE);
            mViewProcessDoneWrapper.setVisibility(View.GONE);
            mViewBingoIngWrapper.setVisibility(View.VISIBLE);

            mTxvProductInfoTitleDescribe1.setText(getResources().getString(R.string.actiivty_state_runlottory_ing));

        }

        if (detailEntity.isPlay()) {
            String hintFormat = getResources().getString(R.string.activity_productdetailinfo_hasjoin_hint_join_2);
            String hint  = String.format(hintFormat, detailEntity.getMyIndianaAmount());
            mTxvHasJoinHint.setText(hint);

        } else {
            String hint = getResources().getString(R.string.activity_productdetailinfo_hasjoin_hint);
            mTxvHasJoinHint.setText(hint);

        }



    }

    //图文详情
    public void onBtnMoreProductInfo(View view) {
        LogUtils.w(TAG, "onBtnMoreProductInfo()");
        String[] imgUrlArrays = mDetailInfoBiz.getDetailEntity().getProductDetailImgs();
        if (imgUrlArrays != null) {
            String imgUrl = imgUrlArrays[0];
            mImageViewBiz.setDisplayImageView(imgUrl);
            Intent intent = new Intent(this, ImageViewActivity.class);
            startActivity(intent);
        }

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

    public void onBtnBack(View view) {
        finish();
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
