package com.example.liangge.indiana.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.DetailInfoBiz;
import com.example.liangge.indiana.biz.ShareOrdersBiz;
import com.example.liangge.indiana.model.ShareOrdersEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baoxing on 2016/1/21.
 */
public class ShareOrderDetailActivity extends SimpleAdapterBaseActivity2 {

    private static final String TAG = ShareOrderDetailActivity.class.getSimpleName();

    private ShareOrdersEntity mShareOrdersEntity;

    private ShareOrdersBiz mShareOrdersBiz;

    private DetailInfoBiz mDetailInfoBiz;

    private TextView mTxvShareTitle;
    private TextView mTxvShareAuthor;
    private TextView mTxvShareTime;
    private TextView mTxvGoodName;
    private TextView mTxvActivityId;
    private TextView mTxvBuyCnt;
    private TextView mTxvLuckyNumber;
    private TextView mTxvRunLotteryTime;

    private TextView mTxvShareContent;

    private List<ImageView> mImgList = new ArrayList<>();
    private ImageView mImg0;
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;
    private ImageView mImg4;

    private static DisplayImageOptions mDisplayImageOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);

        initView();
        initManager();
        initRes();

        initUI();
    }

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


    private void initView() {
        mTxvShareTitle = (TextView) findViewById(R.id.share_detail_title);
        mTxvShareAuthor = (TextView) findViewById(R.id.share_detail_author);
        mTxvShareTime = (TextView) findViewById(R.id.share_detail_time);
        mTxvGoodName = (TextView) findViewById(R.id.share_detail_goods);
        mTxvActivityId = (TextView) findViewById(R.id.one_title_period_text);
        mTxvBuyCnt = (TextView) findViewById(R.id.share_detail_num);
        mTxvLuckyNumber = (TextView) findViewById(R.id.share_detail_code);
        mTxvRunLotteryTime = (TextView) findViewById(R.id.share_detail_reveal_time);
        mTxvShareContent = (TextView) findViewById(R.id.share_detail_content);

        mImg0 = (ImageView) findViewById(R.id.img0);
        mImg1 = (ImageView) findViewById(R.id.img1);
        mImg2 = (ImageView) findViewById(R.id.img2);
        mImg3 = (ImageView) findViewById(R.id.img3);
        mImg4 = (ImageView) findViewById(R.id.img4);
        mImgList.add(mImg0);
        mImgList.add(mImg1);
        mImgList.add(mImg2);
        mImgList.add(mImg3);
        mImgList.add(mImg4);
    }

    private void initUI() {
        if (mShareOrdersEntity == null) {

            return;
        }

        mTxvShareTitle.setText(mShareOrdersEntity.getShareTitle());
        mTxvShareAuthor.setText(mShareOrdersEntity.getUsername());
        mTxvShareTime.setText(mShareOrdersEntity.getShareDate());
        mTxvGoodName.setText(mShareOrdersEntity.getBingoRecordEntity().getTitle());
        mTxvActivityId.setText(mShareOrdersEntity.getBingoRecordEntity().getActivityId()+"");
        mTxvBuyCnt.setText(mShareOrdersEntity.getBingoRecordEntity().getBuyCnt()+"");
        mTxvLuckyNumber.setText(mShareOrdersEntity.getBingoRecordEntity().getLuckyNumber());
        mTxvRunLotteryTime.setText(mShareOrdersEntity.getBingoRecordEntity().getHumanAlreadyRunLotteryTime());
        mTxvShareContent.setText(mShareOrdersEntity.getShareContent());

        List<String> imgUrlLists = mShareOrdersEntity.getShareImgs();
        ImageView imageView;
        String imgUrl;
        for (int i=0, len=imgUrlLists.size(); i<len; i++) {
            imgUrl = imgUrlLists.get(i);
            imageView = mImgList.get(i);
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(imgUrl, imageView, mDisplayImageOptions);
        }

    }



    private void initManager() {
        mShareOrdersBiz = ShareOrdersBiz.getInstance(this);
        mDetailInfoBiz = DetailInfoBiz.getInstance(this);
    }

    private void initRes() {
        initImageLoaderConf();
        mShareOrdersEntity = mShareOrdersBiz.getDataEntity();

    }


    @Override
    protected void handleUIMessage(String strUIAction) {

    }

    @Override
    protected String getDebugTag() {
        return TAG;
    }

    /**
     * 该期活动详细
     * @param view
     */
    public void onBtnActivityDetial(View view) {
        mDetailInfoBiz.startActivity(this, mShareOrdersEntity.getBingoRecordEntity().getActivityId());
    }










    public void onBtnBack(View view) {
        super.onBackPressed();
    }



}
